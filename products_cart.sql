create table cart
(
    id       blob        not null,
    user_id  blob        not null,
    id_prod  blob        null,
    quantity varchar(20) not null,
    add_date date        null
);

INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3134, 0x31, 0x3132, '2', '2022-08-29');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3136, 0x31, 0x3131, '2', '2022-08-29');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3138, 0x31, 0x3130, '2', '2022-08-29');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3230, 0x3530, 0x3133, '1', '2022-08-31');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3231, 0x3530, 0x3132, '2', '2022-08-31');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3232, 0x3530, 0x3131, '3', '2022-08-31');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3233, 0x3531, 0x3130, '2', '2022-09-06');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3234, 0x3531, 0x3131, '3', '2022-09-08');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3237, 0x3531, 0x3132, '1', '2022-09-09');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x3333, 0x3531, 0x3133, '1', '2022-09-09');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x313535, 0x3532, 0x3131, '1', '2022-09-29');
INSERT INTO products.cart (id, user_id, id_prod, quantity, add_date) VALUES (0x313536, 0x3532, 0x3132, '1', '2022-09-29');
