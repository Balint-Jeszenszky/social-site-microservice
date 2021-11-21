import { Request, Response } from "express";

export default function getProfilePicture() {

    return (req: Request, res: Response) => {
        const filename = req.params.name;

        return res.sendFile(`${process.cwd()}/media/profilePicture/${filename}`);
    }
}