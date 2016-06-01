UPDATE task SET score = 3 WHERE score = 4;
UPDATE task SET score = 4 WHERE score = 8;

CREATE TABLE IF NOT EXISTS project_user (
    username character varying(100) NOT NULL,
    project_id integer NOT NULL
);

ALTER TABLE project ADD COLUMN admin varchar(100) DEFAULT 'default';

INSERT INTO project_user(username, project_id) (SELECT DISTINCT owner_story.owner, iteration.project_id FROM (SELECT task.owner, story.iteration_id FROM task JOIN story ON task.story_id = story.story_id) as owner_story JOIN iteration ON owner_story.iteration_id = iteration.iteration_id WHERE owner IS NOT NULL);

UPDATE project SET admin = COALESCE((SELECT pu.username FROM project_user as pu WHERE pu.project_id = project.project_id LIMIT 1), 'default');

INSERT INTO account(username, password, mail) SELECT project.name, project.code, project.code || '@scrumlr.com' FROM project WHERE project.admin = 'default';
INSERT INTO project_user(username, project_id) SELECT name, project_id FROM project WHERE project.admin = 'default';

UPDATE project SET admin = name WHERE admin = 'default';
