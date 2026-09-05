package com.annamalayarrice.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.annamalayarrice.dto.HullerReportDto;

@Service
public class ExcelExportService {

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public byte[] exportHullerReport(List<HullerReportDto> reports)
            throws IOException {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Huller Report");

            // =========================================================
            // COLUMN WIDTHS
            // =========================================================

            // A & B - Empty space
            sheet.setColumnWidth(0, 4 * 256);
            sheet.setColumnWidth(1, 4 * 256);

            // C - SL.NO
            sheet.setColumnWidth(2, 10 * 256);

            // D - Start Date
            sheet.setColumnWidth(3, 22 * 256);

            // E - End Date
            sheet.setColumnWidth(4, 22 * 256);

            // F - Paddy ID
            sheet.setColumnWidth(5, 12 * 256);

            // G - Material
            sheet.setColumnWidth(6, 28 * 256);

            // H - Paddy
            sheet.setColumnWidth(7, 15 * 256);

            // I - Rice
            sheet.setColumnWidth(8, 15 * 256);

            // J - Broken
            sheet.setColumnWidth(9, 15 * 256);

            // K - KWH
            sheet.setColumnWidth(10, 15 * 256);

            // L - Rice %
            sheet.setColumnWidth(11, 15 * 256);

            // M - Broken %
            sheet.setColumnWidth(12, 15 * 256);

            // N - Loss
            sheet.setColumnWidth(13, 15 * 256);

            // O - Loss %
            sheet.setColumnWidth(14, 15 * 256);


            // =========================================================
            // TITLE
            // =========================================================

            Row titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(30);

            Cell titleCell = titleRow.createCell(2);
            titleCell.setCellValue("ANAMMALAYAR RICE MILL");
            titleCell.setCellStyle(createTitleStyle(workbook));

            sheet.addMergedRegion(
                    new CellRangeAddress(0, 0, 2, 14)
            );


            // =========================================================
            // SUB TITLE
            // =========================================================

            Row subTitleRow = sheet.createRow(1);
            subTitleRow.setHeightInPoints(22);

            Cell subTitleCell = subTitleRow.createCell(2);
            subTitleCell.setCellValue("HULLER BATCH REPORT");
            subTitleCell.setCellStyle(createSubTitleStyle(workbook));

            sheet.addMergedRegion(
                    new CellRangeAddress(1, 1, 2, 14)
            );


            // =========================================================
            // EMPTY ROW
            // =========================================================

            sheet.createRow(2);


            // =========================================================
            // HEADER
            // =========================================================

            Row headerRow = sheet.createRow(3);
            headerRow.setHeightInPoints(30);

            String[] headers = {
                    "SL.NO",
                    "Start Date & Time",
                    "End Date & Time",
                    "Paddy ID",
                    "Material",
                    "Paddy (Kg)",
                    "Rice (Kg)",
                    "Broken (Kg)",
                    "Energy (KWh)",
                    "Rice (%)",
                    "Broken (%)",
                    "Loss (Kg)",
                    "Loss (%)"
            };

            CellStyle headerStyle =
                    createHeaderStyle(workbook);

            // Start from Column C = index 2
            for (int i = 0; i < headers.length; i++) {

                Cell cell = headerRow.createCell(i + 2);

                cell.setCellValue(headers[i]);

                cell.setCellStyle(headerStyle);
            }


            // =========================================================
            // DATA
            // =========================================================

            CellStyle dateStyle =
                    createDateStyle(workbook);

            CellStyle numberStyle =
                    createNumberStyle(workbook);

            CellStyle integerStyle =
                    createIntegerStyle(workbook);

            CellStyle textStyle =
                    createTextStyle(workbook);

            int rowIndex = 4;

            int serialNo = 1;

            for (HullerReportDto report : reports) {

                Row row = sheet.createRow(rowIndex++);


                // -----------------------------------------------------
                // C - SL.NO
                // -----------------------------------------------------

                Cell slNoCell = row.createCell(2);

                slNoCell.setCellValue(serialNo++);

                slNoCell.setCellStyle(integerStyle);


                // -----------------------------------------------------
                // D - Start Date & Time
                // -----------------------------------------------------

                Cell startCell = row.createCell(3);

                if (report.getStartDateTime() != null) {

                    startCell.setCellValue(
                            report.getStartDateTime()
                                    .format(DATE_TIME_FORMAT)
                    );
                }

                startCell.setCellStyle(dateStyle);


                // -----------------------------------------------------
                // E - End Date & Time
                // -----------------------------------------------------

                Cell endCell = row.createCell(4);

                if (report.getEndDateTime() != null) {

                    endCell.setCellValue(
                            report.getEndDateTime()
                                    .format(DATE_TIME_FORMAT)
                    );
                }

                endCell.setCellStyle(dateStyle);


                // -----------------------------------------------------
                // F - Paddy ID
                // -----------------------------------------------------

                Cell paddyIdCell = row.createCell(5);

                paddyIdCell.setCellValue(
                        report.getPaddyId() != null
                                ? report.getPaddyId()
                                : 0
                );

                paddyIdCell.setCellStyle(integerStyle);


                // -----------------------------------------------------
                // G - Material
                // -----------------------------------------------------

                Cell materialCell = row.createCell(6);

                materialCell.setCellValue(
                        report.getMaterial() != null
                                ? report.getMaterial()
                                : ""
                );

                materialCell.setCellStyle(textStyle);


                // -----------------------------------------------------
                // H - Paddy
                // -----------------------------------------------------

                Cell paddyCell = row.createCell(7);

                paddyCell.setCellValue(
                        report.getPaddy() != null
                                ? report.getPaddy()
                                : 0
                );

                paddyCell.setCellStyle(numberStyle);


                // -----------------------------------------------------
                // I - Rice
                // -----------------------------------------------------

                Cell riceCell = row.createCell(8);

                riceCell.setCellValue(
                        report.getRice() != null
                                ? report.getRice()
                                : 0
                );

                riceCell.setCellStyle(numberStyle);


                // -----------------------------------------------------
                // J - Broken
                // -----------------------------------------------------

                Cell brokenCell = row.createCell(9);

                brokenCell.setCellValue(
                        report.getBroken() != null
                                ? report.getBroken()
                                : 0
                );

                brokenCell.setCellStyle(numberStyle);


                // -----------------------------------------------------
                // K - KWH
                // -----------------------------------------------------

                Cell kwhCell = row.createCell(10);

                kwhCell.setCellValue(
                        report.getKwh() != null
                                ? report.getKwh()
                                : 0
                );

                kwhCell.setCellStyle(numberStyle);


                // -----------------------------------------------------
                // L - Rice %
                // -----------------------------------------------------

                Cell ricePercentCell = row.createCell(11);

                ricePercentCell.setCellValue(
                        report.getRicePercent() != null
                                ? report.getRicePercent()
                                : 0
                );

                ricePercentCell.setCellStyle(numberStyle);


                // -----------------------------------------------------
                // M - Broken %
                // -----------------------------------------------------

                Cell brokenPercentCell = row.createCell(12);

                brokenPercentCell.setCellValue(
                        report.getBrokenPercent() != null
                                ? report.getBrokenPercent()
                                : 0
                );

                brokenPercentCell.setCellStyle(numberStyle);


                // -----------------------------------------------------
                // N - Loss
                // -----------------------------------------------------

                Cell lossCell = row.createCell(13);

                lossCell.setCellValue(
                        report.getLoss() != null
                                ? report.getLoss()
                                : 0
                );

                lossCell.setCellStyle(numberStyle);


                // -----------------------------------------------------
                // O - Loss %
                // -----------------------------------------------------

                Cell lossPercentCell = row.createCell(14);

                lossPercentCell.setCellValue(
                        report.getLossPercent() != null
                                ? report.getLossPercent()
                                : 0
                );

                lossPercentCell.setCellStyle(numberStyle);
            }


            // =========================================================
            // TOTAL ROW
            // =========================================================

            int totalRowIndex = rowIndex;

            Row totalRow = sheet.createRow(totalRowIndex);

            totalRow.setHeightInPoints(25);

            CellStyle totalLabelStyle =
                    createTotalLabelStyle(workbook);

            CellStyle totalNumberStyle =
                    createTotalNumberStyle(workbook);


   
// C:G - Merge TOTAL cells
Cell totalLabelCell = totalRow.createCell(2);
totalLabelCell.setCellValue("TOTAL");
totalLabelCell.setCellStyle(totalLabelStyle);

CellRangeAddress totalMergeRange =
        new CellRangeAddress(
                totalRowIndex,
                totalRowIndex,
                2,  // C
                6   // G
        );

sheet.addMergedRegion(totalMergeRange);

// Apply border around the complete merged C:G area
RegionUtil.setBorderTop(
        BorderStyle.MEDIUM, totalMergeRange, sheet
);
RegionUtil.setBorderBottom(
        BorderStyle.MEDIUM, totalMergeRange, sheet
);
RegionUtil.setBorderLeft(
        BorderStyle.THIN, totalMergeRange, sheet
);
RegionUtil.setBorderRight(
        BorderStyle.THIN, totalMergeRange, sheet
);


            // ---------------------------------------------------------
            // H - Paddy Total
            // ---------------------------------------------------------

            Cell totalPaddyCell = totalRow.createCell(7);

            totalPaddyCell.setCellFormula(
                    "SUM(H5:H" + totalRowIndex + ")"
            );

            totalPaddyCell.setCellStyle(totalNumberStyle);


            // ---------------------------------------------------------
            // I - Rice Total
            // ---------------------------------------------------------

            Cell totalRiceCell = totalRow.createCell(8);

            totalRiceCell.setCellFormula(
                    "SUM(I5:I" + totalRowIndex + ")"
            );

            totalRiceCell.setCellStyle(totalNumberStyle);


            // ---------------------------------------------------------
            // J - Broken Total
            // ---------------------------------------------------------

            Cell totalBrokenCell = totalRow.createCell(9);

            totalBrokenCell.setCellFormula(
                    "SUM(J5:J" + totalRowIndex + ")"
            );

            totalBrokenCell.setCellStyle(totalNumberStyle);


            // ---------------------------------------------------------
            // K - KWH Total
            // ---------------------------------------------------------

            Cell totalKwhCell = totalRow.createCell(10);

            totalKwhCell.setCellFormula(
                    "SUM(K5:K" + totalRowIndex + ")"
            );

            totalKwhCell.setCellStyle(totalNumberStyle);


            // ---------------------------------------------------------
            // L - Rice %
            // ---------------------------------------------------------

            Cell totalRicePercentCell = totalRow.createCell(11);

            totalRicePercentCell.setCellFormula(
                    "IF(H" + (totalRowIndex + 1)
                            + "=0,0,I" + (totalRowIndex + 1)
                            + "/H" + (totalRowIndex + 1)
                            + "*100)"
            );

            totalRicePercentCell.setCellStyle(
                    totalNumberStyle
            );


            // ---------------------------------------------------------
            // M - Broken %
            // ---------------------------------------------------------

            Cell totalBrokenPercentCell =
                    totalRow.createCell(12);

            totalBrokenPercentCell.setCellFormula(
                    "IF(H" + (totalRowIndex + 1)
                            + "=0,0,J" + (totalRowIndex + 1)
                            + "/H" + (totalRowIndex + 1)
                            + "*100)"
            );

            totalBrokenPercentCell.setCellStyle(
                    totalNumberStyle
            );


            // ---------------------------------------------------------
            // N - Loss
            // ---------------------------------------------------------

            Cell totalLossCell = totalRow.createCell(13);

            totalLossCell.setCellFormula(
                    "H" + (totalRowIndex + 1)
                            + "-I" + (totalRowIndex + 1)
                            + "-J" + (totalRowIndex + 1)
            );

            totalLossCell.setCellStyle(
                    totalNumberStyle
            );


            // ---------------------------------------------------------
            // O - Loss %
            // ---------------------------------------------------------

            Cell totalLossPercentCell =
                    totalRow.createCell(14);

            totalLossPercentCell.setCellFormula(
                    "IF(H" + (totalRowIndex + 1)
                            + "=0,0,N" + (totalRowIndex + 1)
                            + "/H" + (totalRowIndex + 1)
                            + "*100)"
            );

            totalLossPercentCell.setCellStyle(
                    totalNumberStyle
            );


            // =========================================================
            // FREEZE HEADER
            // =========================================================

            // Freeze rows above header + columns A & B
            sheet.createFreezePane(2, 4);


            // =========================================================
            // FILTER
            // =========================================================

            if (!reports.isEmpty()) {

                sheet.setAutoFilter(
                        new CellRangeAddress(
                                3,
                                3 + reports.size(),
                                2,
                                14
                        )
                );
            }


            // =========================================================
            // WRITE FILE
            // =========================================================

            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
    }


    // =============================================================
    // TITLE STYLE
    // =============================================================

    private CellStyle createTitleStyle(
            Workbook workbook) {

        CellStyle style =
                workbook.createCellStyle();

        Font font =
                workbook.createFont();

        font.setBold(true);
        font.setFontHeightInPoints((short) 18);

        style.setFont(font);

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        return style;
    }


    // =============================================================
    // SUBTITLE STYLE
    // =============================================================

    private CellStyle createSubTitleStyle(
            Workbook workbook) {

        CellStyle style =
                workbook.createCellStyle();

        Font font =
                workbook.createFont();

        font.setBold(true);
        font.setFontHeightInPoints((short) 13);

        style.setFont(font);

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        return style;
    }


    // =============================================================
    // HEADER STYLE
    // =============================================================

    private CellStyle createHeaderStyle(
            Workbook workbook) {

        CellStyle style =
                workbook.createCellStyle();

        Font font =
                workbook.createFont();

        font.setBold(true);
        font.setFontHeightInPoints((short) 11);

        // Black text
        font.setColor(
                IndexedColors.BLACK.getIndex()
        );

        style.setFont(font);

        // Light blue background
        style.setFillForegroundColor(
                IndexedColors.LIGHT_BLUE.getIndex()
        );

        style.setFillPattern(
                FillPatternType.SOLID_FOREGROUND
        );

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        style.setWrapText(true);

        addBorders(style);

        return style;
    }


    // =============================================================
    // DATE STYLE
    // =============================================================

    private CellStyle createDateStyle(
            Workbook workbook) {

        CellStyle style =
                workbook.createCellStyle();

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        addBorders(style);

        return style;
    }


    // =============================================================
    // NUMBER STYLE
    // =============================================================

    private CellStyle createNumberStyle(
            Workbook workbook) {

        CellStyle style =
                workbook.createCellStyle();

        style.setAlignment(
                HorizontalAlignment.RIGHT
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        style.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("0.00")
        );

        addBorders(style);

        return style;
    }


    // =============================================================
    // INTEGER STYLE
    // =============================================================

    private CellStyle createIntegerStyle(
            Workbook workbook) {

        CellStyle style =
                workbook.createCellStyle();

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        style.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("0")
        );

        addBorders(style);

        return style;
    }


    // =============================================================
    // TEXT STYLE
    // =============================================================

    private CellStyle createTextStyle(
            Workbook workbook) {

        CellStyle style =
                workbook.createCellStyle();

        style.setAlignment(
                HorizontalAlignment.LEFT
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        addBorders(style);

        return style;
    }


    // =============================================================
    // TOTAL LABEL STYLE
    // =============================================================

private CellStyle createTotalLabelStyle(Workbook workbook) {

    XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();

    Font font = workbook.createFont();
    font.setBold(true);
    font.setFontHeightInPoints((short) 11);
    font.setColor(IndexedColors.BLACK.getIndex());
    style.setFont(font);

    // Very light blue
    style.setFillForegroundColor(
        new XSSFColor(new byte[] {
            (byte) 221,
            (byte) 235,
            (byte) 247
        })
    );

    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);

    return style;
}
    // =============================================================
    // TOTAL NUMBER STYLE
    // =============================================================

  private CellStyle createTotalNumberStyle(Workbook workbook) {

    XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();

    Font font = workbook.createFont();
    font.setBold(true);
    font.setFontHeightInPoints((short) 11);
    font.setColor(IndexedColors.BLACK.getIndex());
    style.setFont(font);

    // Very light blue
    style.setFillForegroundColor(
        new XSSFColor(new byte[] {
            (byte) 221,
            (byte) 235,
            (byte) 247
        })
    );
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    style.setAlignment(HorizontalAlignment.RIGHT);
    style.setVerticalAlignment(VerticalAlignment.CENTER);

    style.setDataFormat(
        workbook.createDataFormat().getFormat("0.00")
    );

    style.setBorderTop(BorderStyle.MEDIUM);
    style.setBorderBottom(BorderStyle.MEDIUM);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);

    return style;
}


    // =============================================================
    // COMMON BORDERS
    // =============================================================

    private void addBorders(CellStyle style) {

        style.setBorderTop(
                BorderStyle.THIN
        );

        style.setBorderBottom(
                BorderStyle.THIN
        );

        style.setBorderLeft(
                BorderStyle.THIN
        );

        style.setBorderRight(
                BorderStyle.THIN
        );
    }
}