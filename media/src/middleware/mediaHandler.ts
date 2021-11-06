import { Request, Response } from "express";
import convertImage from "../services/convertImage";
import convertVideo from "../services/convertVideo";
import fs from "fs";

export default function mediaHandler() {
    const MEDIA_PATH = 'media';
    const IMAGE_PATH = `${MEDIA_PATH}/images`;
    const VIDEO_PATH = `${MEDIA_PATH}/videos`;
    
    createDir(MEDIA_PATH);
    createDir(IMAGE_PATH);
    createDir(VIDEO_PATH);

    return async (req: Request, res: Response) => {
        if (!req.file) {
            return res.sendStatus(400);
        }

        if (req.file.mimetype.startsWith('image/')) {
            const success = await convertImage(req.file.path, IMAGE_PATH, 1);
            if (!success) {
                console.log('failed image conversion');
                return res.sendStatus(406);
            }
        } else {
            try {
                convertVideo(req.file.path, VIDEO_PATH, 1);
            } catch (e) {
                res.status(406).send(e);
            }
        }

        res.sendStatus(202);
    }
}

function createDir(name: string) {
    try {
        fs.accessSync(name);
    } catch (e) {
        fs.mkdirSync(name);
    }
}
