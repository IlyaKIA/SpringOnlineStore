create table users(
    username varchar(50) not null primary key,
    password varchar(80) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

INSERT INTO users
VALUES
    ('user1', '{noop}123', true),
    ('user2', '{noop}123', true);

INSERT INTO authorities
VALUES
    ('user1', 'ROLE_ADMIN'),
    ('user1', 'ROLE_USER'),
    ('user2', 'ROLE_USER');