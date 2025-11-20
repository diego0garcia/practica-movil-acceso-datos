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


CREATE TABLE IF NOT EXISTS pedido (
                            id VARCHAR(36) PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            image_path VARCHAR(255),
                            descripcion VARCHAR(255),
                            enabled BOOLEAN NOT NULL,
                            date DATE NOT NULL,
                            id_dependiente VARCHAR(36) NOT NULL,
                            CONSTRAINT fk_pedido_dependiente FOREIGN KEY (id_dependiente) REFERENCES dependiente(id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS producto (
                         id VARCHAR(36) PRIMARY KEY,
                         categoriasId VARCHAR(36),
                         name VARCHAR(100) NOT NULL,
                         image_path VARCHAR(255),
                         descripcion VARCHAR(255),
                         price FLOAT NOT NULL,
                         enabled BOOLEAN NOT NULL,
                         categoriasName FLOAT NOT NULL,
                         id_categoria VARCHAR(36) NOT NULL,
                         CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS lineapedido (
                           id VARCHAR(36) PRIMARY KEY,
                           pedido_id VARCHAR(36) NOT NULL,
                           id_pedido VARCHAR(36) NOT NULL,
                           CONSTRAINT fk_lineapedido_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id) ON DELETE CASCADE);
