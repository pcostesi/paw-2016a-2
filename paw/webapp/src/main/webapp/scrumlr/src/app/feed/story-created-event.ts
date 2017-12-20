import { BaseFeedEvent } from 'app/feed/base-feed-event';
import { Story } from 'app/story/story';

export interface StoryCreatedEvent extends BaseFeedEvent {
    story: Story;
}
