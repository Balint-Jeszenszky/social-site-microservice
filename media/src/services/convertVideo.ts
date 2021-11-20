import path from "path";
import ffmpegStatic from 'ffmpeg-static'
import ffprobeStatic from 'ffprobe-static';
import Ffmpeg, { FfprobeStream } from "fluent-ffmpeg";
import { promises as fs } from 'fs';
import { setFailed, setProcessed, setProcessingStarted, setProgress } from './postService';

export default function convertVideo(filepath: string, dest: string, postId: number) {

    Ffmpeg.setFfmpegPath(ffmpegStatic);
    Ffmpeg.setFfprobePath(ffprobeStatic.path);

    Ffmpeg.ffprobe(filepath, (err, metadata) => {
        if (err) {
            throw new Error(err);
        }

        const videoStream = metadata.streams.find(s => s.codec_type === 'video');
        const audioStream = metadata.streams.find(s => s.codec_type === 'audio');

        if (!videoStream || !audioStream) {
            throw new Error('no video or audio stream');
        }

        const videoProps = calculateVideoProperties(videoStream);
        const audioProps = calculateAudioProperties(audioStream);

        const tid = setTimeout(() => command.kill('SIGKILL'), (metadata.format.size || 1024 ** 3) / 300);

        const command = Ffmpeg()
            .input(filepath)
            .format('mp4')
            .videoBitrate(videoProps.bitRate)
            .videoCodec('libx264')
            .fps(videoProps.fps)
            .size(videoProps.dimensions)
            .audioBitrate(audioProps.bitRate)
            .audioChannels(audioProps.channels)
            .audioFrequency(audioProps.sampleRate)
            .audioCodec('aac')
            .output(`${dest}/${path.parse(filepath).name}.mp4`)
            .on('start', function (commandLine) {
                console.log('Spawned Ffmpeg with command: ' + commandLine);
                setProcessingStarted(postId, `${path.parse(filepath).name}.mp4`);
            })
            .on('progress', function (progress) {
                setProgress(postId, progress.percent);
                console.log('Processing: ' + progress.percent + '% done');
            })
            .on('end', async () => {
                clearTimeout(tid);
                await fs.rm(filepath);
                console.log('Transcoding succeeded !');
                setProcessed(postId);
            })
            .on('error', async (err) => {
                clearTimeout(tid);
                await fs.rm(filepath);
                await fs.rm(dest);
                console.log('Cannot process video: ' + err.message);
                setFailed(postId);
            });

        command.run();
    });
}

function calculateVideoProperties(videoStream: FfprobeStream) {
    const { width, height, avg_frame_rate } = videoStream;

    if (!width || !height || !avg_frame_rate) {
        throw new Error('no video metadata');
    }

    const hozizontal = width > height;

    const supportedDimensions = [1280, 640, 320];

    const maxDimension = Math.max(width, height);

    let size = 320;

    for (const dim of supportedDimensions) {
        if (dim <= maxDimension) {
            size = dim;
            break;
        }
    }

    const fps = Math.min(parseInt(avg_frame_rate), 30);

    const dimensions = hozizontal ? `${size}x?` : `?x${size}`;

    const bitRate = width * height * fps / 30000;

    return { fps, dimensions, bitRate };
}

function calculateAudioProperties(audioStream: FfprobeStream) {
    const { bit_rate, channels, sample_rate } = audioStream;

    if (!bit_rate || !channels || !sample_rate) {
        throw new Error('no audio metadata');
    }

    const bitRate = Math.min(parseInt(bit_rate) / 1000, 128);
    const channelsNumber = Math.min(channels, 2);
    const sampleRate = Math.min(sample_rate, 44100);

    return { bitRate, channels: channelsNumber, sampleRate };
}
