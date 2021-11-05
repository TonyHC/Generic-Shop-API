INSERT INTO categories (name) VALUES ('Fruits');
INSERT INTO categories (name) VALUES ('Dried');
INSERT INTO categories (name) VALUES ('Fresh');
INSERT INTO categories (name) VALUES ('Exotic');
INSERT INTO categories (name) VALUES ('Nuts');

INSERT INTO customers (first_name, last_name) VALUES ('Wayne', 'Hone');
INSERT INTO customers (first_name, last_name) VALUES ('Royce', 'Lark');
INSERT INTO customers (first_name, last_name) VALUES ('Travis', 'Kurt');

INSERT INTO vendors (id, name) VALUES (1, 'Fresh Fruits');
INSERT INTO vendors (id, name) VALUES (2, 'Oranges INC');
INSERT INTO vendors (id, name) VALUES (3, 'Dried Fruits');

INSERT INTO products (id, name, price, vendor_id) VALUES (1, 'Bananas', 1.99, 1);
INSERT INTO products (id, name, price, vendor_id) VALUES (2, 'Oranges', 3.00, 2);
INSERT INTO products (id, name, price, vendor_id) VALUES (3, 'Pineapples', 2.49, 1);
INSERT INTO products (id, name, price, vendor_id) VALUES (4, 'Apple', 0.50, 1);
INSERT INTO products (id, name, price, vendor_id) VALUES (5, 'Fresh Mango', 1.00, 1);
INSERT INTO products (id, name, price, vendor_id) VALUES (6, 'Dried Cranberries', 3.50, 3);
INSERT INTO products (id, name, price, vendor_id) VALUES (7, 'Green Grapes', 2.99, 1);
INSERT INTO products (id, name, price, vendor_id) VALUES (8, 'Bag of Tangerines', 5.99, 2);
INSERT INTO products (id, name, price, vendor_id) VALUES (9, 'Dried Pineapples', 3.50, 3);
INSERT INTO products (id, name, price, vendor_id) VALUES (10, 'Peaches', 2.99, 1);