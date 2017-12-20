import { BaseFeedEvent } from 'app/feed/base-feed-event';
import { Iteration } from 'app/iteration/iteration';

export interface IterationCreatedEvent extends BaseFeedEvent {
    iteration: Iteration;
}
