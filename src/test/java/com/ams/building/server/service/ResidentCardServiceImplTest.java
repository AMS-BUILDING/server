package com.ams.building.server.service;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.ResidentCard;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusResidentCard;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.ResidentCardDAO;
import com.ams.building.server.dao.StatusResidentCardDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ResidentCardAddResponse;
import com.ams.building.server.response.ResidentCardResponse;
import com.ams.building.server.service.impl.ResidentCardServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(PowerMockRunner.class)
public class ResidentCardServiceImplTest {

    @InjectMocks
    ResidentCardServiceImpl residentCardServiceImpl;

    @Mock
    ResidentCardDAO residentCardDAO;

    @Mock
    ApartmentDAO apartmentDAO;

    @Mock
    AccountDAO accountDAO;

    @Mock
    StatusResidentCardDAO statusResidentCardDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    Pageable pageable = PageRequest.of(1, 5);

    Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
            , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
            , new Position(1L, "ch? h?", true), "1213", true);

    Building building = new Building(1L, "A01", "aa", "cccc");

    FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));

    RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");

    Apartment apartment = new Apartment(1L, account, building, roomNumber);

    StatusResidentCard statusResidentCard = new StatusResidentCard(1L, "a");

    ResidentCard residentCard = new ResidentCard(1L, account, statusResidentCard,
            "cardCode", 1000D, "price", new Date(), 1);

    List<ResidentCard> residentCardList = Arrays.asList(residentCard);

    Page<ResidentCard> residentCards = new PageImpl<ResidentCard>(residentCardList, pageable, residentCardList.size());

    @Test
    public void getResidentCardByAccountId() {
        List<ResidentCardResponse> cardResponses = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }

        Mockito.when(residentCardDAO.searchResidentCardByAccountId(Mockito.any(), Mockito.anyString(), Mockito.anyObject())).thenReturn(residentCards);

        residentCards.forEach(s -> cardResponses.add(convertToCardResponse(s)));
        Long totalPage = residentCards.getTotalElements();

        residentCardServiceImpl.getResidentCardByAccountId(1, 5, 1L);
    }

    @Test
    public void updateStatusResidentCard() {
        Mockito.when(residentCardDAO.getDetailResidentCardById(Mockito.any())).thenReturn(residentCard);
        Mockito.doNothing().when(residentCardDAO).updateStatus(Mockito.any(), Mockito.any());
        residentCardServiceImpl.updateStatusResidentCard(1L, 1L);
    }

    @Test
    public void updateStatusResidentCardTestFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        residentCardServiceImpl.updateStatusResidentCard(id, 1L);
    }

    @Test
    public void updateStatusResidentCardTestFailByIdStatusNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        Long idStatus = null;
        residentCardServiceImpl.updateStatusResidentCard(id, idStatus);
    }

    @Test
    public void updateStatusResidentCardTestFailByObjectNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Thẻ căn hộ không tồn tại");
        Long id = 1L;
        Long idStatus = 1L;
        Mockito.when(residentCardDAO.getDetailResidentCardById(Mockito.any())).thenReturn(null);
        residentCardServiceImpl.updateStatusResidentCard(id, idStatus);
    }

    @Test
    public void removeResidentCard() {
        Mockito.when(residentCardDAO.getDetailResidentCardById(Mockito.any())).thenReturn(residentCard);
        Mockito.doNothing().when(residentCardDAO).cancelExtend(Mockito.any(), Mockito.any());
        residentCardServiceImpl.removeResidentCard(1L);
    }

    @Test
    public void removeResidentCardTestFailByIdNull() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        residentCardServiceImpl.removeResidentCard(id);
    }

    @Test
    public void addResidentCardTestSuccess() {
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.any())).thenReturn(apartment);
        RoomNumber roomNumber = apartment.getRoomNumber();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }
        String billingMonth = monthStr + "/" + year;
        List<Long> ids = new ArrayList<>();
        for (Long i = 0L; i < 1L; i++) {
            statusResidentCard.setId(1L);
            residentCard.setStatusResidentCard(statusResidentCard);
            String cardCode = genCardCode(roomNumber.getRoomName());
            residentCard.setCardCode(cardCode);
            account.setId(1L);
            residentCard.setAccount(account);
            residentCard.setPrice(Double.valueOf(50000));
            residentCard.setBillingMonth(billingMonth);
            residentCard.setIsUse(1);
            residentCard.setId(1L);

            Mockito.when(residentCardDAO.save(Mockito.any())).thenReturn(residentCard);
            System.out.println(residentCard.getId());
            ids.add(residentCard.getId());
        }
        ResidentCardAddResponse response = ResidentCardAddResponse.builder().build();
        response.setServiceId(ids);
        response.setTypeService(3L);
        residentCardServiceImpl.addResidentCard(1L, 1L);
    }

    @Test
    public void addResidentCardTestFailByAmountEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = 1L;
        Long amount = null;
        residentCardServiceImpl.addResidentCard(amount, id);
    }

    @Test
    public void addResidentCardTestFailByIdEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        Long id = null;
        Long amount = null;
        residentCardServiceImpl.addResidentCard(amount, id);
    }

    @Test
    public void addResidentCardTestFailByApartmentNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        Long id = 1L;
        Long amount = 1L;
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.any())).thenReturn(null);
        residentCardServiceImpl.addResidentCard(amount, id);
    }

    @Test
    public void addResidentCardTestFailByRoomNumberNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phòng không tồn tại");
        Long id = 1L;
        Long amount = 1L;
        Apartment apartment = new Apartment(1L, account, building, null);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.any())).thenReturn(apartment);
        residentCardServiceImpl.addResidentCard(amount, id);
    }

    @Test
    public void addResidentCardInWebTestFailEmailEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không được để trống");
        String email = null;
        residentCardServiceImpl.addResidentCard(email);
    }

    @Test
    public void addResidentCardInWebTestFailEmailNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Email không đúng định dạng");
        String email = "abcd";
        residentCardServiceImpl.addResidentCard(email);
    }

    @Test
    public void addResidentCardInWebTestFailAccountNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Tài khoản  không tồn tại");
        String email = "abc@gmail.com";
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(null);
        residentCardServiceImpl.addResidentCard(email);
    }

    @Test
    public void addResidentCardInWebTestFailApartmentNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        String email = "abc@gmail.com";
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.anyString())).thenReturn(null);
        residentCardServiceImpl.addResidentCard(email);
    }

    @Test
    public void addResidentCardInWebTestFailRoomNumberNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Phòng không tồn tại");
        String email = "abc@gmail.com";
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.anyString())).thenReturn(new Apartment());
        residentCardServiceImpl.addResidentCard(email);
    }

    @Test
    public void addResidentCardInWebTestSuccess() {
        String email = "abc@gmail.com";
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.anyString())).thenReturn(apartment);
        List<ResidentCard> listResidentCard = new ArrayList<>();
        Mockito.when(residentCardDAO.checkRegisterCard(Mockito.anyLong())).thenReturn(listResidentCard);
        Mockito.when(statusResidentCardDAO.getOne(1L)).thenReturn(statusResidentCard);
        residentCardServiceImpl.addResidentCard(email);
    }

    @Test
    public void addResidentCardInWebTestSuccessWithlistMoreThenReRo() {
        String email = "abc@gmail.com";
        Mockito.when(accountDAO.getAccountByEmail(Mockito.anyString())).thenReturn(account);
        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any(), Mockito.anyString())).thenReturn(apartment);
        List<ResidentCard> listResidentCard = new ArrayList<>();
        listResidentCard.add(new ResidentCard());
        Mockito.when(residentCardDAO.checkRegisterCard(Mockito.anyLong())).thenReturn(listResidentCard);
        Mockito.when(statusResidentCardDAO.getOne(1L)).thenReturn(statusResidentCard);
        residentCardServiceImpl.addResidentCard(email);
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
