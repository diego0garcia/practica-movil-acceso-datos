CREATE TABLE IF NOT EXISTS dependiente (
                             id VARCHAR(36) PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             email VARCHAR(100) NOT NULL,
                             password VARCHAR(100) NOT NULL,
                             image_path VARCHAR(255),
                             enabled BOOLEAN NOT NULL,
                             is_admin BOOLEAN NOT NULL);

CREATE TABLE IF NOT EXISTS categoria (
                             id VARCHAR(36) PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             image_path VARCHAR(255),
                             descripcion VARCHAR(255),
                             enabled BOOLEAN NOT NULL);

DROP TABLE IF EXISTS pedido;
CREATE TABLE IF NOT EXISTS pedido (
                            id VARCHAR(36) PRIMARY KEY,
                            enabled BOOLEAN NOT NULL,
                            date DATE NOT NULL,
                            id_dependiente VARCHAR(36) NOT NULL,
                            CONSTRAINT fk_pedido_dependiente FOREIGN KEY (id_dependiente) REFERENCES dependiente(id) ON DELETE CASCADE);

DROP TABLE IF EXISTS producto;
CREATE TABLE IF NOT EXISTS producto (
                         id VARCHAR(36) PRIMARY KEY,
                         id_categoria VARCHAR(36) NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         image_path VARCHAR(255),
                         descripcion VARCHAR(255),
                         price FLOAT NOT NULL,
                         enabled BOOLEAN NOT NULL,
                         categoriasName VARCHAR(255) NOT NULL,
                         CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id) ON DELETE CASCADE);

DROP TABLE IF EXISTS lineapedido;
CREATE TABLE IF NOT EXISTS lineapedido (
                           id VARCHAR(36) PRIMARY KEY,
                           product_name VARCHAR(100) NOT NULL,
                           product_price FLOAT NOT NULL,
                           id_pedido VARCHAR(36) NOT NULL,
                           CONSTRAINT fk_lineapedido_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE);

INSERT INTO dependiente (id, name, email, password, image_path, enabled, is_admin)
VALUES ('admin', 'admin', 'admin@admin.es', 'admin', 'c1c51d6b-918c-4b26-98af-23a69e43127a.png', True, True);