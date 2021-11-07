import { Router } from 'express';
import upload from '../services/upload';
import mediaHandler from '../middleware/mediaHandler';
import auth from '../middleware/auth';
import mediaStatus from '../middleware/mediaStatus';
import accessMedia from '../middleware/accessMedia';
import getMedia from '../middleware/getMedia';

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

router.post(
    '/media/accessKey',
    auth(),
    accessMedia()
);

router.get(
    '/media/:name',
    getMedia()
);

export default router;
