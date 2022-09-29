create table orders
(
    id          blob         null,
    user_id     blob         not null,
    total_sum   varchar(100) not null,
    first_name  varchar(50)  not null,
    last_name   varchar(50)  not null,
    middle_name varchar(50)  not null,
    address     varchar(250) not null,
    phone_num   varchar(50)  null,
    comment     varchar(500) null,
    status      varchar(50)  not null,
    add_date    datetime     not null,
    delivery    varchar(100) not null,
    user_ac_id  varchar(100) null
);

INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x3836, 0x3532, '600', 'Андрій ', 'Карпенко ', 'Юрійович', 'м. Миколаїв #21', '0667517681', '23123', 'Підтвердженно', '2022-09-19 16:32:27', '1', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x3838, 0x3532, '600', 'Андрій ', 'Карпенко ', 'Юрійович', 'м. Миколаїв #21', '0667517681', '23123', 'Відправленно', '2022-09-19 16:32:27', '1', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x3933, 0x3532, '380', 'Андрій ', 'Карпенко ', 'Юрійович', 'м. Одесса', '0667517681', '23232', 'Відміна', '2022-09-19 16:32:43', '1', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x3937, 0x3532, '400', 'Андрій ', 'Карпенко ', 'Юрійович', 'м. Маріуполь', '0667517681', '2123', 'Очікуйте дзвінка', '2022-09-19 16:33:10', '1', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313032, 0x3532, '290', 'Стефан', 'Петроник', 'Сергійович', 'м. Тернопіль №55', '23213123', ' ', 'Відміна', '2022-09-19 16:34:23', '1', '');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313035, 0x3532, '1000', 'Тимошенко', 'Данкович', 'Сергійович', 'м. Миколаїв %2', '+380662321', '213', 'Очікуйте дзвінка', '2022-09-19 16:42:19', '1', '');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313136, 0x3532, '580', 'Андрій ', 'Карпенко ', 'Юрійович', 'м. Миколаїв №20', '0667517681', ' ', 'Очікуйте дзвінка', '2022-09-23 21:11:18', '1', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313232, 0x3532, '890', 'Андрій ', 'Карпенко ', 'Юрійович', 'yuuyuyuy', '0667517681', ' ', 'Очікуйте дзвінка', '2022-09-23 23:30:42', '1', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313237, 0x3532, '290', 'Авто', 'дед', 'кадович', 'о улкдптлукпку', '06306406588888', 'лол', 'Очікуйте дзвінка', '2022-09-26 20:53:11', '2', '');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313331, 0x3532, '570', 'Авто', 'дед', 'кадович', 'гуглмит', '0667517681', ' ', 'Очікуйте дзвінка', '2022-09-26 20:58:35', '2', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313333, 0x3532, '0', 'dvewv', 'molk', 'moop', 'klmlk', 'lmklk', 'kmkl', 'Очікуйте дзвінка', '2022-09-26 21:05:14', '1', '');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313335, 0x3532, '190', 'kjnjk', ' k ', ' k lk', 'jknjk', '88989', 'nlnk', 'Очікуйте дзвінка', '2022-09-26 21:08:02', '1', '');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313339, 0x3532, '290', 'Андрій ', 'Карпенко ', 'Юрійович', '323123', '0667517681', ' ', 'Очікуйте дзвінка', '2022-09-26 21:25:30', '1', 'karpenko2002in42@gmail.com');
INSERT INTO products.orders (id, user_id, total_sum, first_name, last_name, middle_name, address, phone_num, comment, status, add_date, delivery, user_ac_id) VALUES (0x313438, 0x3532, '190', 'л лмслдуц', 'уцаьдсцудс', 'цуьсжцудс35', 'фысфысфс', '234234324', 'фысфысфы', 'Очікуйте дзвінка', '2022-09-26 21:44:03', '2', '');
