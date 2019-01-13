CREATE TABLE books (
  id                UUID       PRIMARY  KEY       NOT NULL,
  isbn              VARCHAR    UNIQUE             NOT NULL,
  file_name         VARCHAR                       NOT NULL,
  image_url_path    VARCHAR                       NOT NULL,
  title             VARCHAR                       NOT NULL,
  author            VARCHAR                       NULL,
  genres_id         UUID REFERENCES genres (id)   NOT NULL
)