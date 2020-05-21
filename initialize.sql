CREATE TABLE users (
	id INTEGER PRIMARY KEY,
  username TEXT NOT NULL,
  password TEXT NOT NULL
);
INSERT INTO users (username, password)
VALUES('admin', '$2y$10$WRZeOh.bOOboXuxL1s2aAO8Fos5SUodtHq77IdE8Kp.3bunaFe.dy');
