import { Request, Response } from "express";
import path from "path";
import process from "process";


export default function getMedia() {

    return (req: Request, res: Response) => {
        const filename = req.params.name;

        const ext = path.parse(filename).ext;
        const mediaPath = (ext === '.jpg') ? 'images' : (ext === '.mp4') ? 'videos' : 'files';

        return res.sendFile(path.join(process.cwd(), 'media', mediaPath, filename));
    }
}