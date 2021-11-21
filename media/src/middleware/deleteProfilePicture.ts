import { Request, Response } from "express";
import Profile from "../model/Profile";
import { promises as fs } from 'fs';

export default function deleteProfilePicture() {

    return async (req: Request, res: Response) => {
        const userId = parseInt(req.params.userId);

        if (!userId) {
            return res.sendStatus(400);
        }

        if (res.locals.userDetails.id !== userId && !res.locals.isAdmin) {
            return res.sendStatus(403);
        }

        try {
            const profile = await Profile.findOne({userId});
            
            if (!profile) {
                return res.sendStatus(404);
            }

            await fs.rm(`media/profilePicture/${profile.filename}.jpg`);
            await fs.rm(`media/profilePicture/${profile.filename}_small.jpg`);

            await profile.delete();

            return res.sendStatus(204);

        } catch (e) {
            console.log(e);
            return res.sendStatus(500);
        }
    }
}