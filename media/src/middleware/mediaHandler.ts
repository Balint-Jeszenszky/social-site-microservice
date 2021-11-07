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
        } else {
            try {
                convertVideo(req.file.path, VIDEO_PATH, postId);
            } catch (e) {
                return res.status(406).send(e);
            }
        }

        return res.status(202).json({processed});
    }
}

function createDir(name: string) {
    try {
        fs.accessSync(name);
    } catch (e) {
        fs.mkdirSync(name);
    }
}
