import mongoose from 'mongoose';

mongoose.connect(process.env.DB_CONN!, { auth: {username: process.env.DB_USER, password: process.env.DB_PASS }});

export default mongoose;
