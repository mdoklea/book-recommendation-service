package com.task.zenreadsservice.books;

import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.books.service.BookService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Book>> upload(MultipartFile file) {

        if (file == null) {
            return ResponseEntity.badRequest().build();
        }
        val results = bookService.importBooksFromFile(file);

        return ResponseEntity.ok(bookService.saveParsedBooks(results));
    }

}

