import { Iteration } from 'app/iteration/iteration';


export interface Story {
    storyId: number;
    title: string;
    iteration: Iteration;
}
