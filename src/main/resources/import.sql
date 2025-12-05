------------------------------------------------------------
-- USERS
------------------------------------------------------------
INSERT INTO tb_user (user_id, user_type, user_first_name, user_second_name, user_address, user_post_code, user_phone_number, user_email, user_password) VALUES (1, 0, 'John', 'Doe', '123 Maple Street', 'AB12 3CD', 1234567890, 'john@example.com', 'password123');

INSERT INTO tb_user (user_id, user_type, user_first_name, user_second_name, user_address, user_post_code, user_phone_number, user_email, user_password) VALUES (2, 1, 'Alice', 'Smith', '45 Queen Road', 'XY98 7ZT', 987654321, 'alice@example.com', 'secretPass');

------------------------------------------------------------
-- PRODUCTS
------------------------------------------------------------
INSERT INTO tb_products (prod_id, prod_name, prod_pic_name_small, prod_pic_name_large, prod_description_short, prod_description_long, prod_price, prod_quantity) VALUES (1, 'Wireless Mouse', 'mouse_small.jpg', 'mouse_large.jpg', 'Comfortable wireless mouse', 'A 6-button ergonomic wireless mouse with silent clicks.', 19.99, 150);

INSERT INTO tb_products (prod_id, prod_name, prod_pic_name_small, prod_pic_name_large, prod_description_short, prod_description_long, prod_price, prod_quantity) VALUES (2, 'Mechanical Keyboard', 'keyboard_small.jpg', 'keyboard_large.jpg', 'RGB Mechanical Keyboard', 'A high-quality mechanical keyboard with customizable RGB lighting.', 89.99, 80);

INSERT INTO tb_products (prod_id, prod_name, prod_pic_name_small, prod_pic_name_large, prod_description_short, prod_description_long, prod_price, prod_quantity) VALUES (3, 'USB-C Charger', 'charger_small.jpg', 'charger_large.jpg', 'Fast USB-C charger', 'A 45W fast-charging USB-C wall adapter.', 24.50, 200);

INSERT INTO tb_products (prod_id, prod_name, prod_pic_name_small, prod_pic_name_large, prod_description_short, prod_description_long, prod_price, prod_quantity) VALUES (4, 'Bluetooth Headphones', 'headphones_small.jpg', 'headphones_large.jpg', 'Noise-cancelling headphones', 'Wireless headphones with ANC and 20-hour battery life.', 59.95, 60);

------------------------------------------------------------
-- ORDERS
------------------------------------------------------------
INSERT INTO tb_orders (order_no, order_date, order_total, order_status, shipping_date, user_id) VALUES (1, '2025-01-10T14:32:00', 134.48, 'Processing', '2025-01-15', 1);

INSERT INTO tb_orders (order_no, order_date, order_total, order_status, shipping_date, user_id) VALUES (2, '2025-01-12T10:05:00', 89.99, 'Shipped', '2025-01-17', 2);

------------------------------------------------------------
-- ORDER LINES
------------------------------------------------------------
INSERT INTO tb_order_line (order_line_id, quantity_ordered, sub_total, order_no, prod_id) VALUES (1, 1, 89.99, 1, 2);

INSERT INTO tb_order_line (order_line_id, quantity_ordered, sub_total, order_no, prod_id) VALUES (2, 2, 49.00, 1, 3);

INSERT INTO tb_order_line (order_line_id, quantity_ordered, sub_total, order_no, prod_id) VALUES (3, 1, 19.99, 1, 1);

INSERT INTO tb_order_line (order_line_id, quantity_ordered, sub_total, order_no, prod_id) VALUES (4, 1, 59.95, 2, 4);
