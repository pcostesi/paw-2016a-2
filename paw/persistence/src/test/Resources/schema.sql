CREATE TABLE IF NOT EXISTS project (
	project_id IDENTITY,
	name varchar(100) NOT NULL,
	code varchar(10) NOT NULL,
	description varchar(500) NOT NULL,
	date_start DATE NOT NULL,
	UNIQUE ( project_id, name ),
	UNIQUE ( name ),
	UNIQUE ( code )
);

CREATE TABLE IF NOT EXISTS iteration (
	iteration_id IDENTITY,
	project_id INTEGER NOT NULL,
	number INTEGER NOT NULL,
	date_start DATE NOT NULL,
	date_end DATE NOT NULL,
	FOREIGN KEY ( project_id ) REFERENCES project ( project_id ) ON DELETE CASCADE,
	UNIQUE ( project_id, number )
);

CREATE TABLE IF NOT EXISTS story (
	story_id IDENTITY,
	iteration_id INTEGER NOT NULL,
	title varchar(100) NOT NULL,
	FOREIGN KEY ( iteration_id ) REFERENCES iteration ( iteration_id ) ON DELETE CASCADE,
	UNIQUE ( iteration_id, title )
);

CREATE TABLE IF NOT EXISTS task (
	task_id IDENTITY,
	story_id INTEGER NOT NULL,
	title varchar(100) NOT NULL,
	description varchar(500),
	owner varchar(100),
	status INTEGER NOT NULL,
	priority INTEGER NOT NULL,
	score INTEGER NOT NULL,
	FOREIGN KEY ( story_id ) REFERENCES story ( story_id ) ON DELETE CASCADE,
	UNIQUE ( story_id, title )
);

CREATE TABLE IF NOT EXISTS account (
    username varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    mail varchar(100) NOT NULL,
    UNIQUE ( username ),
    UNIQUE ( mail )
);

CREATE TABLE IF NOT EXISTS backlog (
	item_id IDENTITY,
	project_id INTEGER NOT NULL,
	title varchar(100) NOT NULL,
	description varchar(500),	
	FOREIGN KEY ( project_id ) REFERENCES project ( project_id ) ON DELETE CASCADE,
	UNIQUE ( project_id, title )
);
