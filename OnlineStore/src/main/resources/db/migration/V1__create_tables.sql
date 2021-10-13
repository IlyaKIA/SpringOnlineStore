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

INSERT INTO category
VALUES
    (1,'Fruit'),
    (2,'Baking'),
    (3,'Electronics'),
    (4,'Toys');

INSERT INTO product
VALUES
    (1,'TV',20000,3,'\data\images\product\monitor.png'),
    (2,'Apple',120,1,'\data\images\product\apple.png'),
    (3,'Muffin',40,2,'\data\images\product\muffin.png'),
    (4,'Toaster',750,3,'\data\images\product\toast.png'),
    (5,'Cake',85,2,'\data\images\product\cake.png'),
    (6,'Doll',330,4,'\data\images\product\doll.png'),
    (7,'Watch',2000,3,'\data\images\product\watch.png'),
    (8,'Calculator',500,3,'\data\images\product\schoolCalc.png'),
    (9,'Orange',200,1,'\data\images\product\orange.png'),
    (10,'Bred',45,2,'\data\images\product\bread.png'),
    (11,'Banana',110,1,'\data\images\product\bananas.png');

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