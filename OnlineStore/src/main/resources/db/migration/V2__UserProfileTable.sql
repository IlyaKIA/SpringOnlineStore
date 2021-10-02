CREATE TABLE user_profile (
    username text not null primary key,
    email text,
    phone_number text,
    city text,
    picture_path text,
    constraint fk_profile_users
        foreign key(username)
        references users(username)
);
INSERT INTO user_profile (username)
VALUES
    ('user1'),
    ('user2');