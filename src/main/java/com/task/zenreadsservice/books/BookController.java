package com.task.zenreadsservice.books;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.books.parse.BookCsvParseService;
import com.task.zenreadsservice.books.service.BookService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService bookService;
    private final BookCsvParseService bookCsvParseService;

    @Autowired
    public BookController(BookService bookService, BookCsvParseService bookCsvParseService) {
        this.bookService = bookService;
        this.bookCsvParseService = bookCsvParseService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Book>> uploadCSV(@RequestParam("file") MultipartFile file) {

        if (file == null) {
            return ResponseEntity.badRequest().build();
        }
        val results = bookCsvParseService.importBooksFromCsvFile(file);

        return ResponseEntity.ok(bookService.saveParsedBooks(results));
    }
}

