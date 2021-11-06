import mongoose from 'mongoose';

mongoose.connect(process.env.DB_CONN!);

export default mongoose;
