INSERT INTO tbl_user (email, fullname, password, role) VALUES ('usuario1@email.com','usuario 1', '$2a$10$nOSsWuFjJSv5CRm7Dsd3.uII/pC7j7qCdwQ5iGDloSqhJImW1cAPy', 'CUSTOMER');
INSERT INTO tbl_user (email, fullname, password, role) VALUES ('usuario2@email.com', 'usuario 2', '$2a$10$PY3Kr9rM3v1KWYEGkaeZAOWuC2pUzHRDUVY5Ew2mrzaFEQX2IJDU6', 'ASSISTANT_ADMINISTRATOR');
INSERT INTO tbl_user (email, fullname, password, role) VALUES ('usuario3@email.com', 'usuario 3', '$2a$10$lHtHSESKwAv.K6TYLQDs/O0fDDhyg/7l1xYOZu.PVHXzDD.DOoU2q', 'ADMINISTRATOR');

-- CREACIÓN DE CATEGORIAS
INSERT INTO category (name, status) VALUES ('Electrónica', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Ropa', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Deportes', 'ENABLED');
INSERT INTO category (name, status) VALUES ('Hogar', 'ENABLED');

-- CREACIÓN DE PRODUCTOS
INSERT INTO product (name, price, status, category_id) VALUES ('Smartphone', 500.00, 'ENABLED', 1);
INSERT INTO product (name, price, status, category_id) VALUES ('Auriculares Bluetooth', 50.00, 'DISABLED', 1);
INSERT INTO product (name, price, status, category_id) VALUES ('Tablet', 300.00, 'ENABLED', 1);

INSERT INTO product (name, price, status, category_id) VALUES ('Camiseta', 25.00, 'ENABLED', 2);
INSERT INTO product (name, price, status, category_id) VALUES ('Pantalones', 35.00, 'ENABLED', 2);
INSERT INTO product (name, price, status, category_id) VALUES ('Zapatos', 45.00, 'ENABLED', 2);

INSERT INTO product (name, price, status, category_id) VALUES ('Balón de Fútbol', 20.00, 'ENABLED', 3);
INSERT INTO product (name, price, status, category_id) VALUES ('Raqueta de Tenis', 80.00, 'DISABLED', 3);

INSERT INTO product (name, price, status, category_id) VALUES ('Aspiradora', 120.00, 'ENABLED', 4);
INSERT INTO product (name, price, status, category_id) VALUES ('Licuadora', 50.00, 'ENABLED', 4);

