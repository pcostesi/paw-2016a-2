import { UserProfile } from 'app/api/account.service';
import { Project } from 'app/project/project';

export interface BaseFeedEvent {
    type: string;
    actor?: UserProfile;
    eventId: number;
    project?: Project;
    time: string;
}
