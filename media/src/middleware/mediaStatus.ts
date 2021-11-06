import { Request, Response } from "express";
import { getStatus } from '../services/postService';

export default function mediaStatus() {
    
    return async (req: Request, res: Response) => {
        const postId = req.params.postId;

        if (postId === undefined) {
            return res.sendStatus(400);
        }

        const status = await getStatus(parseInt(postId));

        if (status) {
            return res.json({status: status.status});
        }

        return res.sendStatus(404);
    };
}