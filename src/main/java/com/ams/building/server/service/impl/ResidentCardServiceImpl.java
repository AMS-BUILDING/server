package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ResidentCard;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusResidentCard;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.ResidentCardDAO;
import com.ams.building.server.dao.StatusResidentCardDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.ResidentCardResponse;
import com.ams.building.server.service.ResidentCardService;
import org.apache.log4j.Logger;
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
import java.util.Random;

import static com.ams.building.server.utils.ValidateUtil.isEmail;

@Service
public class ResidentCardServiceImpl implements ResidentCardService {

    private static final Logger logger = Logger.getLogger(ResidentCardServiceImpl.class);

    @Autowired
    private ResidentCardDAO residentCardDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private StatusResidentCardDAO statusResidentCardDAO;

    @Override
    public ApiResponse getResidentCardByAccountId(Integer page, Integer size, Long accountId) {
        List<ResidentCardResponse> cardResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }
        String billingMonth = monthStr + "/" + year;
        Page<ResidentCard> residentCards = residentCardDAO.searchResidentCardByAccountId(accountId, billingMonth, pageable);
        residentCards.forEach(s -> cardResponses.add(convertToCardResponse(s)));
        Long totalPage = residentCards.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(cardResponses).totalElement(totalPage).build();
        return response;
    }

    @Override
    public void updateStatusResidentCard(Long cardId, Long statusId) {
        if (StringUtils.isEmpty(cardId) || StringUtils.isEmpty(statusId))
            throw new RestApiException(StatusCode.DATA_EMPTY);
        ResidentCard residentCard = residentCardDAO.getDetailResidentCardById(cardId);
        if (Objects.isNull(residentCard)) throw new RestApiException(StatusCode.RESIDENT_CARD_NOT_EXIST);
        residentCardDAO.updateStatus(statusId, cardId);
    }

    @Override
    public void removeResidentCard(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        ResidentCard residentCard = residentCardDAO.getDetailResidentCardById(id);
        if (Objects.isNull(residentCard)) throw new RestApiException(StatusCode.RESIDENT_CARD_NOT_EXIST);
        residentCardDAO.delete(residentCard);
    }

    @Override
    public void addResidentCard(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (!isEmail(email)) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        Account account = accountDAO.getAccountByEmail(email);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(account.getId(), String.valueOf(RoleEnum.ROLE_LANDLORD));
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }
        String billingMonth = monthStr + "/" + year;
        String roomName = roomNumber.getRoomName();
        ResidentCard newCard = new ResidentCard();
        newCard.setAccount(account);
        newCard.setPrice(Double.valueOf(Constants.ResidentCard.PRICE));
        newCard.setCardCode(genCardCode(roomName));
        StatusResidentCard statusResidentCard = statusResidentCardDAO.getOne(2L);
        newCard.setStatusResidentCard(statusResidentCard);
        newCard.setBillingMonth(billingMonth);
        residentCardDAO.save(newCard);
    }

    @Override
    public void addResidentCard(Long amount, Long accountId) {
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(amount)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(accountId, String.valueOf(RoleEnum.ROLE_LANDLORD));
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
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
        for (Long i = 0L; i < amount; i++) {
            ResidentCard residentCard = new ResidentCard();
            StatusResidentCard status = new StatusResidentCard();
            status.setId(1L);
            residentCard.setStatusResidentCard(status);
            String cardCode = genCardCode(roomNumber.getRoomName());
            residentCard.setCardCode(cardCode);
            account.setId(accountId);
            residentCard.setAccount(account);
            residentCard.setPrice(Double.valueOf(50000));
            residentCard.setBillingMonth(billingMonth);
            residentCardDAO.save(residentCard);
        }
    }

    private String genCardCode(String apartmentName) {
        String cardCode = apartmentName;
        Random rand = new Random();
        // mang chu hoa
        List<Character> listHoa = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            listHoa.add((char) (65 + rand.nextInt(3)));
        }
        cardCode += String.valueOf(listHoa.get(0));
        cardCode += String.valueOf(listHoa.get(1));
        // mang chu thuong
        List<Character> listsChar = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listsChar.add((char) (97 + rand.nextInt(3)));
        }
        cardCode += String.valueOf(listsChar.get(0));
        cardCode += String.valueOf(listsChar.get(1));
        cardCode += String.valueOf(listsChar.get(2));

        // mang so
        List<Integer> lists = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            lists.add(rand.nextInt(10));
        }
        // mess
        cardCode += String.valueOf(lists.get(0));
        cardCode += String.valueOf(lists.get(1));
        return cardCode;
    }

    private ResidentCardResponse convertToCardResponse(ResidentCard card) {
        ResidentCardResponse response = ResidentCardResponse.builder().build();
        response.setResidentId(card.getId());
        response.setResidentCode(card.getCardCode());
        response.setStatus(card.getStatusResidentCard().getStatusName());
        return response;
    }
}
