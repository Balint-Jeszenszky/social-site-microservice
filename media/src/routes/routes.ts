import { Router } from 'express';
import upload from '../services/upload';
import mediaHandler from '../middleware/mediaHandler';
import auth from '../middleware/auth';

const router = Router();

router.post(
    '/upload',
    // auth(),
    upload,
    mediaHandler()
);

export default router;