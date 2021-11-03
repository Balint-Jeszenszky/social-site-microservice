import { Schema, Document } from 'mongoose';
import db from '../db/db';

export enum MediaStatusEnum { PROCESSING, FAILED, AVAILABLE }

export interface IPost extends Document {
    postId: number;
    filename: string;
    createdAt: Date;
    status: MediaStatusEnum;
}

export function toPostDataDto(post: IPost) {
    return {
        id: post._id,
        postId: post.postId,
        filename: post.filename,
        createdAt: post.createdAt,
        status: post.status
    };
}

const PostSchema: Schema = new Schema({
    postId: { type: Number, required: true, unique: true },
    filename: { type: String, required: true, unique: true },
    createdAt: { type: Date, required: true, unique: true },
    status: { type: String, enum: ['PROCESSING', 'FAILED', 'AVAILABLE'], default: 'PROCESSING', required: true, unique: true }
});

export default db.model<IPost>('Post', PostSchema);
