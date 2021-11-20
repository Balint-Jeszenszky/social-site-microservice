import { Request, Response } from "express";
import { MediaStatusEnum } from "../model/Post";
import { getPostMedia } from '../services/postService';

export default function mediaStatus() {
    
    return async (req: Request, res: Response) => {
        const postId = req.params.postId;

        if (postId === undefined) {
            return res.sendStatus(400);
        }

        const media = await getPostMedia(parseInt(postId));

        if (media) {
            const data: any = {status: MediaStatusEnum[media.status], progress: media.progress ?? 0, name: media.filename}

            return res.json(data);
        }

        return res.sendStatus(404);
    };
}