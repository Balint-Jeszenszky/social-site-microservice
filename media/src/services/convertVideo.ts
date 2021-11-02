import handbrake from "handbrake-js";
import path from "path";

export default async function convertVideo(filepath: string, dest: string) {
    try {
        await handbrake.run({ input: filepath, output: `${dest}/${path.parse(filepath).name}.mp4`, optimize: true, preset: 'Very Fast 480p30'});
        return true;
    } catch (e) {
        console.log(e)
        return false;
    }
}