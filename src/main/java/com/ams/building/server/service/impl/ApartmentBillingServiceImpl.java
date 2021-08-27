package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.DetailApartmentBilling;
import com.ams.building.server.bean.RequestService;
import com.ams.building.server.bean.ResidentCard;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusApartmentBilling;
import com.ams.building.server.bean.VehicleCard;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DetailApartmentBillingDAO;
import com.ams.building.server.dao.RequestServiceDAO;
import com.ams.building.server.dao.ResidentCardDAO;
import com.ams.building.server.dao.VehicleCardDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.BillingDetailResponse;
import com.ams.building.server.response.BillingResponse;
import com.ams.building.server.response.BillingTotalResponse;
import com.ams.building.server.response.NotificationFeeApartmentResponse;
import com.ams.building.server.response.ServiceNameFeeApartmentResponse;
import com.ams.building.server.service.ApartmentBillingService;
import com.ams.building.server.service.EmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ams.building.server.utils.HelperUtils.formatCurrentMoney;
import static com.ams.building.server.utils.HelperUtils.formatDoubleNUmber;

@Service
public class ApartmentBillingServiceImpl implements ApartmentBillingService {

    private static final Logger logger = Logger.getLogger(ApartmentBillingServiceImpl.class);

    @Autowired
    private DetailApartmentBillingDAO detailApartmentBillingDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private ApartmentBillingDAO apartmentBillingDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VehicleCardDAO vehicleCardDAO;

    @Autowired
    private ResidentCardDAO residentCardDAO;

    @Autowired
    private RequestServiceDAO requestServiceDAO;

    @Override
    public NotificationFeeApartmentResponse getListFeeBillingByMonthAndAccount(Long accountId, String billingMonth) {

        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(billingMonth)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(accountId);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }

        ApartmentBilling billing = apartmentBillingDAO.getDetailByMonth(billingMonth, accountId);

        if (Objects.isNull(billing)) {
            throw new RestApiException(StatusCode.BILLING_MONTH_NOT_RIGHT);
        }

        Double feeVehicle = detailApartmentBillingDAO.feeVehicleCardByAccountIdAndMonth(accountId, billingMonth);
        Double feeApartment = detailApartmentBillingDAO.feeResidentCardByAccountIdAndMonth(accountId, billingMonth);
        List<ServiceNameFeeApartmentResponse> services = detailApartmentBillingDAO.feeServiceNameByAccountIdAndMonth(accountId, billingMonth);
        List<DetailApartmentBilling> billings = detailApartmentBillingDAO.detailApartmentBillingByAccountIdAndMonth(accountId, billingMonth);

        Apartment apartment = apartmentDAO.getApartmentByAccountId(account.getId());
        String apartmentSquarMetter = apartment.getRoomNumber().getTypeApartment().getTypeName();
        Long squarMetter = Long.valueOf(apartmentSquarMetter);
        Long fee = Long.valueOf(Constants.GeneralSerivce.FEE_GENERAL_SERVICE) * squarMetter;

        NotificationFeeApartmentResponse response = NotificationFeeApartmentResponse.builder().build();
        response.setGeneralFeeName(Constants.NotificationFeeName.FEE_GENERAL_NAME);
        response.setFeeGeneralFee(fee.toString());

        response.setVehicleName(Constants.NotificationFeeName.FEE_VEHICLE_NAME);
        response.setFeeVehicleName(formatDoubleNUmber(feeVehicle));

        if (feeApartment != 0F) {
            response.setApartmentCardName(Constants.NotificationFeeName.FEE_APARTMENT_CARD_NAME);
            response.setFeeApartmentCard(formatDoubleNUmber(feeApartment));
        }
        response.setId(billings.get(0).getApartmentBilling().getId());
        Double total = billings.get(0).getApartmentBilling().getTotalPrice();
        Long totalFee = total.longValue() + fee;
        response.setTotal(totalFee.toString());
        Long statusId = billings.get(0).getApartmentBilling().getStatusApartmentBilling().getId();
        String month = billingMonth.split("/")[0];
        String year = billingMonth.split("/")[1];
        String monthNext = String.valueOf(Integer.valueOf(month) + 1);
        if (monthNext.length() == 1) {
            monthNext = "0" + monthNext;
        }
        if (month.equals("12")) {
            monthNext = "01";
            year = String.valueOf(Integer.valueOf(year) + 1);
        }
        response.setStatus(statusId == 1 ? "Hạn chót thanh toán 10/" + monthNext + "/" + year : "Đã thanh toán");
        List<ServiceNameFeeApartmentResponse> servicesResponse = new ArrayList<>();
        services.forEach(s -> {
            if (s.getServiceName() != null) {
                servicesResponse.add(s);
            }
        });
        response.setServices(servicesResponse);
        response.setBillingMonth(billingMonth);
        return response;
    }

    @Transactional
    @Override
    public void checkAndInsertBillingInMonth() {
        // Insert 5 table
        // vehicle card,resident card, service request, apartment billing, detail apartment
        // lay them thong tin cua request service

        // Get Last Month
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String billingMonth = "";
        if (month == 0) {
            billingMonth = "12/" + (year - 1);
        } else if (month <= 9) {
            billingMonth = "0" + month + "/" + year;
        } else {
            billingMonth = month + "/" + year;
        }

        // Current Month
        String billingMonthCurrent;
        if (month >= 0 && month <= 8) {
            billingMonthCurrent = "0" + (month + 1) + "/" + year;
        } else {
            billingMonthCurrent = (month + 1) + "/" + year;
        }

        // 1. Vehicle card
        List<VehicleCard> vehicleCardByBillingMonth = vehicleCardDAO.vehicleCardByBillingMonth(billingMonth);
        for (VehicleCard vehicleCard : vehicleCardByBillingMonth) {
            VehicleCard newVehicleCard = new VehicleCard();
            newVehicleCard.setVehicle(vehicleCard.getVehicle());
            newVehicleCard.setAccount(vehicleCard.getAccount());
            newVehicleCard.setStatusVehicleCard(vehicleCard.getStatusVehicleCard());
            newVehicleCard.setVehicleBranch(vehicleCard.getVehicleBranch());
            newVehicleCard.setLicensePlate(vehicleCard.getLicensePlate());
            newVehicleCard.setVehicleColor(vehicleCard.getVehicleColor());
            newVehicleCard.setBillingMonth(billingMonthCurrent);
            newVehicleCard.setIsUse(1);
            newVehicleCard.setStartDate(new Date());
            // add vehicle card to month
            vehicleCardDAO.save(newVehicleCard);
        }

        // 2. Resident card
        List<ResidentCard> residentCardByBillingMonth = residentCardDAO.residentCardByBillingMonth(billingMonth);
        for (ResidentCard residentCard : residentCardByBillingMonth) {
            ResidentCard newResidentCard = new ResidentCard();
            newResidentCard.setAccount(residentCard.getAccount());
            newResidentCard.setBillingMonth(billingMonthCurrent);
            newResidentCard.setIsUse(1);
            newResidentCard.setStatusResidentCard(residentCard.getStatusResidentCard());
            newResidentCard.setCardCode(residentCard.getCardCode());
            if (residentCard.getStatusResidentCard().getId() == 3) {
                newResidentCard.setPrice(0D);
            } else {
                newResidentCard.setPrice(residentCard.getPrice());
            }
            // add resident card to month
            residentCardDAO.save(newResidentCard);
        }

        // 3. Request Service
        List<RequestService> serviceRequestNotSuccessByMonth = requestServiceDAO.serviceRequestNotSuccessByMonth(billingMonth);
        for (RequestService requestService : serviceRequestNotSuccessByMonth) {
            RequestService newRequest = new RequestService();
            newRequest.setBillingMonth(billingMonthCurrent);
            newRequest.setStatusServiceRequest(requestService.getStatusServiceRequest());
            newRequest.setAccount(requestService.getAccount());
            if (requestService.getStartDate() != null) {
                newRequest.setStartDate(requestService.getStartDate());
            }
            if (requestService.getEndDate() != null) {
                newRequest.setEndDate(requestService.getEndDate());
            }
            if (requestService.getDateRequest() != null) {
                newRequest.setDateRequest(requestService.getDateRequest());
            }
            newRequest.setReasonDetailSubService(requestService.getReasonDetailSubService());
            requestServiceDAO.save(newRequest);
        }

        // Get thong tin
        // 1. Get thong tin tu bang vehicle+ resident + request  : account id + tien + status success + billing month
        List<VehicleCard> vehicleCardsCalculatePrice = vehicleCardDAO.vehicleCardByBillingMonthAndStatus(billingMonth);
        List<ResidentCard> residentCardCalculatePrice = residentCardDAO.residentCardByBillingMonthAndStatus(billingMonth);
        List<RequestService> requestServiceCalculatePrice = requestServiceDAO.requestServiceByBillingMonthAndStatus(billingMonth);

        // 2. Insert table billing detail
        // insert vehicle to billing detail
        List<BillingResponse> billingResponses = new ArrayList<>();
        for (VehicleCard card : vehicleCardsCalculatePrice) {
            DetailApartmentBilling billing = new DetailApartmentBilling();
            billing.setAccount(card.getAccount());
            billing.setVehicleCard(card);
            billing.setVehicleName(card.getVehicleBranch());
            billing.setVehiclePrice(card.getVehicle().getPrice());
            billing.setBillingMonth(billingMonth);
            detailApartmentBillingDAO.save(billing);
            BillingResponse response = BillingResponse.builder().build();
            response.setAccountId(card.getAccount().getId());
            response.setPrice(card.getVehicle().getPrice());
            billingResponses.add(response);
        }
        // Insert resident card to billing detail
        for (ResidentCard card : residentCardCalculatePrice) {
            DetailApartmentBilling billing = new DetailApartmentBilling();
            billing.setAccount(card.getAccount());
            billing.setBillingMonth(billingMonth);
            billing.setResidentCard(card);
            billing.setResidentCode(card.getCardCode());
            billing.setResidentPrice(card.getPrice());
            detailApartmentBillingDAO.save(billing);
            BillingResponse response = BillingResponse.builder().build();
            response.setAccountId(card.getAccount().getId());
            response.setPrice(card.getPrice());
            billingResponses.add(response);
        }
        // Insert service to billing detail
        for (RequestService requestService : requestServiceCalculatePrice) {
            DetailApartmentBilling billing = new DetailApartmentBilling();
            billing.setAccount(requestService.getAccount());
            billing.setBillingMonth(billingMonth);
            billing.setReasonDetailSubService(requestService.getReasonDetailSubService());
            billing.setSubServicePrice(requestService.getReasonDetailSubService().getPrice());
            billing.setSubServiceName(requestService.getReasonDetailSubService().getReasonName());
            detailApartmentBillingDAO.save(billing);
            BillingResponse response = BillingResponse.builder().build();
            response.setAccountId(requestService.getAccount().getId());
            response.setPrice(requestService.getReasonDetailSubService().getPrice());
            billingResponses.add(response);
        }

        // Insert apartment billing
        List<BillingTotalResponse> totalResponses = new ArrayList<>();
        Map<Long, List<BillingResponse>> result = billingResponses.stream().collect(Collectors.groupingBy(BillingResponse::getAccountId));
        for (Map.Entry<Long, List<BillingResponse>> entry : result.entrySet()) {
            List<BillingResponse> responses = entry.getValue();
            Double sum = responses.stream()
                    .mapToDouble(value -> value.getPrice())
                    .sum();
            BillingTotalResponse totalResponse = BillingTotalResponse.builder().build();
            Apartment apartment = apartmentDAO.getApartmentByAccountId(responses.get(0).getAccountId());
            totalResponse.setApartmentId(apartment.getId());
            totalResponse.setTotalPrice(sum);
            totalResponses.add(totalResponse);
        }
        // Insert Apartment
        StatusApartmentBilling status = new StatusApartmentBilling();
        status.setId(1L);
        List<BillingDetailResponse> responses = new ArrayList<>();
        for (BillingTotalResponse response : totalResponses) {
            ApartmentBilling apartmentBilling = new ApartmentBilling();
            Apartment apartment = apartmentDAO.getById(response.getApartmentId());
            apartmentBilling.setApartment(apartment);
            apartmentBilling.setTotalPrice(response.getTotalPrice());
            apartmentBilling.setIsRead(false);
            apartmentBilling.setBillingMonth(billingMonth);
            apartmentBilling.setStatusApartmentBilling(status);
            ApartmentBilling addApartment = apartmentBillingDAO.save(apartmentBilling);
            BillingDetailResponse response1 = BillingDetailResponse.builder().build();
            response1.setApartmentId(addApartment.getId());
            response1.setAccountId(apartment.getAccount().getId());
            responses.add(response1);
        }

        for (BillingDetailResponse response : responses) {
            List<DetailApartmentBilling> detailApartmentBillingByAccountIdAndMonth = detailApartmentBillingDAO.detailApartmentBillingByAccountIdAndMonth(response.getAccountId(), billingMonth);
            for (DetailApartmentBilling billing : detailApartmentBillingByAccountIdAndMonth) {
                detailApartmentBillingDAO.updateDetailBilling(response.getApartmentId(), billing.getId());
            }
        }
    }

    @Override
    public void sendEmailToNotificationForResidentAboutFeeServiceInMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String billingMonth = "";
        if (month == 0) {
            billingMonth = "12/" + (year - 1);
        } else if (month <= 9) {
            billingMonth = "0" + month + "/" + year;
        } else {
            billingMonth = month + "/" + year;
        }

        List<ApartmentBilling> apartmentBillings = apartmentBillingDAO.listApartmentBillingByMonth(billingMonth);
        if (apartmentBillings.isEmpty()) {
            return;
        }

        // Write content to send mail
        for (ApartmentBilling billing : apartmentBillings) {
            Apartment apartment = billing.getApartment();
            Account account = apartment.getAccount();
            String email = account.getEmail();
            List<String> contents = createContentToSendMail(billing);
            try {
                emailService.sendSimpleMessage(email, contents.get(0), contents.get(1));
            } catch (MessagingException e) {
                logger.error(e);
            }
        }
    }

    @Override
    public void sendMailRemindForResident() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String billingMonth = "";
        if (month == 0) {
            billingMonth = "12/" + (year - 1);
        } else if (month <= 9) {
            billingMonth = "0" + month + "/" + year;
        } else {
            billingMonth = month + "/" + year;
        }

        List<ApartmentBilling> apartmentBillings = apartmentBillingDAO.listApartmentBillingByMonthNotPayment(billingMonth);
        if (apartmentBillings.isEmpty()) {
            return;
        }

        // Write content to send mail
        for (ApartmentBilling billing : apartmentBillings) {
            Apartment apartment = billing.getApartment();
            Account account = apartment.getAccount();
            String email = account.getEmail();
            List<String> contents = createContentToMailRemain(billing);
            try {
                emailService.sendSimpleMessage(email, contents.get(0), contents.get(1));
            } catch (MessagingException e) {
                logger.error(e);
            }
        }
    }

    private List<String> createContentToSendMail(ApartmentBilling billing) {
        String[] billingMonth = billing.getBillingMonth().split("/");
        String month = billingMonth[0];
        String year = billingMonth[1];
        String monthNext = String.valueOf(Integer.valueOf(month) + 1);
        if (monthNext.length() == 1) {
            monthNext = "0" + monthNext;
        }
        if (month.equals("12")) {
            monthNext = "01";
            year = String.valueOf(Integer.valueOf(year) + 1);
        }
        Apartment apartment = billing.getApartment();
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        Account account = apartment.getAccount();
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }

        Double feeVehicle = detailApartmentBillingDAO.feeVehicleCardByAccountIdAndMonth(account.getId(), billing.getBillingMonth());
        Double feeApartment = detailApartmentBillingDAO.feeResidentCardByAccountIdAndMonth(account.getId(), billing.getBillingMonth());
        List<ServiceNameFeeApartmentResponse> services = detailApartmentBillingDAO.feeServiceNameByAccountIdAndMonth(account.getId(), billing.getBillingMonth());
        List<DetailApartmentBilling> billings = detailApartmentBillingDAO.detailApartmentBillingByAccountIdAndMonth(account.getId(), billing.getBillingMonth());

        String apartmentSquarMetter = roomNumber.getTypeApartment().getTypeName();
        Long squarMetter = Long.valueOf(apartmentSquarMetter);
        Long fee = Long.valueOf(Constants.GeneralSerivce.FEE_GENERAL_SERVICE) * squarMetter;

        Double total = billings.get(0).getApartmentBilling().getTotalPrice();
        Long totalFee = total.longValue() + fee;

        List<ServiceNameFeeApartmentResponse> servicesResponse = new ArrayList<>();
        services.forEach(s -> {
            if (s.getServiceName() != null) {
                servicesResponse.add(s);
            }
        });

        String accountName = account.getName();
        String roomNumberStr = roomNumber.getRoomName();

        StringBuilder content = new StringBuilder();
        StringBuilder title = new StringBuilder();
        // Title
        title.append("[ABMS] THÔNG BÁO PHÍ THÁNG ");
        title.append(month + " Năm " + year + "_HẠN NỘP 10/" + monthNext + "/" + year);

        // content
        content.append("<p style=`color:red;font-size:20px;`><b> THÔNG BÁO PHÍ CĂN HỘ THÁNG " + month + " NĂM " + year + "</b></p>");
        content.append("<p> Thân gửi quý cư dân: <b>" + accountName + "</b></p>");
        content.append("<p>Mã căn hộ: <b>" + roomNumberStr + "</b></p>");
        content.append("<p style=`font-size:15px;`><b> Thông báo phí dịch vụ tháng  " + month + " năm " + year + "</b></p>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí dịch vụ chung: " + formatCurrentMoney(fee));
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí gửi xe : " + formatCurrentMoney(feeVehicle.longValue()));
        content.append("<br/>");
        if (feeApartment != 0F) {
            content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí thẻ căn hộ đăng kí thêm : " + formatCurrentMoney(feeApartment.longValue()));
            content.append("<br/>");
        }
        if (!servicesResponse.isEmpty()) {
            for (ServiceNameFeeApartmentResponse s : servicesResponse) {
                content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí dịch vụ " + s.getServiceName().toLowerCase() + " : " + formatCurrentMoney(s.getFeeService().longValue()));
                content.append("<br/>");
            }
        }
        content.append("<p> Tổng phí: <b style=`color:red;`>" +
                formatCurrentMoney(totalFee) + "</b></p>");
        content.append("<p> Thời hạn nộp tiền: <b style=`color:red;`>10/" + monthNext + "/" + year + "</b></p>");
        content.append("<p> <b>Hình thức nộp tiền </b></p>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; + Chuyển khoản qua STK:");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - Số TK: <b>0361000302156</b>");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - Tên TK: <b>NGUYỄN VĂN MẠNH</b>");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - Ngân hàng: Vietcombank chi nhánh Vĩnh Phúc");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; + Chuyển khoản qua ví điện tử: Momo, Viettel Pay <b><0964600609></b>");
        content.append("<p> <b>Nội dung thanh toán </b></p>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; +[Mã căn hô],[Số điện thoại],Nộp phí tháng ...");
        content.append("<br/>");
        content.append("<p> Nếu có thắc mắc về các khoản phí trên, quý cư dân gửi phản hồi qua ứng dụng. Chúng tôi sẽ tiếp nhận và xử lý</p>");
        List<String> result = new ArrayList<>();
        result.add(title.toString());
        result.add(content.toString());
        return result;
    }

    private List<String> createContentToMailRemain(ApartmentBilling billing) {
        String[] billingMonth = billing.getBillingMonth().split("/");
        String month = billingMonth[0];
        String year = billingMonth[1];
        String monthNext = String.valueOf(Integer.valueOf(month) + 1);
        if (monthNext.length() == 1) {
            monthNext = "0" + monthNext;
        }
        if (month.equals("12")) {
            monthNext = "01";
            year = String.valueOf(Integer.valueOf(year) + 1);
        }
        Apartment apartment = billing.getApartment();
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        Account account = apartment.getAccount();
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }

        Double feeVehicle = detailApartmentBillingDAO.feeVehicleCardByAccountIdAndMonth(account.getId(), billing.getBillingMonth());
        Double feeApartment = detailApartmentBillingDAO.feeResidentCardByAccountIdAndMonth(account.getId(), billing.getBillingMonth());
        List<ServiceNameFeeApartmentResponse> services = detailApartmentBillingDAO.feeServiceNameByAccountIdAndMonth(account.getId(), billing.getBillingMonth());
        List<DetailApartmentBilling> billings = detailApartmentBillingDAO.detailApartmentBillingByAccountIdAndMonth(account.getId(), billing.getBillingMonth());

        String apartmentSquarMetter = roomNumber.getTypeApartment().getTypeName();
        Long squarMetter = Long.valueOf(apartmentSquarMetter);
        Long fee = Long.valueOf(Constants.GeneralSerivce.FEE_GENERAL_SERVICE) * squarMetter;

        Double total = billings.get(0).getApartmentBilling().getTotalPrice();
        Long totalFee = total.longValue() + fee;

        List<ServiceNameFeeApartmentResponse> servicesResponse = new ArrayList<>();
        services.forEach(s -> {
            if (s.getServiceName() != null) {
                servicesResponse.add(s);
            }
        });

        String accountName = account.getName();
        String roomNumberStr = roomNumber.getRoomName();

        StringBuilder content = new StringBuilder();
        StringBuilder title = new StringBuilder();
        // Title
        title.append("[ABMS] THÔNG BÁO PHÍ NHẮC NHỞ VIỆC ĐÓNG PHÍ DỊCH VỤ THÁNG ");
        title.append(month + " Năm " + year + "_HẠN NỘP 10/" + monthNext + "/" + year);

        // content
        content.append("<p style=`color:red;font-size:20px;`><b> THÔNG BÁO NHẮC NHỞ VIỆC ĐÓNG PHÍ DỊCH VỤ THÁNG " + month + " NĂM " + year + "</b></p>");
        content.append("<p> Thân gửi quý cư dân: <b>" + accountName + "</b></p>");
        content.append("<p>Mã căn hộ: <b>" + roomNumberStr + "</b></p>");
        content.append("<p style=`font-size:15px;`><b> Thông báo phí dịch vụ tháng  " + month + " năm " + year + "</b></p>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí dịch vụ chung: " + formatCurrentMoney(fee));
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí gửi xe : " + formatCurrentMoney(feeVehicle.longValue()));
        content.append("<br/>");
        if (feeApartment != 0F) {
            content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí thẻ căn hộ đăng kí thêm : " + formatCurrentMoney(feeApartment.longValue()));
            content.append("<br/>");
        }
        if (!servicesResponse.isEmpty()) {
            for (ServiceNameFeeApartmentResponse s : servicesResponse) {
                content.append("&nbsp;&nbsp;&nbsp;&nbsp; Phí dịch vụ " + s.getServiceName().toLowerCase() + " : " + formatCurrentMoney(s.getFeeService().longValue()));
                content.append("<br/>");
            }
        }
        content.append("<p> Tổng phí: <b style=`color:red;`>" +
                formatCurrentMoney(totalFee) + "</b></p>");
        content.append("<p> Thời hạn nộp tiền: <b style=`color:red;`>10/" + monthNext + "/" + year + "</b></p>");
        content.append("<p> <b>Hình thức nộp tiền </b></p>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; + Chuyển khoản qua STK:");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - Số TK: <b>0361000302156</b>");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - Tên TK: <b>NGUYỄN VĂN MẠNH</b>");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - Ngân hàng: Vietcombank chi nhánh Vĩnh Phúc");
        content.append("<br/>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; + Chuyển khoản qua ví điện tử: Momo, Viettel Pay <b><0964600609></b>");
        content.append("<p> <b>Nội dung thanh toán </b></p>");
        content.append("&nbsp;&nbsp;&nbsp;&nbsp; +[Mã căn hô],[Số điện thoại],Nộp phí tháng ...");
        content.append("<br/>");
        content.append("<p> Nếu có thắc mắc về các khoản phí trên, quý cư dân gửi phản hồi qua ứng dụng. Chúng tôi sẽ tiếp nhận và xử lý</p>");
        List<String> result = new ArrayList<>();
        result.add(title.toString());
        result.add(content.toString());
        return result;
    }

}
