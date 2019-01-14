package com.task.zenreadsservice.books.errorhandling.exceptions;

import lombok.Getter;

@Getter
public class EmptyCellException extends RuntimeException {

    private static final String ERROR_MESSAGE = "%s cell is empty.";
    private final String responseMessageKey;

    public EmptyCellException(final String name,  final String responseMessageKey) {
        super(String.format(ERROR_MESSAGE, name));
        this.responseMessageKey = responseMessageKey;
    }
}
