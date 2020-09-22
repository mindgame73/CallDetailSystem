-- Used by Spring Remember Me API.
CREATE TABLE niiar.persistent_logins (
    username varchar2(64) not null,
    series varchar2(64) not null,
    token varchar2(64) not null,
    last_used Date not null,
    PRIMARY KEY (series)
);
--------------------------------------

-- test user with login: admin; password: 123123aA
insert into niiar.users (USER_ID, USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values (niiar.user_seq.nextval, 'admin', '$2a$10$W69aVkDdVaokr.wHKh/biOBwGX9z5oLYFh2dwpnyGcuixT630aa0G', 1);
---


-- add roles
insert into niiar.roles (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');

insert into niiar.roles (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_USER');
---


insert into niiar.user_roles (USER_ID, ROLE_ID)
values ((select USER_ID from niiar.users where USER_NAME = 'admin'),  1);
---
Commit;