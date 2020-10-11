-- dummy
INSERT INTO SHARING (id, user_id, room_id, token, total_amount, created_date) VALUES (1, 314, 333, 'ABC', 10000, NOW());
INSERT INTO SHARING (id, user_id, room_id, token, total_amount, created_date) VALUES (2, 314, 333, 'DEF', 100000, NOW());
INSERT INTO DISTRIBUTION (sharing_id, amount) VALUES (1, 3000);
INSERT INTO DISTRIBUTION (sharing_id, amount) VALUES (1, 3000);
INSERT INTO DISTRIBUTION (sharing_id, amount) VALUES (1, 4000);