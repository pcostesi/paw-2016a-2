import { Project } from '../project/project';

export interface Iteration {
  number: number;
  code: string;
  project: Project;
  startDate: string;
  endDate: string;
}
