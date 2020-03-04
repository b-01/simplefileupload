-- Role: FilesRole

-- DROP ROLE "FilesRole";

CREATE ROLE "FilesRole"
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

-- Role: filesuser

-- DROP ROLE filesuser;

CREATE ROLE filesuser LOGIN
  ENCRYPTED PASSWORD 'md5dc207a80fc82380f1ae44c6d47fe8d80' -- filesuser CHANGE THIS!!!
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
GRANT "FilesRole" TO filesuser;

-- Database: filesdb

-- DROP DATABASE filesdb;

CREATE DATABASE filesdb
  WITH OWNER = "FilesRole"
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

-- Table: "user"

-- DROP TABLE "user";

CREATE TABLE "user"
(
  username character varying(50) NOT NULL,
  salt character varying(50) NOT NULL,
  id serial NOT NULL,
  user_pass character varying(65) NOT NULL,
  CONSTRAINT "PK_USER_ID" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "user"
  OWNER TO "FilesRole";
GRANT ALL ON TABLE "user" TO "FilesRole";

-- Table: storage

-- DROP TABLE storage;

CREATE TABLE storage
(
  user_id integer NOT NULL,
  file_uid character varying(100) NOT NULL,
  file_name character varying(200) NOT NULL,
  CONSTRAINT "PK_STORAGE_UID" PRIMARY KEY (file_uid),
  CONSTRAINT "FK_STORAGE_USER" FOREIGN KEY (user_id)
      REFERENCES "user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storage
  OWNER TO "FilesRole";
GRANT ALL ON TABLE storage TO "FilesRole";
