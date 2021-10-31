import { Router } from 'express';
import upload from '../services/upload';
import mediaHandler from '../middleware/mediaHandler';

const router = Router();

router.post(
    '/upload',
    upload,
    mediaHandler
);

export default router;