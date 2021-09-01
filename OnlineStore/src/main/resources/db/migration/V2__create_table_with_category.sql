ALTER TABLE product ADD COLUMN category_id int;
CREATE TABLE category (
id serial primary key,
title text not null
);
