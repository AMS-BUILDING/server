package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusApartmentBilling;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.BillingApartmentTotalResponse;
import com.ams.building.server.service.BillingApartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.HelperUtils.formatDoubleNUmber;

@Service
public class BillingApartmentServiceImpl implements BillingApartmentService {

    private static final Logger logger = Logger.getLogger(BillingApartmentServiceImpl.class);

    @Autowired
    private ApartmentBillingDAO apartmentBillingDAO;

    @Override
    public ApiResponse searchBuildingApartmentByMonth(Integer page, Integer size, String month) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApartmentBilling> apartmentBillings = apartmentBillingDAO.searchApartmentBillingByMonth(month, pageable);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        Long totalPage = apartmentBillings.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(billingTotalResponse).totalElement(totalPage).build();
        return response;
    }

    @Override
    public void downloadBillingApartmentByMonth(HttpServletResponse response, Integer page, Integer size, String month) {
//        if (DateTimeUtils.convertStringToDate(month, DateTimeUtils.MM_YYYY) == null) {
//            throw new RestApiException(StatusCode.DATETIME_INCORRECT_FORMAT);
//        }
//        try {
//            String[] date = month.split("/");
//            String fileName = URLEncoder.encode(String.format("AMSBUILDING_FREE_APARTMENT_%s_%s.xlsx", date[1], date[0]), "UTF-8");
//            response.setContentType("application/ms-excel; charset=UTF-8");
//            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
//            response.setHeader(Constants.HEADER_KEY, headerValue);
//            writeBillingApartmentByMonth(response, header, invoiceList);
//        } catch (Exception ex) {
//            logger.error("Method writeCustomerDetailInvoiceExcel with Exception = ", ex);
//        }
    }

//    private void writeBillingApartmentByMonth(HttpServletResponse response, Moconavi050Invoice header, List<Moconavi050Invoice> invoiceList) {
//        try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream os = response.getOutputStream()) {
//            createInvoiceExcelByContract(workbook, header, invoiceList);
//            workbook.write(os);
//        } catch (Exception ex) {
//            logger.error("Method writeCustomerDetailInvoiceExcel with Exception = ", ex);
//        }
//    }
//
//    private void createInvoiceExcelByContract(XSSFWorkbook workbook, Moconavi050Invoice header, List<Moconavi050Invoice> invoiceList) {
//        Sheet sheet = workbook.createSheet("請求明細");
//        sheet.setColumnWidth(0, 10000);
//        sheet.setColumnWidth(1, 10000);
//        sheet.setColumnWidth(2, 4000);
//        sheet.setColumnWidth(3, 4000);
//
//        // Title
//        Row row = sheet.createRow(0);
//
//        CellStyle style = workbook.createCellStyle();
//        style.setWrapText(true);
//        XSSFFont font = workbook.createFont();
//        font.setFontName("Arial");
//        font.setBold(true);
//        style.setFont(font);
//
//        Cell cell = row.createCell(0);
//        cell.setCellValue("moconavi050請求書別紙明細");
//        cell.setCellStyle(style);
//
//        // Contract Code
//        row = sheet.createRow(2);
//
//        style = workbook.createCellStyle();
//        style.setWrapText(true);
//
//        cell = row.createCell(0);
//        cell.setCellValue(Constants.FixedContent.CONTRACT_CODE);
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        cell.setCellValue(header.getMoconaviContractCode());
//        cell.setCellStyle(style);
//
//        // Company Name
//        row = sheet.createRow(3);
//
//        style = workbook.createCellStyle();
//        style.setWrapText(true);
//
//        cell = row.createCell(0);
//        cell.setCellValue(Constants.FixedContent.COMPANY_NAME);
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        cell.setCellValue(header.getCompanyName());
//        cell.setCellStyle(style);
//
//        // Moconavi Plan Name
//        row = sheet.createRow(4);
//
//        style = workbook.createCellStyle();
//        style.setWrapText(true);
//
//        cell = row.createCell(0);
//        cell.setCellValue(Constants.FixedContent.PLAN_NAME);
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        cell.setCellValue(header.getMoconaviPlanName());
//        cell.setCellStyle(style);
//
//        // Get Billing Month
//        Date billingMonth = DateUtils.convertStringToDate(header.getBillingMonth(), Constants.DateFormat.YYYY_MM);
//        Calendar calendar = Calendar.getInstance();
//        if (billingMonth != null) {
//            calendar.setTime(billingMonth);
//        }
//        calendar.add(Calendar.MONTH, -1);
//
//        // Start Date
//        row = sheet.createRow(5);
//
//        style = workbook.createCellStyle();
//        style.setWrapText(true);
//
//        cell = row.createCell(0);
//        cell.setCellValue(Constants.FixedContent.START_DATE);
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
//        Date startDate = calendar.getTime();
//        cell.setCellValue(DateUtils.convertDateToStringWithTimezone(startDate, Constants.DateFormat.YYYYMMDD, null));
//        cell.setCellStyle(style);
//
//        // End Date
//        row = sheet.createRow(6);
//        style = workbook.createCellStyle();
//        style.setWrapText(true);
//
//        cell = row.createCell(0);
//        cell.setCellValue(Constants.FixedContent.END_DATE);
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Date endDate = calendar.getTime();
//        cell.setCellValue(DateUtils.convertDateToStringWithTimezone(endDate, Constants.DateFormat.YYYYMMDD, null));
//        cell.setCellStyle(style);
//
//        // Table
//        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//        font = workbook.createFont();
//        font.setFontName("Arial");
//        headerStyle.setFont(font);
//        headerStyle.setBorderBottom(BorderStyle.THIN);
//        headerStyle.setBorderTop(BorderStyle.THIN);
//        headerStyle.setBorderRight(BorderStyle.THIN);
//        headerStyle.setBorderLeft(BorderStyle.THIN);
//
//        // Set headers
//        row = sheet.createRow(8);
//        Cell headerCell = row.createCell(0);
//        headerCell.setCellValue(Constants.FixedContent.NUMBER_050);
//        headerCell.setCellStyle(headerStyle);
//
//        headerCell = row.createCell(1);
//        headerCell.setCellValue(Constants.FixedContent.CATEGORY_NAME);
//        headerCell.setCellStyle(headerStyle);
//
//        headerCell = row.createCell(2);
//        headerCell.setCellValue(Constants.FixedContent.PRICE);
//        headerCell.setCellStyle(headerStyle);
//
//        headerCell = row.createCell(3);
//        headerCell.setCellValue(Constants.FixedContent.TAX);
//        headerCell.setCellStyle(headerStyle);
//
//        // Set value
//        style = workbook.createCellStyle();
//        style.setWrapText(true);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//
//        Map<String, Moconavi050Invoice> number050AndCategoryMap = new HashMap<>();
//        for (Moconavi050Invoice invoice : invoiceList) {
//            if (invoice.getFee() == null || invoice.getFee() == 0) continue;
//            Moconavi050Invoice moconavi050Invoice = number050AndCategoryMap.get(invoice.getKeyByNumber050AndCategoryName());
//            if (moconavi050Invoice == null) {
//                moconavi050Invoice = new Moconavi050Invoice();
//                BeanUtils.copyProperties(invoice, moconavi050Invoice);
//            } else {
//                moconavi050Invoice.setFee(moconavi050Invoice.getFee() + invoice.getFee());
//            }
//            number050AndCategoryMap.put(invoice.getKeyByNumber050AndCategoryName(), moconavi050Invoice);
//        }
//
//        int i = 8;
//        double totalMoneyWithTax = 0d;
//        double totalMoneyWithoutTax = 0d;
//        List<Moconavi050Invoice> exportList = new ArrayList<>(number050AndCategoryMap.values());
//        exportList.sort(Comparator.comparing(Moconavi050Invoice::getNumber050));
//        for (Moconavi050Invoice invoice : exportList) {
//            row = sheet.createRow(++i);
//            cell = row.createCell(0);
//            cell.setCellStyle(style);
//            cell.setCellValue((invoice.getNumber050() == null) ? "" : invoice.getNumber050());
//
//            cell = row.createCell(1);
//            cell.setCellStyle(style);
//            cell.setCellValue((invoice.getMoconaviCategoryDisplayName() == null) ? "" : invoice.getMoconaviCategoryDisplayName());
//
//            cell = row.createCell(2);
//            cell.setCellStyle(style);
//            cell.setCellValue(invoice.getFee());
//
//            cell = row.createCell(3);
//            cell.setCellStyle(style);
//            if (Boolean.TRUE.equals(invoice.getTax())) {
//                totalMoneyWithTax += invoice.getFee();
//            } else {
//                cell.setCellValue("非課税");
//                totalMoneyWithoutTax += invoice.getFee();
//            }
//        }
//
//        // Set Style Total Money
//        style = workbook.createCellStyle();
//        style.setWrapText(true);
//
//        row = sheet.createRow(++i);
//        cell = row.createCell(1);
//        cell.setCellStyle(style);
//        cell.setCellValue(Constants.FixedContent.TOTAL_PRICE_WITH_TAX);
//
//        cell = row.createCell(2);
//        cell.setCellStyle(style);
//        cell.setCellValue(totalMoneyWithTax);
//
//        cell = row.createCell(3);
//        cell.setCellStyle(style);
//        cell.setCellValue("円");
//
//        row = sheet.createRow(++i);
//        cell = row.createCell(1);
//        cell.setCellStyle(style);
//        cell.setCellValue(Constants.FixedContent.TOTAL_PRICE_WITHOUT_TAX);
//
//        cell = row.createCell(2);
//        cell.setCellStyle(style);
//        cell.setCellValue(totalMoneyWithoutTax);
//
//        cell = row.createCell(3);
//        cell.setCellStyle(style);
//        cell.setCellValue("円");
//    }

    private BillingApartmentTotalResponse convertToBillingTotalResponse(ApartmentBilling billing) {
        BillingApartmentTotalResponse response = BillingApartmentTotalResponse.builder().build();
        Apartment apartment = billing.getApartment();
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        FloorBlock floorBlock = roomNumber.getFloorBlock();
        if (Objects.isNull(floorBlock)) {
            throw new RestApiException(StatusCode.FLOOR_BLOCK_NOT_EXIST);
        }
        Block block = floorBlock.getBlock();
        if (Objects.isNull(block)) {
            throw new RestApiException(StatusCode.BLOCK_NOT_EXIST);
        }
        StatusApartmentBilling status = billing.getStatusApartmentBilling();
        if (Objects.isNull(status)) {
            throw new RestApiException(StatusCode.STATUS_NOT_EXIST);
        }
        response.setId(billing.getId());
        response.setBlockName(block.getBlockName());
        response.setRoomNumber(roomNumber.getRoomName());
        response.setTotalPrice(formatDoubleNUmber(billing.getTotalPrice()));
        response.setBillingMonth(billing.getBillingMonth());
        response.setStatusName(status.getStatusName());
        return response;
    }
}
