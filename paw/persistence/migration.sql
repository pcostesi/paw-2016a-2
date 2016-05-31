UPDATE task SET score = 3 WHERE score = 4;
UPDATE task SET score = 4 WHERE score = 8;

CREATE TABLE IF NOT EXISTS project_user (
    username character varying(100) NOT NULL,
    project_id integer NOT NULL
);

INSERT INTO account(username, password, mail) (SELECT name, code, code || '@scrumlr.com' FROM project);

INSERT INTO project_user(username, project_id) (SELECT DISTINCT owner_story.owner, iteration.project_id FROM (SELECT task.owner, story.iteration_id FROM task JOIN story ON task.story_id = story.story_id) as owner_story JOIN iteration ON owner_story.iteration_id = iteration.iteration_id WHERE owner IS NOT NULL);

INSERT INTO project_user(username, project_id) (SELECT name, project_id FROM project);

ALTER TABLE project ADD COLUMN admin varchar(100) DEFAULT 'default';

UPDATE project SET admin = project.name;
