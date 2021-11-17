/* tslint:disable */
/* eslint-disable */
import { UserDetailsDto } from './user-details-dto';
export interface CommentDto {
  id: number;
  postId: number;
  text: string;
  user: UserDetailsDto;
}
