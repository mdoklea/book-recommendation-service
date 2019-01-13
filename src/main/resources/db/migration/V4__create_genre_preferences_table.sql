CREATE TABLE genre_preferences (
  users_id           UUID REFERENCES users (id)  NOT NULL,
  genres_id          UUID REFERENCES genres (id) NOT NULL,
  PRIMARY KEY (users_id, genres_id)
)