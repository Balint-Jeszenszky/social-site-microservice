import Profile from "../model/Profile";
import { promises as fs } from 'fs';

export async function setProcessed(userId: number, filename: string) {
    const oldProfile = await Profile.findOne({userId});
    
    if (oldProfile) {
        await fs.rm(`media/profilePicture/${oldProfile.filename}.jpg`);
        await fs.rm(`media/profilePicture/${oldProfile.filename}_small.jpg`);

        await oldProfile.delete();
    }

    const profile = new Profile();
    profile.userId = userId;
    profile.filename = filename;
    profile.createdAt = new Date();

    await profile.save();
}
