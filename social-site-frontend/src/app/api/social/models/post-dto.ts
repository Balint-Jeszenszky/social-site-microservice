/* tslint:disable */
/* eslint-disable */
import { UserDetailsDto } from './user-details-dto';
export interface PostDto {
  comments: number;
  created: string;
  id: number;
  liked: boolean;
  likes: number;
  mediaName?: string;
  processedMedia?: boolean;
  text: string;
  user: UserDetailsDto;
}
