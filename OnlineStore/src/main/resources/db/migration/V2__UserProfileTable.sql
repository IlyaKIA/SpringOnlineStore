CREATE TABLE user_profile (
    username text not null primary key,
    name text,
    email text,
    phone_number text,
    city text,
    picture_path text,
    constraint fk_profile_users
        foreign key(username)
        references users(username)
);
INSERT INTO user_profile (username, picture_path)
VALUES
    ('user1', '\data\images\user\blank-profile_640.png'),
    ('user2', '\data\images\user\blank-profile_640.png');