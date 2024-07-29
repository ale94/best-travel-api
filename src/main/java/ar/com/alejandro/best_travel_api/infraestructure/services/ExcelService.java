package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.domain.entities.CustomerEntity;
import ar.com.alejandro.best_travel_api.domain.repositories.CustomerRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ExcelService implements ReportService {

    private final CustomerRepository customerRepository;

    @Override
    public byte[] readFile() {
        return new byte[0];
    }

    private void createReport() {
        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet(SHEET_NAME);

        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 3000);

        var header = sheet.createRow(0);
        var headerStyle = workbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.VIOLET.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        var font = workbook.createFont();
        font.setFontName(FONT_TYPE);
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        var headerCell = header.createCell(0);
        headerCell.setCellValue(COLUMN_CUSTOMER_ID);
        header.setRowStyle(headerStyle);

        header.createCell(1);
        headerCell.setCellValue(COLUMN_CUSTOMER_NAME);
        header.setRowStyle(headerStyle);

        header.createCell(2);
        headerCell.setCellValue(COLUMN_CUSTOMER_PURCHASES);
        header.setRowStyle(headerStyle);

        var style = workbook.createCellStyle();
        style.setWrapText(true);

        var customers = this.customerRepository.findAll();
        var rowPos = 1;
        for (CustomerEntity customer : customers) {
            var row = sheet.createRow(rowPos);
            var cell = row.createCell(0);
            cell.setCellValue(customer.getDni());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(customer.getFullName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(getTotalPurchase(customer));
            cell.setCellStyle(style);

            rowPos++;
        }
    }

    private static int getTotalPurchase(CustomerEntity customer) {
        return customer.getTotalLodgings() + customer.getTotalFlights() + customer.getTotalTours();
    }

    private static final String SHEET_NAME = "Customer total sales";
    private static final String FONT_TYPE = "Arial";
    private static final String COLUMN_CUSTOMER_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "name";
    private static final String COLUMN_CUSTOMER_PURCHASES = "purchases";
    private static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
    private static final String REPORTS_PATH = "reports";
    private static final String FILE_TYPE = ".xlsx";
    private static final String FILE_NAME = "Sales-%s.xlsx";

}
