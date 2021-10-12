/* tslint:disable */
/* eslint-disable */
import { UserDetailsDto } from './user-details-dto';
export interface LoginDetailsDto {
  accessToken?: string;
  refreshToken?: string;
  userDetails?: UserDetailsDto;
}
