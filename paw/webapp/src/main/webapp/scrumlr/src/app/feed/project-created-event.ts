import { BaseFeedEvent } from 'app/feed/base-feed-event';
import { Project } from 'app/project/project';

export interface ProjectCreatedEvent extends BaseFeedEvent {
    project: Project;
}
