CREATE TABLE users (
	id INTEGER PRIMARY KEY,
  username TEXT NOT NULL,
  password TEXT NOT NULL,
  responsabile BOOLEAN NOT NULL
);
INSERT INTO users (username, password, responsabile)
VALUES('admin', '$2b$10$swPp91a8qj40VkcBEn704eIFNOQ1Tvwxc2lZlQppIq/VgyLFLfzpS', 1);
