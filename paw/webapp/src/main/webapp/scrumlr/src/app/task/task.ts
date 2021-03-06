import { TaskStatus } from 'app/task/task-status.enum';
import { TaskPriority } from 'app/task/task-priority.enum';
import { TaskScore } from 'app/task/task-score.enum';
import { UserProfile } from 'app/api/account.service';
import { Story } from 'app/story/story';

export class Task {
  taskId: number;
  title: string;
  description: string;
  status: TaskStatus;
  priority: TaskPriority;
  score: TaskScore;
  owner: string;
  story: Story;
}
