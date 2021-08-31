package com.ams.building.server.schedule;

import com.ams.building.server.service.ApartmentBillingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ApartmentBillingSchedule {

    private static final Logger logger = Logger.getLogger(ApartmentBillingSchedule.class);

    @Autowired
    private ApartmentBillingService service;

    /**
     * Check And Auto Insert Billing In Month.
     * In the 1rd when 00:00 AM will auto insert to calculate billing for resident
     */
    //@Scheduled(cron = "1 * * * * ?")
//    @Scheduled(cron = "0 0 1 * * ?")
    public void checkAndInsertBillingInMonth() {
        try {
            service.checkAndInsertBillingInMonth();
        } catch (Exception e) {
            logger.error("checkAndInsertBillingInMonth Error : ", e);
        }
    }

    /**
     * Send Mail To Resident About Fee Service In Month.
     * Mail will send 1rd in month.
     */
//    @Scheduled(cron = "0 9 1 * * ?")
//    @Scheduled(cron = "1 * * * * ?")
    public void sendEmailToNotificationForResidentAboutFeeServiceInMonth() {
        try {
            service.sendEmailToNotificationForResidentAboutFeeServiceInMonth();
        } catch (Exception e) {
            logger.error("sendEmailToNotificationForResidentAboutFeeServiceInMonth Error : ", e);
        }
    }

    /**
     * Send Mail To Remind For  Resident About Fee Service In Month.
     * Mail will send 5rd in month.
     */
//    @Scheduled(cron = "0 9 5 * * ?")
//    @Scheduled(cron = "1 * * * * ?")
    public void sendMailRemindForResident() {
        try {
            service.sendMailRemindForResident();
        } catch (Exception e) {
            logger.error("sendEmailToNotificationForResidentAboutFeeServiceInMonth Error : ", e);
        }
    }

}
