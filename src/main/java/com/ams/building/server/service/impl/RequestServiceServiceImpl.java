package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.DetailSubService;
import com.ams.building.server.bean.ReasonDetailSubService;
import com.ams.building.server.bean.RequestService;
import com.ams.building.server.bean.ResidentCard;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusServiceRequest;
import com.ams.building.server.bean.VehicleCard;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DetailSubServiceDAO;
import com.ams.building.server.dao.ReasonDetailSubServiceDAO;
import com.ams.building.server.dao.RequestServiceDAO;
import com.ams.building.server.dao.ResidentCardDAO;
import com.ams.building.server.dao.StatusRequestServiceDAO;
import com.ams.building.server.dao.VehicleCardDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.RequestServiceRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.DetailServiceRequestResponse;
import com.ams.building.server.response.DetailSubServiceClientResponse;
import com.ams.building.server.response.HourResponse;
import com.ams.building.server.response.ReasonDetailSubServiceResponse;
import com.ams.building.server.response.RequestServiceClientResponse;
import com.ams.building.server.response.RequestServiceResponse;
import com.ams.building.server.response.ServiceAddResponse;
import com.ams.building.server.response.StatusServiceResponse;
import com.ams.building.server.service.RequestServiceService;
import com.ams.building.server.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
import static com.ams.building.server.utils.DateTimeUtils.HH_MM;
import static com.ams.building.server.utils.DateTimeUtils.convertDateToStringWithTimezone;
import static com.ams.building.server.utils.DateTimeUtils.convertStringToDate;

@Transactional
@Service
public class RequestServiceServiceImpl implements RequestServiceService {

    @Autowired
    private StatusRequestServiceDAO statusRequestServiceDAO;

    @Autowired
    private RequestServiceDAO requestServiceDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private ReasonDetailSubServiceDAO reasonDetailSubServiceDAO;

    @Autowired
    private DetailSubServiceDAO detailSubServiceDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private VehicleCardDAO vehicleCardDAO;

    @Autowired
    private ResidentCardDAO residentCardDAO;

    @Override
    public List<StatusServiceResponse> statusResponses() {
        List<StatusServiceResponse> responses = new ArrayList<>();
        List<StatusServiceRequest> requests = statusRequestServiceDAO.requestStatusList();
        requests.forEach(s -> responses.add(covertToStatusRequestResponse(s)));
        return responses;
    }

    @Override
    public ApiResponse searchServiceRequest(Integer size, Integer page, String name, String serviceName, Long statusId) {
        List<RequestServiceResponse> requestServiceResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<RequestService> requestServices;
        if (statusId == -1) {
            requestServices = requestServiceDAO.requestServicesNotStatus(name, serviceName, pageable);
        } else {
            requestServices = requestServiceDAO.requestServicesWithStatus(name, serviceName, statusId, pageable);
        }
        requestServices.forEach(s -> requestServiceResponses.add(covertToRequestResponse(s)));
        Long totalElement = requestServices.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(requestServiceResponses).totalElement(totalElement).build();
        return response;
    }

    @Override
    public RequestServiceResponse getRequestServiceById(Long id) {
        if (StringUtils.isEmpty(id)) throw new RestApiException(StatusCode.DATA_EMPTY);
        RequestService service = requestServiceDAO.findRequestServiceById(id);
        if (Objects.isNull(service)) {
            throw new RestApiException(StatusCode.REQUEST_SERVICE_NOT_EXIST);
        }
        RequestServiceResponse response = covertToRequestResponse(service);
        return response;
    }

    @Override
    public void updateStatusRequest(Long statusId, Long requestId) {
        if (StringUtils.isEmpty(requestId) || StringUtils.isEmpty(statusId))
            throw new RestApiException(StatusCode.DATA_EMPTY);
        RequestService service = requestServiceDAO.findRequestServiceById(requestId);
        if (Objects.isNull(service)) throw new RestApiException(StatusCode.REQUEST_SERVICE_NOT_EXIST);
        requestServiceDAO.updateStatus(statusId, requestId);
    }

    @Override
    public List<RequestServiceClientResponse> historyServiceResponse(Long id, Long statusId) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(statusId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String billingMonth;
        if (month >= 0 && month <= 8) {
            billingMonth = "0" + (month + 1) + "/" + year;
        } else {
            billingMonth = (month + 1) + "/" + year;
        }
        List<Long> status = new ArrayList<>();
        status.add(statusId);
        List<Integer> isUses = new ArrayList<>();
        isUses.add(1);
        isUses.add(0);
        List<ResidentCard> residentCards = residentCardDAO.residentCardRegister(id, status, billingMonth, isUses);
        List<RequestService> requestServiceByAccountId = requestServiceDAO.requestServiceByAccountId(id, statusId);
        List<RequestServiceClientResponse> responses = new ArrayList<>();
        requestServiceByAccountId.forEach(s -> responses.add(convertToHistoryResponse(s)));
        for (ResidentCard residentCard : residentCards) {
            RequestServiceClientResponse requestServiceClientResponse = convertResidentCardToResponse(residentCard);
            responses.add(requestServiceClientResponse);
        }
        responses.sort(Comparator.comparing(RequestServiceClientResponse::getMinutes));
        return responses;
    }

    @Override
    public DetailServiceRequestResponse detailServiceRequest(Long serviceRequestId, Long typeRequest) {
        if (StringUtils.isEmpty(serviceRequestId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        DetailServiceRequestResponse response = DetailServiceRequestResponse.builder().build();
        if (typeRequest == 1) {
            RequestService request = requestServiceDAO.findRequestServiceById(serviceRequestId);
            if (Objects.isNull(request)) {
                throw new RestApiException(StatusCode.REQUEST_SERVICE_NOT_EXIST);
            }
            String message = request.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName();
            if (request.getReasonDetailSubService().getDetailSubService().getService().getId() == 9) {
                message += request.getReasonDetailSubService().getDetailSubService().getDetailSubServiceName().toLowerCase() + " vì  " + request.getReasonDetailSubService().getReasonName().toLowerCase();
            }
            response.setStatusId(request.getStatusServiceRequest().getId());
            response.setServiceName(message);
        } else if (typeRequest == 2) {
            VehicleCard card = vehicleCardDAO.getById(serviceRequestId);
            if (Objects.isNull(card)) {
                throw new RestApiException(StatusCode.VEHICLE_NOT_EXIST);
            }
            String message = "Đăng kí thẻ xe";
            response.setStatusId(card.getStatusVehicleCard().getId());
            response.setServiceName(message);
        } else {
            ResidentCard card = residentCardDAO.getById(serviceRequestId);
            if (Objects.isNull(card)) {
                throw new RestApiException(StatusCode.RESIDENT_CARD_NOT_EXIST);
            }
            String message = "Đăng kí thẻ căn hộ " + card.getCardCode();
            response.setServiceName(message);
            response.setStatusId(card.getStatusResidentCard().getId());
        }

        return response;
    }

    @Override
    public void updateStatusRequestByTypeRequest(Long statusId, Long requestId, Long typeRequest) {
        if (StringUtils.isEmpty(requestId) || StringUtils.isEmpty(statusId) || StringUtils.isEmpty(typeRequest))
            throw new RestApiException(StatusCode.DATA_EMPTY);
        if (typeRequest == 1) {
            RequestService service = requestServiceDAO.findRequestServiceById(requestId);
            if (Objects.isNull(service)) throw new RestApiException(StatusCode.REQUEST_SERVICE_NOT_EXIST);
            requestServiceDAO.updateStatus(statusId, requestId);
        } else if (typeRequest == 2) {
            VehicleCard vehicleCard = vehicleCardDAO.getById(requestId);
            if (Objects.isNull(vehicleCard)) throw new RestApiException(StatusCode.VEHICLE_CARD_NOT_EXIST);
            vehicleCardDAO.updateStatus(statusId, requestId);
        } else {
            ResidentCard card = residentCardDAO.getById(requestId);
            if (Objects.isNull(card)) {
                throw new RestApiException(StatusCode.RESIDENT_CARD_NOT_EXIST);
            }
            residentCardDAO.updateStatus(statusId, requestId);
        }
    }

    @Override
    public List<HourResponse> listHours() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        List<HourResponse> hours = new ArrayList<>();
        int start = hour;
        if (minute != 0) {
            start = hour + 1;
        }
        for (int i = start; i <= 24; i++) {
            HourResponse response = HourResponse.builder().build();
            response.setLabel(i + "h");
            if (i >= 1 && i <= 9) {
                response.setValue("0" + i + ":00");
            } else {
                response.setValue(i + ":00");
            }
            hours.add(response);
        }
        return hours;
    }

    @Override
    public ServiceAddResponse addRequestServiceSuccessStatus(RequestServiceRequest requestServiceRequest) {
        Long id;
        if (Objects.isNull(requestServiceRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        RequestService requestService = new RequestService();
        Long requestId = requestServiceRequest.getReasonDetailSubServiceId();
        ReasonDetailSubService reason = new ReasonDetailSubService();
        reason.setId(requestServiceRequest.getReasonDetailSubServiceId());

        StatusServiceRequest status = new StatusServiceRequest();
        status.setId(3L);

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String billingMonth;
        if (month >= 0 && month <= 8) {
            billingMonth = "0" + (month + 1) + "/" + year;
        } else {
            billingMonth = (month + 1) + "/" + year;
        }

        Account account = new Account();
        account.setId(requestServiceRequest.getAccountId());
        if (requestId <= 4L) {
            RequestService currentRequestService = requestServiceDAO.findRequestServiceByStartDateAndReasonDetailSubServiceId(convertStringToDate(requestServiceRequest.getStartDate(), DateTimeUtils.YYYY_MM_DD_HH_MM), requestServiceRequest.getReasonDetailSubServiceId());
            if (Objects.nonNull(currentRequestService)) {
                throw new RestApiException(StatusCode.REQUEST_SERVICE_REGISTER_BEFORE);
            }
            requestService.setReasonDetailSubService(reason);
            requestService.setStatusServiceRequest(status);
            requestService.setAccount(account);
            requestService.setStartDate(convertStringToDate(requestServiceRequest.getStartDate(), DateTimeUtils.YYYY_MM_DD_HH_MM));
            requestService.setEndDate(convertStringToDate(requestServiceRequest.getEndDate(), DateTimeUtils.YYYY_MM_DD_HH_MM));
            requestService.setBillingMonth(billingMonth);
            requestServiceDAO.save(requestService);
        } else if (requestId == 5L) {
            requestService.setReasonDetailSubService(reason);
            requestService.setStatusServiceRequest(status);
            requestService.setAccount(account);
            requestService.setStartDate(convertStringToDate(requestServiceRequest.getStartDate(), DateTimeUtils.YYYY_MM_DD_HH_MM));
            requestService.setEndDate(convertStringToDate(requestServiceRequest.getEndDate(), DateTimeUtils.YYYY_MM_DD_HH_MM));
            requestService.setBillingMonth(billingMonth);
            requestServiceDAO.save(requestService);
        } else {
            requestService.setReasonDetailSubService(reason);
            status.setId(1L);
            requestService.setStatusServiceRequest(status);
            requestService.setAccount(account);
            requestService.setStartDate(convertStringToDate(requestServiceRequest.getStartDate(), DateTimeUtils.YYYY_MM_DD_HH_MM));
            requestService.setEndDate(convertStringToDate(requestServiceRequest.getEndDate(), DateTimeUtils.YYYY_MM_DD_HH_MM));
            requestService.setBillingMonth(billingMonth);
            requestServiceDAO.save(requestService);
        }
        id = requestService.getId();
        ServiceAddResponse response = ServiceAddResponse.builder()
                .serviceId(id)
                .typeService(1L)
                .build();
        return response;
    }

    @Override
    public Double getPriceByReasonDetailSubServiceId(Long id) {
        if (Objects.isNull(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        ReasonDetailSubService reasonDetailSubService = reasonDetailSubServiceDAO.getReasonDetailSubServiceById(id);
        if (Objects.isNull(reasonDetailSubService)) {
            throw new RestApiException(StatusCode.REASON_DETAIL_SUB_SERVICE_NOT_EXIST);
        }
        Double price = reasonDetailSubService.getPrice();
        return price;
    }

    @Override
    public List<ReasonDetailSubServiceResponse> getListReasonByDetailSubServiceId(Long id) {
        if (Objects.isNull(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        List<ReasonDetailSubService> reasonDetailSubServices = reasonDetailSubServiceDAO.getListReasonByDetailSubServiceId(id);
        List<ReasonDetailSubServiceResponse> responses = new ArrayList<>();
        reasonDetailSubServices.forEach(r -> responses.add(convertReasonDetailSubService(r)));
        return responses;
    }

    @Override
    public List<RequestServiceClientResponse> findRequestServiceByAccountId(Long accountId) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String billingMonth;
        if (month >= 0 && month <= 8) {
            billingMonth = "0" + (month + 1) + "/" + year;
        } else {
            billingMonth = (month + 1) + "/" + year;
        }
        List<RequestServiceClientResponse> response = new ArrayList<>();
        List<RequestService> requestServices = requestServiceDAO.findRequestServiceByAccountId(accountId, billingMonth);
        List<VehicleCard> requestVehicleCard = vehicleCardDAO.vehicleCardRegister(accountId, billingMonth);
        List<Long> status = new ArrayList<>();
        status.add(1L);
        status.add(2L);
        List<Integer> isUses = new ArrayList<>();
        isUses.add(1);
        List<ResidentCard> residentCards = residentCardDAO.residentCardRegister(accountId, status, billingMonth, isUses);
        for (RequestService requestService : requestServices) {
            RequestServiceClientResponse requestServiceClientResponse = convertRequestServiceToRequestServiceClientResponse(requestService);
            response.add(requestServiceClientResponse);
        }
        for (VehicleCard vehicleCard : requestVehicleCard) {
            RequestServiceClientResponse requestServiceClientResponse = convertVehicleCardToResponse(vehicleCard);
            response.add(requestServiceClientResponse);
        }
        for (ResidentCard residentCard : residentCards) {
            RequestServiceClientResponse requestServiceClientResponse = convertHistoryResidentCard(residentCard);
            response.add(requestServiceClientResponse);
        }
        response.sort(Comparator.comparing(RequestServiceClientResponse::getMinutes));
        return response;
    }

    @Override
    public List<DetailSubServiceClientResponse> getDetailSubServiceBySubServiceId(Long subServiceId) {
        if (Objects.isNull(subServiceId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        List<DetailSubService> detailSubServices = detailSubServiceDAO.getDetailSubServiceBySubServiceId(subServiceId);
        List<DetailSubServiceClientResponse> responses = new ArrayList<>();
        detailSubServices.forEach(detailSubService -> responses.add(convertDetailSubServiceToDetailSubServiceClientResponse(detailSubService)));
        return responses;
    }

    private DetailSubServiceClientResponse convertDetailSubServiceToDetailSubServiceClientResponse(DetailSubService detailSubService) {
        DetailSubServiceClientResponse response = DetailSubServiceClientResponse.builder()
                .id(detailSubService.getId())
                .detailSubServiceName(detailSubService.getDetailSubServiceName())
                .build();
        return response;
    }

    private RequestServiceClientResponse convertRequestServiceToRequestServiceClientResponse(RequestService requestService) {
        String serviceName = requestService.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName();
        Date currentTime = new Date();
        Account account = requestService.getAccount();
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(account.getId());
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        Long reasonRequestId = requestService.getReasonDetailSubService().getId();
        String serviceNameStr = "Yêu cầu sử dụng dịch vụ " + serviceName.toLowerCase();
        String description;
        if (reasonRequestId >= 21 && reasonRequestId <= 29) {
            description = serviceNameStr + requestService.getReasonDetailSubService().getDetailSubService().getDetailSubServiceName().toLowerCase() + " " + requestService.getReasonDetailSubService().getReasonName().toLowerCase() + " tại căn hộ " + roomNumber.getRoomName() + " lúc " + convertDateToStringWithTimezone(requestService.getStartDate(), HH_MM, null);
        } else {
            description = serviceNameStr + " tại căn hộ " + roomNumber.getRoomName() + " lúc " + convertDateToStringWithTimezone(requestService.getStartDate(), HH_MM, null);
        }
        Long diff = currentTime.getTime() - requestService.getDateRequest().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " phút trước";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " giờ trước";
        } else {
            time = convertDateToStringWithTimezone(requestService.getDateRequest(), DD_MM_YYYY, null);
        }
        RequestServiceClientResponse response = RequestServiceClientResponse.builder()
                .id(requestService.getId())
                .serviceName(serviceNameStr + " - " + convertDateToStringWithTimezone(requestService.getStartDate(), DD_MM_YYYY, null))
                .description(description)
                .time(time)
                .statusId(requestService.getStatusServiceRequest().getId())
                .typeRequest(1L)
                .minutes(minutes)
                .build();
        return response;
    }

    private RequestServiceClientResponse convertVehicleCardToResponse(VehicleCard vehicleCard) {
        Account account = vehicleCard.getAccount();
        Date currentTime = new Date();
        Apartment apartment = apartmentDAO.getApartmentByAccountId(account.getId());
        RoomNumber roomNumber = apartment.getRoomNumber();
        Long diff = currentTime.getTime() - vehicleCard.getStartDate().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " phút trước";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " giờ trước";
        } else {
            time = convertDateToStringWithTimezone(vehicleCard.getStartDate(), DD_MM_YYYY, null);
        }
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        response.setId(vehicleCard.getId());
        response.setServiceName("Yêu cầu đăng kí thẻ gửi xe  - " + convertDateToStringWithTimezone(vehicleCard.getStartDate(), DD_MM_YYYY, null));
        response.setDescription("Yêu cầu đăng kí thẻ gửi xe của căn hộ " + roomNumber.getRoomName() + " lúc " + convertDateToStringWithTimezone(vehicleCard.getStartDate(), HH_MM, null));
        response.setTime(time);
        response.setStatusId(vehicleCard.getStatusVehicleCard().getId());
        response.setTypeRequest(2L);
        response.setMinutes(minutes);
        return response;

    }

    private RequestServiceClientResponse convertResidentCardToResponse(ResidentCard residentCard) {
        Account account = residentCard.getAccount();
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        Date currentTime = new Date();
        Apartment apartment = apartmentDAO.getApartmentByAccountId(account.getId());
        RoomNumber roomNumber = apartment.getRoomNumber();
        Long diff = currentTime.getTime() - residentCard.getStartDate().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " phút trước";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " giờ trước";
        } else {
            time = convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null);
        }
        response.setId(residentCard.getId());
        response.setServiceName("Yêu cầu được cấp thẻ căn hộ  - " + convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null));
        response.setDescription("Yêu cầu được cấp  thẻ căn hộ của căn hộ " + roomNumber.getRoomName() + " lúc " + convertDateToStringWithTimezone(residentCard.getStartDate(), HH_MM, null));
        response.setTime(time);
        response.setStatusId(residentCard.getStatusResidentCard().getId());
        response.setTypeRequest(3L);
        response.setMinutes(minutes);
        return response;
    }

    private ReasonDetailSubServiceResponse convertReasonDetailSubService(ReasonDetailSubService reasonDetailSubService) {
        ReasonDetailSubServiceResponse resp = ReasonDetailSubServiceResponse.builder()
                .reasonName(reasonDetailSubService.getReasonName())
                .id(reasonDetailSubService.getId())
                .price(reasonDetailSubService.getPrice())
                .build();
        return resp;
    }

    private StatusServiceResponse covertToStatusRequestResponse(StatusServiceRequest request) {
        StatusServiceResponse response = StatusServiceResponse.builder().build();
        response.setId(request.getId());
        response.setName(request.getRequestName());
        return response;
    }

    private RequestServiceResponse covertToRequestResponse(RequestService requestService) {
        RequestServiceResponse response = RequestServiceResponse.builder().build();
        if (Objects.isNull(requestService.getAccount())) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(requestService.getAccount().getId(), String.valueOf(RoleEnum.ROLE_LANDLORD));
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        response.setId(requestService.getId());
        response.setBlock(apartment.getRoomNumber().getFloorBlock().getBlock().getBlockName());
        if (requestService.getReasonDetailSubService().getId() < 7) {
            response.setServiceName(requestService.getReasonDetailSubService().getReasonName());
        } else {
            response.setServiceName(requestService.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName() + " " + requestService.getReasonDetailSubService().getDetailSubService().getDetailSubServiceName().toLowerCase());
        }
        response.setStatus(requestService.getStatusServiceRequest().getRequestName());
        response.setRoomName(apartment.getRoomNumber().getRoomName());
        response.setName(requestService.getAccount().getName());
        response.setDayWant(convertDateToStringWithTimezone(requestService.getStartDate(), DD_MM_YYYY, null));
        return response;
    }

    private RequestServiceClientResponse convertToHistoryResponse(RequestService requestService) {
        Date currentTime = new Date();
        Long diff = currentTime.getTime() - requestService.getDateRequest().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(Math.abs(diff));
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " phút trước";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " giờ trước";
        } else {
            time = convertDateToStringWithTimezone(requestService.getDateRequest(), DD_MM_YYYY, null);
        }
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        String serviceName = requestService.getReasonDetailSubService().getDetailSubService().getService().getSubServiceName();
        String firstDescription = "Đã đăng kí thành công dịch vụ ";
        String serviceNameFirst = "Sử dụng dịch vụ ";
        String date = convertDateToStringWithTimezone(requestService.getStartDate(), DD_MM_YYYY, null);
        String timeStr = convertDateToStringWithTimezone(requestService.getStartDate(), HH_MM, null);
        response.setId(requestService.getId());
        response.setServiceName(serviceNameFirst + serviceName.toLowerCase() + " - " + date);
        response.setDescription(firstDescription + serviceName.toLowerCase() + " vào lúc " + timeStr + " ngày " + date);
        response.setTime(time);
        response.setStatusId(requestService.getStatusServiceRequest().getId());
        response.setTypeRequest(1L);
        response.setMinutes(minutes);
        return response;
    }

    private RequestServiceClientResponse convertHistoryResidentCard(ResidentCard residentCard) {
        Account account = residentCard.getAccount();
        RequestServiceClientResponse response = RequestServiceClientResponse.builder().build();
        Date currentTime = new Date();
        Apartment apartment = apartmentDAO.getApartmentByAccountId(account.getId());
        RoomNumber roomNumber = apartment.getRoomNumber();
        Long diff = currentTime.getTime() - residentCard.getStartDate().getTime();
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(diff);
        String time = "";
        if (minutes >= 0 && minutes < 60) {
            time = minutes + " phút trước";
        } else if (minutes >= 60 && minutes < 1440) {
            time = Long.valueOf(minutes / 60) + " giờ trước";
        } else {
            time = convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null);
        }
        response.setId(residentCard.getId());
        response.setServiceName("Đã đăng kí thành công thêm thẻ căn hộ  - " + convertDateToStringWithTimezone(residentCard.getStartDate(), DD_MM_YYYY, null));
        response.setDescription("Đã đăng kí thành công thẻ gửi xe của căn hộ " + roomNumber.getRoomName() + " lúc " + convertDateToStringWithTimezone(residentCard.getStartDate(), HH_MM, null));
        response.setTime(time);
        response.setStatusId(residentCard.getStatusResidentCard().getId());
        response.setTypeRequest(3L);
        response.setMinutes(minutes);
        return response;
    }

}
