import path from "path";
import { promises as fs } from 'fs';
import { setFailed, setProcessed, setProcessingStarted } from './postService';


export default async function saveFile(filepath: string, dest: string, postId: number) {
    const filename = path.parse(filepath).base;
    setProcessingStarted(postId, filename);

    try {
        await fs.rename(filepath, `${dest}/${filename}`);
    } catch(e) {
        setFailed(postId);
        return false;
    }

    setProcessed(postId);
    return true;
}