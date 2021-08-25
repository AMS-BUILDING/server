package com.ams.building.server;

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
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.ResidentCardAddResponse;
import com.ams.building.server.response.ResidentCardResponse;
import com.ams.building.server.service.impl.ResidentCardServiceImpl;
import org.junit.Test;
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


    @Mock
    ResidentCardDAO residentCardDAO;

    @Mock
    AccountDAO accountDAO;

    @Mock
    ApartmentDAO apartmentDAO;

    @Mock
    StatusResidentCardDAO statusResidentCardDAO;

    @InjectMocks
    ResidentCardServiceImpl residentCardServiceImpl;

    Pageable pageable = PageRequest.of(1, 5);

    Account account = new Account(1L,"loi" , "loi@gmail","0983302977",true,"123456","123456789"
            ,"image.jpg" ,"22201202",true,"homeTown" ,"currentAddress" , new Role( 1L,"ROLE_ADMIN")
            ,new Position(1L,"ch? h?",true),"1213",true);

    Building building = new Building(1L,"A01","aa","cccc");

    FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"),new Floor(1L,"b"));

    RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L,"a") ,floorBlock ,"roomName");

    Apartment apartment = new Apartment(1L,account,building,roomNumber);

    List<Apartment> apartments = Arrays.asList(apartment);

    StatusResidentCard statusResidentCard = new StatusResidentCard(1L,"a");

    ResidentCard residentCard = new ResidentCard(1L,account,statusResidentCard,
            "cardCode",1000D,"price",new Date(),1);

    List<ResidentCard> residentCardList = Arrays.asList(residentCard);

    Page<ResidentCard> residentCards =new PageImpl<ResidentCard>(residentCardList,pageable,residentCardList.size());

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
        String billingMonth = monthStr + "/" + year;

        Mockito.when(residentCardDAO.searchResidentCardByAccountId(Mockito.any(),Mockito.anyString(),Mockito.anyObject())).thenReturn(residentCards);

        residentCards.forEach(s -> cardResponses.add(convertToCardResponse(s)));
        Long totalPage = residentCards.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(cardResponses).totalElement(totalPage).build();

        residentCardServiceImpl.getResidentCardByAccountId(1,5,1L);
    }

    @Test
    public void updateStatusResidentCard() {

        Mockito.when(residentCardDAO.getDetailResidentCardById(Mockito.any())).thenReturn(residentCard);
        Mockito.doNothing().when(residentCardDAO).updateStatus(Mockito.any(),Mockito.any() );

        residentCardServiceImpl.updateStatusResidentCard(1L,1L);
    }

    @Test
    public void removeResidentCard() {

        Mockito.when(residentCardDAO.getDetailResidentCardById(Mockito.any())).thenReturn(residentCard);
        Mockito.doNothing().when(residentCardDAO).cancelExtend(Mockito.any(),Mockito.any() );
        residentCardServiceImpl.removeResidentCard(1L);
    }


    @Test
    public void  addResidentCard() {

        Mockito.when(apartmentDAO.getApartmentByAccountId(Mockito.any() , Mockito.any())).thenReturn(apartment);

        RoomNumber roomNumber = apartment.getRoomNumber();

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }
        Long amount = 1L;
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

//            ResidentCard card  = new ResidentCard();

            Mockito.when(residentCardDAO.save(Mockito.any())).thenReturn(residentCard);
            System.out.println(residentCard.getId());
            ids.add(residentCard.getId());
        }

        ResidentCardAddResponse response = ResidentCardAddResponse.builder().build();
        response.setServiceId(ids);
        response.setTypeService(3L);

        residentCardServiceImpl.addResidentCard(1L,1L);

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
