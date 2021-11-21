import { Router } from 'express';
import upload from '../services/upload';
import mediaHandler from '../middleware/mediaHandler';
import auth from '../middleware/auth';
import mediaStatus from '../middleware/mediaStatus';
import accessMedia from '../middleware/accessMedia';
import getMedia from '../middleware/getMedia';
import deleteMedia from '../middleware/deleteMedia';
import createProfilePicture from '../middleware/createProfilePicture';
import deleteProfilePicture from '../middleware/deleteProfilePicture';
import uploadProfilePicture from '../services/uploadProfilePicture';
import getProfilePicture from '../middleware/getProfilePicture';

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

router.get(
    '/profilePicture/:name',
    getProfilePicture()
);

router.post(
    '/profilePicture/:userId',
    auth(),
    uploadProfilePicture,
    createProfilePicture()
);

router.delete(
    '/profilePicture/:userId',
    auth(),
    deleteProfilePicture()
);

router.delete(
    '/:postId',
    deleteMedia()
);

export default router;
