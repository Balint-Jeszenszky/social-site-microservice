import Profile from "../model/Profile";

export async function setProcessed(userId: number, filename: string) {
    const profile = new Profile();
    profile.userId = userId;
    profile.filename = filename;
    profile.createdAt = new Date();

    await profile.save();
}

export async function getPostMedia(userId: number) {
    return await Profile.findOne({userId});
}
