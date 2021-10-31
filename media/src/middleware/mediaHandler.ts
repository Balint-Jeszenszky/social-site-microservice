import { Request, Response } from "express";
import convertImage from "../services/convertImage";
import { promises as fs } from "fs";

export default async function mediaHandler(req: Request, res: Response) {
    if (!req.file) {
        return res.sendStatus(400);
    }

    if (req.file.mimetype.startsWith('image/')) {
        const success = await convertImage(req.file.path);
        if (!success) {
            console.log('failed image conversion');
            return res.sendStatus(406);
        }
    } else {
        console.log('video');
    }
    await fs.rm(req.file.path);
    res.sendStatus(202);
}