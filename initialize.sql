CREATE TABLE users (
	id INTEGER PRIMARY KEY,
  username TEXT NOT NULL,
  password TEXT NOT NULL
);
INSERT INTO users (username, password)
VALUES('admin', 'password');
