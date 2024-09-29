CREATE TABLE IF NOT EXISTS Author(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS Genre(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS Book(id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, authorId BIGINT, genreId BIGINT, FOREIGN KEY (authorId) REFERENCES Author(id), FOREIGN KEY (genreId) REFERENCES Genre(id));



