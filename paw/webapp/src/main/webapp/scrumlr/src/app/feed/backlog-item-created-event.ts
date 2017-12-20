import { BaseFeedEvent } from 'app/feed/base-feed-event';
import { BacklogItem } from 'app/backlog/backlog-item';

export interface BacklogItemCreatedEvent extends BaseFeedEvent {
    item: BacklogItem;
}
