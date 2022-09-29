create table users
(
    id                   mediumtext                   null,
    first_name           varchar(50)                  not null,
    last_name            varchar(50)                  not null,
    middle_name          varchar(100)                 not null,
    email                varchar(100)                 not null,
    phone                varchar(50)                  null,
    status               varchar(20) default 'ACTIVE' not null,
    role                 varchar(20) default 'USER'   not null,
    password             varchar(150)                 not null,
    reset_password_token varchar(30)                  null,
    constraint users_email_uindex
        unique (email)
);

INSERT INTO products.users (id, first_name, last_name, middle_name, email, phone, status, role, password, reset_password_token) VALUES ('1', 'Admin', 'Admin', '', 'admin@gmail.com', '023213', 'ACTIVE', 'ADMIN', '$2a$12$LaUJXMqN6dqmyE2qtaMhruPTVMlqRyrnDQVD8UyI6N2HrOo2CRHru', null);
INSERT INTO products.users (id, first_name, last_name, middle_name, email, phone, status, role, password, reset_password_token) VALUES ('34', 'Андрій ', 'Карпенко ', 'Юрійович', 'karpenko2002in42@gmail.com', '0667517681', 'ACTIVE', 'USER', '$2a$12$XLdgrEPpMo5yhF0bNUtb8eGfMzxSq/T7MxvERcN47z/Vz.JHaFXZK', 'bzbxHKDIhV7641g8UkLNG2uiq8bH03');
INSERT INTO products.users (id, first_name, last_name, middle_name, email, phone, status, role, password, reset_password_token) VALUES ('2', 'User', 'user', '', 'user@gmail.com', '22213', 'ACTIVE', 'USER', '$2a$12$B/7K6JHE60q.1cBoE/ymfeEuDZt2rQTcXGwsHmZp7vKmTjJiTyoeK', null);
