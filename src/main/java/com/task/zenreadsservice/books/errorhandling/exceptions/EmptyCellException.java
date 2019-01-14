package com.task.zenreadsservice.books.errorhandling.exceptions;

import lombok.Getter;

@Getter
public class EmptyCellException extends RuntimeException {

    private static final String ERROR_MESSAGE_1 = "%s cell is empty, rowIdx: %d";
    private static final String ERROR_MESSAGE_2 = "%s cell is empty.";



    private final String responseMessageKey;
    private final int row;

    public EmptyCellException(final String name, final int row, final String responseMessageKey) {
        super(String.format(ERROR_MESSAGE_1, name, row));
        this.responseMessageKey = responseMessageKey;
        this.row = row;
    }

    public EmptyCellException(final String name,  final String responseMessageKey) {
        super(String.format(ERROR_MESSAGE_2, name));
        this.responseMessageKey = responseMessageKey;
        this.row = 0;
    }
}
