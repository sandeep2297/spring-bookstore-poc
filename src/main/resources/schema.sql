DROP TABLE IF EXISTS TBL_USER;
DROP TABLE IF EXISTS TBL_BOOK;
DROP TABLE IF EXISTS TBL_AUTHOR;
DROP TABLE IF EXISTS TBL_AUTHOR_BOOK_MAPPING;

CREATE TABLE TBL_USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  full_name VARCHAR(250) NOT NULL,
  user_name VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  created_date DATETIME NOT NULL
);

CREATE TABLE TBL_BOOK (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  isbn VARCHAR(250) NOT NULL,
  title VARCHAR(250) NOT NULL,
  publish_year INT NOT NULL,
  price INT NOT NULL,
  genre VARCHAR(250) NOT NULL,
  created_by INT NOT NULL,
  created_date DATETIME NOT NULL,
  modified_date DATETIME NOT NULL,
  modified_by INT NOT NULL
);

CREATE TABLE TBL_AUTHOR (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  birth_date VARCHAR(250) NOT NULL,
  created_by INT NOT NULL,
  created_date DATETIME NOT NULL,
  modified_date DATETIME NOT NULL,
  modified_by INT NOT NULL
);

CREATE TABLE TBL_STORE_MAPPING (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  book_id VARCHAR(250) NOT NULL,
  author_id VARCHAR(250) NOT NULL,
  created_by INT NOT NULL,
  created_date DATETIME NOT NULL
);

