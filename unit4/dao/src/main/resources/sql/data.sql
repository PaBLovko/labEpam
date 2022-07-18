INSERT INTO tags (tag_id, tag_name, is_available) VALUES('1', '#funny', '1');
INSERT INTO tags (tag_id, tag_name, is_available) VALUES('2', '#cool', '1');
INSERT INTO tags (tag_id, tag_name, is_available) VALUES('3', '#warm', '1');
INSERT INTO tags (tag_id, tag_name, is_available) VALUES('4', '#cold', '1');
INSERT INTO tags (tag_id ,tag_name, is_available) VALUES('5', '#relax', '1');

INSERT INTO gift_certificates (gift_certificate_id, is_available, certificate_name, description, price, duration, create_date)
VALUES(1, '1', 'Car', 'Fast car', '99.99', 4, '2011-11-19T11:10:11');

INSERT INTO gift_certificates (gift_certificate_id, is_available, certificate_name, description, price, duration, create_date)
VALUES(2, '1', 'Sand', 'Yellow sand', '2.35', 24, '2020-05-05T23:42:12');

INSERT INTO gift_certificates (gift_certificate_id, is_available, certificate_name, description, price, duration, create_date)
VALUES(3, '1', 'City', 'Large city', '1000', 2, '2021-05-10T08:42:10');

INSERT INTO gift_certificates (gift_certificate_id, is_available, certificate_name, description, price, duration, create_date)
VALUES(4, '1', 'Plane', 'Fly plain', '452.12', 6, '2016-01-29T16:30:21');

INSERT INTO gift_certificates (gift_certificate_id, is_available, certificate_name, description, price, duration, create_date)
VALUES(5, '1', 'Ferry', 'Ferryman', '0.99', 14, '2019-11-19T11:10:11');

INSERT INTO gift_certificates_tags (gift_certificate_tag_id, gift_certificate_id_fk, tag_id_fk) VALUES (1, 1, 1);
INSERT INTO gift_certificates_tags (gift_certificate_tag_id, gift_certificate_id_fk, tag_id_fk) VALUES (2, 1, 2);
INSERT INTO gift_certificates_tags (gift_certificate_tag_id, gift_certificate_id_fk, tag_id_fk) VALUES (3, 3, 5);
INSERT INTO gift_certificates_tags (gift_certificate_tag_id, gift_certificate_id_fk, tag_id_fk) VALUES (4, 3, 3);
INSERT INTO gift_certificates_tags (gift_certificate_tag_id, gift_certificate_id_fk, tag_id_fk) VALUES (5, 4, 4);

INSERT INTO users (user_id, first_name, last_name, email, password, role, is_active)
VALUES(1, 'Pablo','Escobar','pablo@gmail.com', '$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W',
       'ADMIN', '1');
INSERT INTO users (user_id, first_name, last_name, email, password, role, is_active)
VALUES(2, 'Pavel','Kazhamiakin','pavel@gmail.com','$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W',
       'USER', '1');
INSERT INTO users (user_id, first_name, last_name, email, password, role, is_active)
VALUES(3, 'Anton','Petrenko','anton@gmail.com','$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W',
       'USER', '1');
INSERT INTO users (user_id, first_name, last_name, email, password, role, is_active)
VALUES(4, 'Kirill','Petrosan','kirill@gmail.com','$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W',
       'USER', '1');
INSERT INTO users (user_id, first_name, last_name, email, password, role, is_active)
VALUES(5, 'Alina','Butterfly','alina@gmail.com','$2y$12$XRUTT3VBVCzf64HS.fCcse0QhSiFZlfTb7E8XZ6iBzTjvPyhC8h.W',
       'USER', '1');

INSERT INTO orders (order_id, price, timestamp, user_id_fk, gift_certificate_id_fk)
VALUES(1, '0.99', '2012-10-10T11:10:11.111', 1, 1);
INSERT INTO orders (order_id, price, timestamp, user_id_fk, gift_certificate_id_fk)
VALUES(2, '99.99', '2021-06-26T16:10:11.111', 2, 1);
INSERT INTO orders (order_id, price, timestamp, user_id_fk, gift_certificate_id_fk)
VALUES(3, '99.99', '2019-12-31T23:59:59.111', 3, 1);
INSERT INTO orders (order_id, price, timestamp, user_id_fk, gift_certificate_id_fk)
VALUES(4, '2.35', '2011-11-19T11:10:11.111', 4, 1);
INSERT INTO orders (order_id, price, timestamp, user_id_fk, gift_certificate_id_fk)
VALUES(5, '1000', '2019-11-19T11:10:11', 5, 1);