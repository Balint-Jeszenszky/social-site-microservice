/* tslint:disable */
/* eslint-disable */
import { UserDetailsDto } from './user-details-dto';
export interface PostDto {
  created: string;
  id: number;
  mediaName?: string;
  text: string;
  user: UserDetailsDto;
}
