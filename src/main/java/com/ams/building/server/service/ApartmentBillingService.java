package com.ams.building.server.service;


import com.ams.building.server.response.NotificationFeeApartmentResponse;

public interface ApartmentBillingService {

    NotificationFeeApartmentResponse getListFeeBillingByMonthAndAccount(Long accountId, String billingMonth);

    void checkAndInsertBillingInMonth();

    void sendEmailToNotificationForResidentAboutFeeServiceInMonth();

}
