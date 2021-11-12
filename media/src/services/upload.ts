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

const upload = multer({
    storage,
    limits: {
        fileSize: 1024**3
    },
    fileFilter: (req, file, cb) => {
        const ext = path.extname(file.originalname);
        const fileTypes = /jpeg|jpg|png|mp4|mov/;
        const mimeTypes = /jpeg|jpg|png|mp4|quicktime/;
        const valid = fileTypes.test(ext.toLowerCase()) && mimeTypes.test(file.mimetype);
        cb(null, valid);
    }
}).single('mediaUpload');

export default upload;
