import { Request, Response } from "express";
import path from "path";
import convertProfilePicture from "../services/convertProfilePicture";
import createDir from "../services/createDirectory";

export default function createProfilePicture() {
    const MEDIA_PATH = 'media';
    const PROFILE_PICTURE_PATH = `${MEDIA_PATH}/profilePicture`;

    createDir(PROFILE_PICTURE_PATH);

    return async (req: Request, res: Response) => {
        const userId = parseInt(req.params.userId);

        if (!req.file || !userId) {
            return res.sendStatus(400);
        }

        if (res.locals.userDetails.id !== userId && !res.locals.isAdmin) {
            return res.sendStatus(403);
        }

        let processed = false;

        const success = await convertProfilePicture(req.file.path, PROFILE_PICTURE_PATH, userId);

        processed = true;

        if (!success) {
            console.log('failed image conversion');
            return res.sendStatus(406);
        }

        return res.status(202).json({processed, name: path.parse(req.file.path).name});
    }
}