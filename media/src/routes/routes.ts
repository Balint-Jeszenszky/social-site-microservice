import { Router } from 'express';
import upload from '../services/upload';
import mediaHandler from '../middleware/mediaHandler';
import auth from '../middleware/auth';
import mediaStatus from '../middleware/mediaStatus';
import accessMedia from '../middleware/accessMedia';

const router = Router();

router.post(
    '/upload',
    auth(),
    upload,
    mediaHandler()
);

router.get(
    '/status/:postId',
    mediaStatus()
);

router.get(
    '/media/:postId',
    auth(),
    accessMedia()
);

export default router;
