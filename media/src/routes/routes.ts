import { Router } from 'express';
import upload from '../services/upload';

const router = Router();

router.post('/upload', upload, (req, res) => {
    console.log(req.file);
    res.sendStatus(202);
});

export default router;