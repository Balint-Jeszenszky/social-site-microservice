import axios from "axios";
import { NextFunction, Request, Response } from "express";

export default function auth() {
    const AUTH_URL = process.env.AUTH_SERVER;

    if (AUTH_URL === undefined) {
        throw new TypeError('AUTH_URL not set');
    }

    return async (req: Request, res: Response, next: NextFunction) => {

        const authHeader = req.headers['authorization'];
        const token = authHeader?.split(' ')[1];

        if (!token) {
            return res.sendStatus(401);
        }

        try {
            const response = await axios.post(AUTH_URL, {accessToken: token});
            const userDetails = response.data;
            res.locals.userDetails = userDetails;
            res.locals.isAdmin = userDetails.roles.includes('ROLE_ADMIN');
            
            return next();
        } catch (e) {
            console.log(e);
            return res.sendStatus(401);
        }
    }
}