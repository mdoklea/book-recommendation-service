package com.task.zenreadsservice.books.parse;

import com.task.zenreadsservice.books.errorhandling.exceptions.BookExcelParseException;
import com.task.zenreadsservice.books.errorhandling.exceptions.EmptyCellException;
import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.genres.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookCsvParseService {

    public List<Book>  importBooksFromCsvFile(MultipartFile file) {

        BufferedReader bufferedReader;
        List<String> results;

        try {

            InputStream is = file.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            results = bufferedReader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            throw new BookExcelParseException("Could not create work book", "Csv file upload error", e);
        }

        return parseBookData(results);
    }

    private List<Book> parseBookData(final List<String> results){

        List<Book> books = new ArrayList<>();

        if(!results.isEmpty()){

            results.forEach(line -> {
                if (!line.contains("\\\"")) {
                    String[] items = line.split(",");
                    if (items.length == 7) {
                        books.add(buildBook(items));
                    }
                }
            });
        }

        return books;
    }

    private Book buildBook(final String[] items){
        return Book.builder()
                    .isbn(checkCell(items[0], "Isbn should not be empty!"))
                    .fileName(checkCell(items[1], "Filename should not be empty!"))
                    .imageUrlPath(checkCell(items[2], "Path should not be empty!"))
                    .title(checkCell(items[3], "Title should not be empty!"))
                    .author(items[4])
                    .genre(checkGenre(items[6]))
                .build();
    }

    private String checkCell(final String item, final String label) {

        if(item.isEmpty()){
            throw new EmptyCellException("Could not create book", label + " Check your file again please.");
        }
        return item;
    }

    private Genre checkGenre(String item) {

        if(item.isEmpty()){
            item = "Other";
        }
        return Genre.builder()
                .category(item)
                .build();

    }
}
