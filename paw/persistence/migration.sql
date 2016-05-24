UPDATE task SET score = 3 WHERE score = 4;
UPDATE task SET score = 4 WHERE score = 8;

CREATE TABLE IF NOT EXISTS project_user (
    username character varying(100) NOT NULL,
    project_id integer NOT NULL
);

INSERT INTO account(username, password, mail) VALUES ('Admin', 'admin', 'admin@paw.com');

INSERT INTO project_user(username, project_id) (SELECT username, project_id FROM account NATURAL FULL JOIN project);

ALTER TABLE project ADD COLUMN admin varchar(100) DEFAULT 'Admin';
