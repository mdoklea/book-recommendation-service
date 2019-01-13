package com.task.zenreadsservice.books.util;

import lombok.val;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class WorkbookHelper {

    private WorkbookHelper() {
    }

    private static boolean isSheetHidden(final Workbook wb, final Sheet sheet) {
        val sheetIdx = wb.getSheetIndex(sheet);
        return wb.isSheetHidden(sheetIdx) || wb.isSheetVeryHidden(sheetIdx);
    }

    public static List<Sheet> getVisibleSheets(final Workbook wb) {
        val visibleSheets = new ArrayList<Sheet>();
        val itr = wb.sheetIterator();
        while (itr.hasNext()) {
            val sheet = itr.next();
            if (!isSheetHidden(wb, sheet)) {
                visibleSheets.add(sheet);
            }
        }
        return visibleSheets;
    }
}
