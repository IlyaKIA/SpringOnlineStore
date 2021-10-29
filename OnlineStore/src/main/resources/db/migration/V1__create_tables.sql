CREATE TABLE category (
    id bigserial primary key,
    title text not null
);

CREATE TABLE product (
    id bigserial primary key,
    title text not null,
    price int not null,
    category_id int,
    picture_path text,

    foreign key (category_id) references category(id)
);

INSERT INTO category (title)
VALUES
    ('Fruit'),
    ('Baking'),
    ('Electronics'),
    ('Toys');

INSERT INTO product (title, price, category_id, picture_path)
VALUES
    ('TV',20000,3,'\data\images\product\monitor.png'),
    ('Apple',120,1,'\data\images\product\apple.png'),
    ('Muffin',40,2,'\data\images\product\muffin.png'),
    ('Toaster',750,3,'\data\images\product\toast.png'),
    ('Cake',85,2,'\data\images\product\cake.png'),
    ('Doll',330,4,'\data\images\product\doll.png'),
    ('Watch',2000,3,'\data\images\product\watch.png'),
    ('Calculator',500,3,'\data\images\product\schoolCalc.png'),
    ('Orange',200,1,'\data\images\product\orange.png'),
    ('Bred',45,2,'\data\images\product\bread.png'),
    ('Banana',110,1,'\data\images\product\bananas.png');

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
    ('user1', '$2a$10$XvtC8aJHBWfQPCBBb35jCexNz1YUKRMvY3.Oefj106tK2gfq0fY1q', true),
    ('user2', '$2a$10$XvtC8aJHBWfQPCBBb35jCexNz1YUKRMvY3.Oefj106tK2gfq0fY1q', true);

INSERT INTO authorities
VALUES
    ('user1', 'ROLE_ADMIN'),
    ('user1', 'ROLE_USER'),
    ('user2', 'ROLE_USER');