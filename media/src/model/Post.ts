import { Schema, Document } from 'mongoose';
import db from '../db/db';

export enum MediaStatusEnum { PROCESSING, FAILED, AVAILABLE }

export interface IPost extends Document {
    postId: number;
    filename: string;
    createdAt: Date;
    status: MediaStatusEnum;
    progress: number;
}

export function toPostDataDto(post: IPost) {
    return {
        id: post._id,
        postId: post.postId,
        filename: post.filename,
        createdAt: post.createdAt,
        status: post.status,
        progress: post.progress
    };
}

const PostSchema: Schema = new Schema({
    postId: { type: Number, required: true, unique: true },
    filename: { type: String, required: true, unique: true },
    createdAt: { type: Date, required: true },
    status: { type: Number, enum: MediaStatusEnum, default: MediaStatusEnum.PROCESSING, required: true },
    progress: { type: Number }
});

export default db.model<IPost>('Post', PostSchema);
