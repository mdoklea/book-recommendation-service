package com.task.zenreadsservice.books.errorhandling;

import com.task.zenreadsservice.books.errorhandling.exceptions.BookExcelParseException;
import com.task.zenreadsservice.books.errorhandling.exceptions.EmptyCellException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler {


    @ExceptionHandler(value = {BookExcelParseException.class})
    public ResponseEntity<Object> handleBookExcelImportException(final BookExcelParseException e) {
        return ResponseEntity.badRequest().body(e.getResponseMessageKey());
    }
    @ExceptionHandler(value = {EmptyCellException.class})
    public ResponseEntity<Object> handleEmptyCell(final EmptyCellException e) {
        return ResponseEntity.badRequest()
                .body(String.format(e.getResponseMessageKey(), e.getRow()));
    }
}