import { UserProfile } from '../api';

export interface Project {

  name: string ;

  code: string;

  description: string;

  startDate: Date;

  admin: UserProfile;

  members: UserProfile[] | string[];
}
