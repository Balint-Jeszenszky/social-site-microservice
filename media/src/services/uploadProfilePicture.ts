import multer from "multer";
import path from "path";
import { randomBytes } from 'crypto';

const storage = multer.diskStorage({
    destination: 'rawFiles',
    filename: (req, file, cb) => {
        const ext = path.extname(file.originalname);
        cb(null, randomBytes(16).toString('hex') + ext);
    }
});

const uploadProfilePicture = multer({
    storage,
    limits: {
        fileSize: 1024 ** 2 * 2
    },
    fileFilter: (req, file, cb) => {
        const ext = path.extname(file.originalname);
        const fileTypes = /jpeg|jpg|png/;
        const mimeTypes = /jpeg|jpg|png/;
        const valid = fileTypes.test(ext.toLowerCase()) && mimeTypes.test(file.mimetype);
        cb(null, valid);
    }
}).single('profilePicture');

export default uploadProfilePicture;
