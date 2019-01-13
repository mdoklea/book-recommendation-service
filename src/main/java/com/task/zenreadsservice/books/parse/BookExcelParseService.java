package com.task.zenreadsservice.books.parse;

import com.task.zenreadsservice.books.errorhandling.exceptions.BookExcelParseException;
import com.task.zenreadsservice.books.errorhandling.exceptions.EmptyCellException;
import com.task.zenreadsservice.books.model.Book;
import com.task.zenreadsservice.books.util.WorkbookHelper;
import com.task.zenreadsservice.genres.model.Genre;
import lombok.val;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.SheetUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BookExcelParseService {


    private static final int DATA_ROWS_START = 0;
    private static final int ISBN_NUMBER_COLUMN = 0;
    private static final int FILENAME_COLUMN = 1;
    private static final int IMAGE_PATH_COLUMN = 2;
    private static final int TITLE_COLUMN = 3;
    private static final int AUTHOR_COLUMN = 4;
    private static final int GENRE_COLUMN = 6;
    private static final String EMPTY_CELL_ERROR = "Empty field";


    public List<Book> parse(final MultipartFile file) {
        val importedBooks = new ArrayList<Book>();

        try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
            wb.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            for (val sheet : WorkbookHelper.getVisibleSheets(wb)) {
                importedBooks.addAll(books(sheet));

            }
        } catch (InvalidFormatException | IOException e) {
            throw new BookExcelParseException("Could not create work book", "File upload error", e);

        }
        return importedBooks;
    }

    private Set<Book> books(final Sheet sheet) {
        val books = new HashSet<Book>();
        val bookRowsEnd = sheet.getLastRowNum();

        for (int row = DATA_ROWS_START; row <= bookRowsEnd; row++) {
            val book = buildBook(sheet, row);
            if(book.getGenre() != null){
                books.add(book);
            }

        }

        return books;
    }

    private Book buildBook(final Sheet sheet, final int rowIdx) {

        return Book.builder()
                .isbn(isbn(sheet, rowIdx))
                .fileName(fileName(sheet, rowIdx))
                .imageUrlPath(imagePath(sheet, rowIdx))
                .title(title(sheet, rowIdx))
                .author(author(sheet, rowIdx))
                .genre(buildGenre(sheet, rowIdx))
                .build();
    }
    private Genre buildGenre(final Sheet sheet, final int rowIdx) {

        return Genre.builder()
                .category(genreCategory(sheet, rowIdx))
                .build();
    }
    private String isbn(final Sheet sheet, final int rowIdx) {

        val isbnNumberOpt = Optional
                .ofNullable(SheetUtil.getCell(sheet, rowIdx, ISBN_NUMBER_COLUMN))
                .orElseThrow(
                        () -> new EmptyCellException("Isbn number ", rowIdx + 1, EMPTY_CELL_ERROR));

        SheetUtil.getCell(sheet, rowIdx, ISBN_NUMBER_COLUMN).setCellType(CellType.STRING);

        return isbnNumberOpt.getStringCellValue();
    }

    private String fileName(final Sheet sheet, final int rowIdx) {
        val fileNameOpt = Optional
                .ofNullable(SheetUtil.getCell(sheet, rowIdx, FILENAME_COLUMN))
                .orElseThrow(
                        () -> new EmptyCellException("File name  url", rowIdx + 1, EMPTY_CELL_ERROR));

        SheetUtil.getCell(sheet, rowIdx, FILENAME_COLUMN).setCellType(CellType.STRING);

        return fileNameOpt.getStringCellValue();
    }

    private String imagePath(final Sheet sheet, final int rowIdx) {
        val imagePathOpt = Optional
                .ofNullable(SheetUtil.getCell(sheet, rowIdx, IMAGE_PATH_COLUMN))
                .orElseThrow(
                        () -> new EmptyCellException("Image path ", rowIdx + 1, EMPTY_CELL_ERROR));

        SheetUtil.getCell(sheet, rowIdx, IMAGE_PATH_COLUMN).setCellType(CellType.STRING);

        return imagePathOpt.getStringCellValue();
    }

    private String title(final Sheet sheet, final int rowIdx) {
       val titleOpt = Optional
                .ofNullable(SheetUtil.getCell(sheet, rowIdx, TITLE_COLUMN))
                .orElseThrow(
                        () -> new EmptyCellException("Title ", rowIdx + 1, EMPTY_CELL_ERROR));

        SheetUtil.getCell(sheet, rowIdx, TITLE_COLUMN).setCellType(CellType.STRING);

        return titleOpt.getStringCellValue();
    }

    private String author(final Sheet sheet, final int rowIdx) {

        val authorOpt = Optional
                .ofNullable(SheetUtil.getCell(sheet, rowIdx, AUTHOR_COLUMN));

        if ( authorOpt.isPresent())
        {
            SheetUtil.getCell(sheet, rowIdx, AUTHOR_COLUMN).setCellType(CellType.STRING);
            authorOpt.map(Cell::getStringCellValue);
        }

        return null;
    }

    private String genreCategory(final Sheet sheet, final int rowIdx) {

        val genreOpt =  Optional.ofNullable(SheetUtil.getCell(sheet, rowIdx, GENRE_COLUMN));

        if ( genreOpt.isPresent())
        {
            SheetUtil.getCell(sheet, rowIdx, AUTHOR_COLUMN).setCellType(CellType.STRING);
            genreOpt.map(Cell::getStringCellValue)
                    .orElse("Other");
        }

        return null;
    }
}
