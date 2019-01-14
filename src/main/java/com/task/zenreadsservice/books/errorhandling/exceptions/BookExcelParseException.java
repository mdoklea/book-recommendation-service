package com.task.zenreadsservice.books.errorhandling.exceptions;

import lombok.Getter;

@Getter
public class BookExcelParseException extends RuntimeException {

    private final String responseMessageKey;

    public BookExcelParseException(final String message, final String responseMessageKey, final Throwable cause) {
        super(message, cause);
        this.responseMessageKey = responseMessageKey;
    }
}
