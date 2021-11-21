import axios from "axios";
import Post, { MediaStatusEnum } from "../model/Post";

export async function setProcessingStarted(postId: number, filename: string) {
    const post = new Post();
    post.postId = postId;
    post.filename = filename;
    post.status = MediaStatusEnum.PROCESSING;
    post.createdAt = new Date();
    await post.save();
    await notifySocialService(postId, post.status, post.filename);
}

export async function setProcessed(postId: number) {
    const post = await Post.findOne({postId});
    if (post) {
        post.status = MediaStatusEnum.AVAILABLE;
        await post.save();
        await notifySocialService(postId, post.status, post.filename);
    }
}

export async function setFailed(postId: number) {
    const post = await Post.findOne({postId});
    if (post) {
        post.status = MediaStatusEnum.FAILED;
        await post.save();
        await notifySocialService(postId, post.status, post.filename);
    }
}

export async function getPostMedia(postId: number) {
    return await Post.findOne({postId});
}

export async function setProgress(postId: number, progress: number) {
    const post = await Post.findOne({postId});
    if (post) {
        post.progress = progress;
        await post.save();
    }
}

async function notifySocialService(postId: number, status: MediaStatusEnum, name: string) {
    await axios.put(`${process.env.SOCIAL_SERVER}/${postId}`, { status: MediaStatusEnum[status], name });
}
