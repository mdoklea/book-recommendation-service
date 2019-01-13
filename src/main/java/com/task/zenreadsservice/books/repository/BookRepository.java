package com.task.zenreadsservice.books.repository;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.genres.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    Optional<Book> findOneById(final UUID id);
    Optional<Book> findOneByIsbn(final String isbn);
    List<Book> findBooksByGenre(final Genre genre);
}

