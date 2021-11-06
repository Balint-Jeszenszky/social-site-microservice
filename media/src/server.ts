import express, { ErrorRequestHandler, NextFunction, Request, Response } from 'express';
import router from './routes/routes';

const app = express();

app.use('/api/media', router);

app.use((err: ErrorRequestHandler, req: Request, res: Response, next: NextFunction) => {
    res.status(500).send('Something went wrong...');
    console.log(`Error: ${err}`);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Listening on port ${PORT}`));
