CREATE TABLE dependiente (
                             id VARCHAR(36) PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             email VARCHAR(100) NOT NULL,
                             password VARCHAR(100) NOT NULL,
                             image_path VARCHAR(255),
                             enabled BOOLEAN NOT NULL,
                             is_admin BOOLEAN NOT NULL );
