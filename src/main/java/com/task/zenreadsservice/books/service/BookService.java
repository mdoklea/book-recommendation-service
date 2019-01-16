package com.task.zenreadsservice.books.service;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.books.repository.BookRepository;
import com.task.zenreadsservice.genres.model.Genre;
import com.task.zenreadsservice.genres.repository.GenreRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
public class BookService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;


    @Autowired
    public BookService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
       return bookRepository.findAll();
    }

    public List<Book> saveParsedBooks(final List<Book> parsedBooks)
    {
        val parsedGenres = parsedBooks.stream()
                .map(Book::getGenre)
                .collect(toSet());

        val newParsedGenres = findNonExistentGenres(parsedGenres);
        genreRepository.saveAll(newParsedGenres);

        val updatedParsedBooks = updateParsedBooksWithGenreFromDB(parsedBooks);
        bookRepository.saveAll(updatedParsedBooks);

        return new ArrayList<>(updatedParsedBooks);
    }

    public Optional<Book> findBookById(final UUID id){
        return bookRepository.findOneById(id);
    }

    private Set<Genre> findNonExistentGenres(final Set<Genre> parsedGenres) {

        val nonExistentGenresFromParsedGenres = new HashSet<Genre>();

        parsedGenres.forEach(genre -> {
            val genreByCategory = genreRepository.findGenreByCategory(genre.getCategory());

            if(!genreByCategory.isPresent()){
                nonExistentGenresFromParsedGenres.add(genre);
            }
        });

        return nonExistentGenresFromParsedGenres;
    }

    private Set<Book> updateParsedBooksWithGenreFromDB(final List<Book> parsedBooks)
    {
        val books = new HashSet<Book>();
       parsedBooks.stream() .forEach(parsedBook -> {
                    val genreFromDB = genreRepository.findGenreByCategory(parsedBook.getGenre().getCategory());
                    if(!bookRepository.findOneByIsbn(parsedBook.getIsbn()).isPresent() &&
                    genreFromDB.isPresent()) {
                        val book =  Book.builder()
                                .isbn(parsedBook.getIsbn())
                                .fileName(parsedBook.getFileName())
                                .imageUrlPath(parsedBook.getImageUrlPath())
                                .title(parsedBook.getTitle())
                                .author(parsedBook.getAuthor())
                                .genre(genreFromDB.get())
                                .build();

                        books.add(book);
                    }
                });
       return books;
    }

    public List<Book> findBooksByGenre(final Genre genre) {

        val books = new ArrayList<Book>();

        if( genre!= null && genreRepository.findGenreByCategory(genre.getCategory()).isPresent()){
             books.addAll(bookRepository.findBooksByGenre(genre));
        }

        return books;
    }
}
