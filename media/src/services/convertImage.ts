import sharp from 'sharp';
import path from "path";
import { promises as fs } from 'fs';
import { setFailed, setProcessed, setProcessingStarted } from './postService';

export default async function convertImage(filepath: string, dest: string, postId: number) {
    try {
        setProcessingStarted(postId, `${path.parse(filepath).name}.jpg`);

        const {width, height} = await sharp(filepath).metadata();

        if (!width || !height) {
            return false;
        }

        let image = sharp(filepath).jpeg({quality: 70});
        const maxSize = 1080;

        if (width > maxSize || height > maxSize) {
            let w, h;
            if (width > height) {
                w = maxSize;
                h = Math.floor(height * maxSize / width);
            } else {
                w = Math.floor(width * maxSize / height);
                h = maxSize;
            }

            image = image.resize(w, h);
        }

        await image.toFile(`${dest}/${path.parse(filepath).name}.jpg`);

        await fs.rm(filepath);

        setProcessed(postId);
        
        return true;
    } catch (e) {
        console.log(e);
        await fs.rm(filepath);
        setFailed(postId);
        return false;
    }
}