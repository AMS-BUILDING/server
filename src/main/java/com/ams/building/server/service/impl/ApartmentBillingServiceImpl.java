package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.DetailApartmentBilling;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentBuildingDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.DetailApartmentBuildingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.NotificationFeeApartmentResponse;
import com.ams.building.server.response.ServiceNameFeeApartmentResponse;
import com.ams.building.server.service.ApartmentBillingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.HelperUtils.formatDoubleNUmber;

@Service
public class ApartmentBillingServiceImpl implements ApartmentBillingService {

    private static final Logger logger = Logger.getLogger(ApartmentBillingServiceImpl.class);

    @Autowired
    private DetailApartmentBuildingDAO detailApartmentBuildingDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private ApartmentBuildingDAO apartmentBuildingDAO;

    @Override
    public NotificationFeeApartmentResponse getListFeeBillingByMonthAndAccount(Long accountId, String billingMonth) {

        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(billingMonth)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(accountId);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }

        ApartmentBilling billing = apartmentBuildingDAO.getDetailByMonth(billingMonth);

        if (Objects.isNull(billing)) {
            throw new RestApiException(StatusCode.BILLING_MONTH_NOT_RIGHT);
        }

        Double feeVehicle = detailApartmentBuildingDAO.feeVehicleCardByAccountIdAndMonth(accountId, billingMonth);
        Double feeApartment = detailApartmentBuildingDAO.feeResidentCardByAccountIdAndMonth(accountId, billingMonth);
        List<ServiceNameFeeApartmentResponse> services = detailApartmentBuildingDAO.feeServiceNameByAccountIdAndMonth(accountId, billingMonth);
        List<DetailApartmentBilling> billings = detailApartmentBuildingDAO.detailApartmentBillingByAccountIdAndMonth(accountId, billingMonth);

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
        String monthNext = String.valueOf(Integer.valueOf(month) + 1);
        if (monthNext.length() == 1) {
            monthNext = "0" + monthNext;
        }
        if (month.equals("12")) {
            monthNext = "01";
        }
        response.setStatus(statusId == 1 ? "Hạn chót thanh toán 10/" + monthNext : "Đã thanh toán");
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

}
