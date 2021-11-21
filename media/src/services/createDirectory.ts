import fs from "fs";

export default function createDir(name: string) {
    try {
        fs.accessSync(name);
    } catch (e) {
        fs.mkdirSync(name);
    }
}
