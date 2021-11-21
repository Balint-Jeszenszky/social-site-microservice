import { Schema, Document } from 'mongoose';
import db from '../db/db';

export interface IProfile extends Document {
    userId: number;
    filename: string;
    createdAt: Date;
}

const ProfileSchema: Schema = new Schema({
    userId: { type: Number, required: true, unique: true },
    filename: { type: String, required: true, unique: true },
    createdAt: { type: Date, required: true }
});

export default db.model<IProfile>('Profile', ProfileSchema);
