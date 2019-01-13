package com.task.zenreadsservice.books.errorhandling.exceptions;

import lombok.Getter;

@Getter
public class EmptyCellException extends RuntimeException {

    private static final String ERROR_MESSAGE = "%s cell is empty, rowIdx: %d";


    private final String responseMessageKey;
    private final int row;

    public EmptyCellException(final String name, final int row, final String responseMessageKey) {
        super(String.format(ERROR_MESSAGE, name, row));
        this.responseMessageKey = responseMessageKey;
        this.row = row;
    }

}
