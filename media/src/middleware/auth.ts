import axios from "axios";
import { NextFunction, Request, Response } from "express";
import { UserDetails } from "../model/UserDetails";


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
            const response: UserDetails = await axios.post(AUTH_URL, token);
            console.log(response);
            return next();
        } catch (e) {
            console.log(e);
            return res.sendStatus(401);
        }
    }
}