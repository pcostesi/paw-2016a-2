import { BaseFeedEvent } from 'app/feed/base-feed-event';
import { BacklogItemCreatedEvent } from 'app/feed/backlog-item-created-event';
import { UserCreatedEvent } from 'app/feed/user-created-event';

export type FeedEvent = BaseFeedEvent | BacklogItemCreatedEvent | UserCreatedEvent;
