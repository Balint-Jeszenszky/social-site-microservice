import { Request, Response } from "express";
import path from "path";
import convertImage from "../services/convertImage";
import convertVideo from "../services/convertVideo";
import createDir from "../services/createDirectory";
import saveFile from "../services/saveFile";

export default function mediaHandler() {
    const MEDIA_PATH = 'media';
    const IMAGE_PATH = `${MEDIA_PATH}/images`;
    const VIDEO_PATH = `${MEDIA_PATH}/videos`;
    const FILE_PATH = `${MEDIA_PATH}/files`;
    
    createDir(MEDIA_PATH);
    createDir(IMAGE_PATH);
    createDir(VIDEO_PATH);
    createDir(FILE_PATH);

    return async (req: Request, res: Response) => {
        if (!req.file || !req.body.postId) {
            return res.sendStatus(400);
        }

        const postId = parseInt(req.body.postId);
        let processed = false;

        if (req.file.mimetype.startsWith('image/')) {
            const success = await convertImage(req.file.path, IMAGE_PATH, postId);
            processed = true;
            if (!success) {
                console.log('failed image conversion');
                return res.sendStatus(406);
            }
        } else if (req.file.mimetype.startsWith('video/')) {
            try {
                convertVideo(req.file.path, VIDEO_PATH, postId);
            } catch (e) {
                return res.status(406).send(e);
            }
        } else {
            const success = await saveFile(req.file.path, FILE_PATH, postId);
            processed = true;
            if (!success) {
                console.log('failed moving file');
                return res.sendStatus(406);
            }
        }

        return res.status(202).json({processed, name: path.parse(req.file.path).name});
    }
}
