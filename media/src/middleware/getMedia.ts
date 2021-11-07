import { Request, Response } from "express";
import path from "path";
import process from "process";


export default function getMedia() {

    return (req: Request, res: Response) => {
        const filename = req.params.name;

        const mediaPath = (path.parse(filename).ext === '.jpg') ? 'images' : 'videos';

        return res.sendFile(path.join(process.cwd(), 'media', mediaPath, filename));
    }
}