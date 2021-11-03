import sharp from 'sharp';
import path from "path";
import { promises as fs } from 'fs';

export default async function convertImage(filepath: string, dest: string) {
    try {
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
                h = Math.round(height * maxSize / width);
            } else {
                w = Math.round(width * maxSize / height);
                h = maxSize;
            }

            image = image.resize(w, h);
        }

        await image.toFile(`${dest}/${path.parse(filepath).name}.jpg`);

        await fs.rm(filepath);
        
        return true;
    } catch (e) {
        console.log(e);
        return false;
    }
}