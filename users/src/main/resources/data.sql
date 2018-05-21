insert into account_rps.roles (name) values ('ROLE_USER');
insert into account_rps.roles (name) values ('ROLE_ADMIN');

insert into account_rps.users (username, password) values ('ADMIN', '$2a$10$jrFdLal8sWBelwsczW.0JuMb6yfwUPQQKQnd9ETJMnRRzT4xPJ3Y.');

insert into account_rps.user_roles (user_id, role_id) values (1, 2);