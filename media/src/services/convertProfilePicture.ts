import sharp from "sharp";
import path from "path";
import { promises as fs } from 'fs';
import { setProcessed } from "./profileService";

export default async function convertProfilePicture(filepath: string, dest: string, userId: number) {
    try {
        const {width, height} = await sharp(filepath).metadata();

        if (!width || !height) {
            return false;
        }

        let image = sharp(filepath).jpeg({quality: 70});
        const maxSize = 300;
        const maxSmallSize = 50;

        let left, top, size;

        if (width > height) {
            left = Math.floor((width - height) / 2);
            top = 0;
            size = height;
        } else {
            left = 0;
            top = Math.floor((height - width) / 2);
            size = width;
        }

        await image.extract({width: size, height: size, left, top}).resize(maxSize, maxSize).toFile(`${dest}/${path.parse(filepath).name}.jpg`);

        await image.resize(maxSmallSize, maxSmallSize).toFile(`${dest}/${path.parse(filepath).name}_small.jpg`);

        await fs.rm(filepath);

        await setProcessed(userId, path.parse(filepath).name);
        
        return true;
    } catch (e) {
        console.log(e);
        await fs.rm(filepath);
        return false;
    }
}