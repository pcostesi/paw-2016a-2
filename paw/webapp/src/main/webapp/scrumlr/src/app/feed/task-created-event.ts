import { BaseFeedEvent } from 'app/feed/base-feed-event';
import { Task } from 'app/task/task';

export interface TaskCreatedEvent extends BaseFeedEvent {
    task: Task;
}
