CREATE TABLE review_and_rating (
    id bigserial not null primary key,
    username text not null,
    product_id bigserial not null,
    review text,
    rating int,

    constraint fk_users_review
        foreign key(username)
        references user_profile(username),

    constraint fk_product_review
        foreign key(product_id)
        references product(id)
);
CREATE index username_index on review_and_rating(username);
CREATE index product_id_index on review_and_rating(product_id);

INSERT INTO review_and_rating (username, product_id, review, rating)
VALUES
    ('user1', 2, 'So nice', 5),
    ('user1', 1, 'Good TV', 4),
    ('user2', 2, 'Good apples', 5);