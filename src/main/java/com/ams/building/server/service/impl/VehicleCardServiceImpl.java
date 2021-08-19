package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.StatusVehicleCard;
import com.ams.building.server.bean.Vehicle;
import com.ams.building.server.bean.VehicleCard;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.VehicleCardDAO;
import com.ams.building.server.dao.VehicleDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.VehicleCardClientRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.VehicleCardResponse;
import com.ams.building.server.response.VehicleTypeResponse;
import com.ams.building.server.service.VehicleCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.DateTimeUtils.DD_MM_YYYY;
import static com.ams.building.server.utils.DateTimeUtils.convertDateToString;

@Service
public class VehicleCardServiceImpl implements VehicleCardService {

    @Autowired
    private VehicleCardDAO vehicleCardDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Override
    public ApiResponse searchVehicleCard(Integer page, Integer size, String vehicleOwner, String phoneNumber, String licenesPlates, Long statusId) {
        List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }
        String billingMonth = monthStr + "/" + year;
        Page<VehicleCard> vehicleCards;
        if (statusId == -1) {
            vehicleCards = vehicleCardDAO.vehicleCardListWithoutStatus(vehicleOwner, phoneNumber, licenesPlates, billingMonth, pageable);
        } else {
            vehicleCards = vehicleCardDAO.vehicleCardListWithStatus(vehicleOwner, phoneNumber, licenesPlates, billingMonth, statusId, pageable);
        }
        for (VehicleCard ad : vehicleCards) {
            VehicleCardResponse response = convertToCardResponse(ad);
            vehicleCardResponses.add(response);
        }
        Long totalElement = vehicleCards.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(vehicleCardResponses).totalElement(totalElement).build();
        return response;
    }

    @Override
    public VehicleCardResponse detailVehicleCard(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        VehicleCard card = vehicleCardDAO.getVehicleCardById(id);
        if (Objects.isNull(card)) {
            throw new RestApiException(StatusCode.VEHICLE_CARD_NOT_EXIST);
        }
        VehicleCardResponse response = convertToCardResponse(card);
        return response;
    }

    @Override
    public void updateStatusVehicleCard(Long id, Long statusId) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        VehicleCard card = vehicleCardDAO.getVehicleCardById(id);
        if (Objects.isNull(card)) {
            throw new RestApiException(StatusCode.VEHICLE_CARD_NOT_EXIST);
        }
        vehicleCardDAO.updateStatus(statusId, id);
    }

    @Override
    public void removeVehicleCard(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        VehicleCard card = vehicleCardDAO.getVehicleCardById(id);
        if (Objects.isNull(card)) {
            throw new RestApiException(StatusCode.VEHICLE_CARD_NOT_EXIST);
        }
        vehicleCardDAO.delete(card);
    }

    @Override
    public List<VehicleTypeResponse> listVehicleByTypeAndByAccountId(Long id, Long vehicleTypeId) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(vehicleTypeId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Vehicle vehicle = vehicleDAO.getById(vehicleTypeId);
        if (Objects.isNull(vehicle)) {
            throw new RestApiException(StatusCode.VEHICLE_NOT_EXIST);
        }
        List<VehicleCard> vehicleCards = vehicleCardDAO.vehicleListByAccountIdAndTypeId(id, vehicleTypeId);
        List<VehicleTypeResponse> responses = new ArrayList<>();
        vehicleCards.forEach(c -> responses.add(convertToVehicleTypeResponse(c)));
        return responses;
    }

    @Override
    public void addVehicleCard(List<VehicleCardClientRequest> requests, Long accountId) {
        if (requests.isEmpty()) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }
        String billingMonth = monthStr + "/" + year;
        Account account = new Account();
        Vehicle vehicle = new Vehicle();
        StatusVehicleCard status = new StatusVehicleCard();
        status.setId(1L);
        for (VehicleCardClientRequest request : requests) {
            VehicleCard vehicleCard = new VehicleCard();
            vehicle.setId(request.getVehicleId());
            vehicleCard.setVehicle(vehicle);
            account.setId(accountId);
            vehicleCard.setAccount(account);
            vehicleCard.setStatusVehicleCard(status);
            vehicleCard.setVehicleBranch(request.getVehicleBranch());
            vehicleCard.setLicensePlate(request.getLicensePlate());
            vehicleCard.setVehicleColor(request.getVehicleColor());
            vehicleCard.setBillingMonth(billingMonth);
            vehicleCardDAO.save(vehicleCard);
        }
    }

    @Override
    public ApiResponse searchVehicleCardByRoomNumber(Integer page, Integer size, Long accountId, Long vehicleId) {
        List<VehicleCardResponse> vehicleCardResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleCard> vehicleCards = vehicleCardDAO.searchVehicleCardByRoomNumber(accountId, vehicleId, pageable);
        for (VehicleCard ad : vehicleCards) {
            VehicleCardResponse response = convertToCardResponse(ad);
            vehicleCardResponses.add(response);
        }
        Long totalElement = vehicleCards.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(vehicleCardResponses).totalElement(totalElement).build();
        return response;
    }

    private VehicleCardResponse convertToCardResponse(VehicleCard card) {
        VehicleCardResponse response = VehicleCardResponse.builder().build();
        response.setId(card.getId());
        response.setVehicleOwner(card.getAccount().getName());
        response.setPhoneNumber(card.getAccount().getPhone());
        response.setLicensePlates(card.getLicensePlate());
        response.setType(card.getVehicle().getVehicleName());
        response.setColor(card.getVehicleColor());
        response.setStatus(card.getStatusVehicleCard().getStatusName());
        return response;
    }

    private VehicleTypeResponse convertToVehicleTypeResponse(VehicleCard card) {
        VehicleTypeResponse response = VehicleTypeResponse.builder().build();
        response.setVehicleBranch(card.getVehicleBranch());
        response.setColor(card.getVehicleColor());
        response.setLicensePlates(card.getLicensePlate());
        response.setStartDate(convertDateToString(card.getStartDate(), DD_MM_YYYY));
        response.setSeat(card.getVehicle().getId() == 3 ? "5" : card.getVehicle().getId() == 4 ? "7" : "");
        return response;
    }

}
