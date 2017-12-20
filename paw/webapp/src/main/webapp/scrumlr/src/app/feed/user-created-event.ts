import { UserProfile } from 'app/api/account.service';
import { BaseFeedEvent } from 'app/feed/base-feed-event';

export interface UserCreatedEvent extends BaseFeedEvent {
    created: UserProfile;
}
