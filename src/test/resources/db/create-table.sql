CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     first_name VARCHAR(30),
                                     last_name VARCHAR(30),
                                     created_at DATETIME NOT NULL,
                                     email VARCHAR(40) NOT NULL UNIQUE,
                                     password VARCHAR(100) NOT NULL,
                                     active boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     role ENUM('ANONYMOUS', 'ADMIN', 'TRADER')
);

CREATE TABLE IF NOT EXISTS user_role (
                                         user_id BIGINT,
                                         role_id BIGINT,
                                         FOREIGN KEY (user_id) REFERENCES users (id),
                                         FOREIGN KEY (role_id) REFERENCES roles (id),
                                         PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS comments (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        message TEXT,
                                        created_at DATETIME NOT NULL,
                                        approved BOOLEAN,
                                        trader_id_fk BIGINT NOT NULL,
                                        FOREIGN KEY (trader_id_fk) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS game_objects (
                                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            title VARCHAR(100),
                                            text TEXT,
                                            created_at DATETIME NOT NULL,
                                            updated_at DATETIME,
                                            trader_id_fk BIGINT,
                                            FOREIGN KEY (trader_id_fk) REFERENCES users (id)
);