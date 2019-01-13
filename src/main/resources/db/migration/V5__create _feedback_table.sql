CREATE TABLE feedback (
  users_id          UUID REFERENCES users (id)  NOT NULL,
  books_id          UUID REFERENCES books (id) NOT NULL,
  rate              VARCHAR                    NOT NULL,
  PRIMARY KEY (users_id, books_id)
)