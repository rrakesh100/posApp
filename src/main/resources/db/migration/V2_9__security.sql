

DROP TABLE IF EXISTS privileges cascade;
CREATE TABLE privileges(
id bigserial PRIMARY key,
name text NOT NULL UNIQUE
);

DROP TABLE IF EXISTS roles cascade;
CREATE TABLE roles (
id bigserial PRIMARY KEY ,
name text NOT NULL UNIQUE
);

DROP TABLE IF EXISTS role_privileges cascade;
CREATE TABLE role_privileges (
role_id bigserial  ,
privilege_id bigserial,
CONSTRAINT foreign_fk_1 FOREIGN key(role_id) REFERENCES roles(id),
CONSTRAINT foreign_fk_2 FOREIGN key(privilege_id) REFERENCES privileges(id)
);


DROP TABLE IF EXISTS user_accounts cascade ;
CREATE TABLE user_accounts (
id bigserial,
first_name text ,
last_name text,
email text ,
password text,
enabled BOOLEAN DEFAULT true,
secret text,
role_id bigserial,
created_time TIMESTAMP WITHOUT TIME zone DEFAULT now() NOT NULL,
CONSTRAINT foreign_fk_1 FOREIGN key(role_id) REFERENCES roles(id)
);


INSERT INTO PRIVILEGES (name) VALUES ('READ_PRIVILEGE');
INSERT INTO PRIVILEGES (name) VALUES ('WRITE_PRIVILEGE');
INSERT INTO PRIVILEGES (name) VALUES ('DELETE_PRIVILEGE');


INSERT INTO ROLES (name) VALUES ('USER_ROLE');
INSERT INTO ROLES (name) VALUES ('ADMIN_ROLE');

INSERT INTO role_privileges (role_id, privilege_id ) VALUES (2, 1);
INSERT INTO role_privileges (role_id, privilege_id ) VALUES (2, 2);
INSERT INTO role_privileges (role_id, privilege_id ) VALUES (2, 3);




