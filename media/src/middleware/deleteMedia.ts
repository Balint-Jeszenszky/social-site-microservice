import { Request, Response } from "express";
import Post from "../model/Post";
import { promises as fs } from 'fs';
import path from "path/posix";

export default function deleteMedia() {

    return async (req: Request, res: Response) => {
        if (req.params.postId === undefined) {
            return res.sendStatus(400);
        }

        const postId = parseInt(req.params.postId);

        try {
            const post = await Post.findOne({postId});
            
            if (!post) {
                return res.sendStatus(404);
            }

            const ext = path.parse(post.filename).ext;
            const mediaPath = (ext === '.jpg') ? 'images' : (ext === '.mp4') ? 'videos' : 'files';

            await fs.rm(path.join('media', mediaPath, post.filename));

            await post.delete();

            return res.sendStatus(204);

        } catch (e) {
            console.log(e);
            return res.sendStatus(500);
        }
    }
}
