package com.ams.building.server.service;

import com.ams.building.server.bean.AbsentDetail;
import com.ams.building.server.bean.AbsentType;
import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.Building;
import com.ams.building.server.bean.Floor;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.dao.AbsentDetailDAO;
import com.ams.building.server.dao.AbsentTypeDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.AbsentRequest;
import com.ams.building.server.response.AbsentResponse;
import com.ams.building.server.service.impl.AbsentServiceImpl;
import com.ams.building.server.utils.DateTimeUtils;
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
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ams.building.server.utils.DateTimeUtils.convertDateToStringWithTimezone;

@RunWith(PowerMockRunner.class)
public class AbsentServiceTest {

    @InjectMocks
    private AbsentServiceImpl absentService;

    @Mock
    private AbsentDetailDAO absentDao;

    @Mock
    private ApartmentDAO apartmentDao;

    @Mock
    private AbsentTypeDAO absentTypeDAO;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void absentListSuccess() {
        Pageable pageable = PageRequest.of(1, 5);
        AbsentDetail absentDetail = new AbsentDetail();
        absentDetail.setId(1L);
        AbsentType absentType = new AbsentType();
        absentType.setId(1L);
        absentType.setAbsentType("sdfd");
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        absentDetail.setApartment(apartment);
        absentDetail.setAbsentType(absentType);
        absentDetail.setName("sfd");
        absentDetail.setReason("fdsa");
        absentDetail.setIdentifyCard("163446335");
        absentDetail.setHomeTown("agf");
        absentDetail.setDob("16/04/1999");
        absentDetail.setStartDate(new Date());
        absentDetail.setEndDate(new Date());
        List<AbsentDetail> absentDetails = new ArrayList<>();
        absentDetails.add(absentDetail);
        Page<AbsentDetail> absentDetailPage = new PageImpl<>(absentDetails, pageable, absentDetails.size());
        Mockito.when(absentDao.absentListNotByAbsentType(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(absentDetailPage);
        Mockito.when(absentDao.absentList(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject()))
                .thenReturn(absentDetailPage);
        List<AbsentResponse> accountDTOS = new ArrayList<>();
        absentDetails.forEach(a -> accountDTOS.add(covertAbsentDetailToDTO(a)));

        absentService.absentList("", "", 1L, 0, 5);
        absentService.absentList("", "", -1L, 0, 5);
    }

    @Test
    public void exportAbsentDetailListTestSuccess() {
        Pageable pageable = PageRequest.of(1, 5);
        AbsentDetail absentDetail = new AbsentDetail();
        absentDetail.setId(1L);
        AbsentType absentType = new AbsentType();
        absentType.setId(1L);
        absentType.setAbsentType("sdfd");
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        absentDetail.setApartment(apartment);
        absentDetail.setAbsentType(absentType);
        absentDetail.setName("sfd");
        absentDetail.setReason("fdsa");
        absentDetail.setIdentifyCard("163446335");
        absentDetail.setHomeTown("agf");
        absentDetail.setDob("16/04/1999");
        absentDetail.setStartDate(new Date());
        absentDetail.setEndDate(new Date());
        List<AbsentDetail> absentDetails = new ArrayList<>();
        absentDetails.add(absentDetail);
        Page<AbsentDetail> absentDetailPage = new PageImpl<>(absentDetails, pageable, absentDetails.size());
        Mockito.when(absentDao.absentListNotByAbsentType(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(absentDetailPage);
        Mockito.when(absentDao.absentList(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject()))
                .thenReturn(absentDetailPage);
        HttpServletResponse response = new MockHttpServletResponse();
        absentService.exportAbsentDetailList(response, "", "", 1L);
    }

    @Test
    public void exportAbsentDetailListTestSuccessAll() {
        Pageable pageable = PageRequest.of(1, 5);
        AbsentDetail absentDetail = new AbsentDetail();
        absentDetail.setId(1L);
        AbsentType absentType = new AbsentType();
        absentType.setId(1L);
        absentType.setAbsentType("sdfd");
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        absentDetail.setApartment(apartment);
        absentDetail.setAbsentType(absentType);
        absentDetail.setName("sfd");
        absentDetail.setReason("fdsa");
        absentDetail.setIdentifyCard("163446335");
        absentDetail.setHomeTown("agf");
        absentDetail.setDob("16/04/1999");
        absentDetail.setStartDate(new Date());
        absentDetail.setEndDate(new Date());
        List<AbsentDetail> absentDetails = new ArrayList<>();
        absentDetails.add(absentDetail);
        Page<AbsentDetail> absentDetailPage = new PageImpl<>(absentDetails, pageable, absentDetails.size());
        Mockito.when(absentDao.absentListNotByAbsentType(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject()))
                .thenReturn(absentDetailPage);
        Mockito.when(absentDao.absentList(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject()))
                .thenReturn(absentDetailPage);
        HttpServletResponse response = new MockHttpServletResponse();
        absentService.exportAbsentDetailList(response, "", "", -1L);
    }

    @Test
    public void exportAbsentDetailListTestFail() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Error Unknown");
        absentService.exportAbsentDetailList(null, "", "", -1L);
    }

    @Test
    public void addAbsentSuccess() {
        AbsentType absentType = new AbsentType(1L, "df");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("sdfs");
        request.setDob(new Date());
        request.setHomeTown("dfg");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);

        Mockito.when(absentTypeDAO.findAbsentTypeById(Mockito.anyLong())).thenReturn(absentType);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong(), Mockito.any())).thenReturn(apartment);
        Mockito.when(absentDao.getAbsentDetailByIdentityCardAndAbsentType(Mockito.anyString(), Mockito.any())).thenReturn(null);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByDataEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        AbsentRequest request = null;
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByDataObjectEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Dữ liệu trống");
        AbsentRequest request = AbsentRequest.builder().build();
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByNameEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Họ và tên không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("");
        request.setDob(new Date());
        request.setHomeTown("dfg");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByDobEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày sinh không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(null);
        request.setHomeTown("dfg");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByHomeTownEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Địa chỉ thường chú không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByReasonEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Lí do không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("vd");
        request.setReason("");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByStartDateEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày bắt đầu không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("dd");
        request.setReason("dfg");
        request.setStartDate(null);
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByEndDateEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Ngày kết đầu không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("dd");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(null);
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByIdentifyCardEmpty() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CMND không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("dd");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByIdentifyCardNotRightFormat() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD phải là số và phải có độ dài là 9 số hoặc 12 số");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("ff");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446sdf225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        AbsentType absentType = new AbsentType(1L, "df");
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);

        Mockito.when(absentTypeDAO.findAbsentTypeById(Mockito.anyLong())).thenReturn(absentType);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong(), Mockito.any())).thenReturn(apartment);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByAbsentNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Loại đăng ký không được để trống");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("dfghfdg");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        Mockito.when(absentTypeDAO.findAbsentTypeById(Mockito.anyLong())).thenReturn(null);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByApartmentNotExist() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Căn hộ không tồn tại");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("fgh");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        AbsentType absentType = new AbsentType(1L, "df");
        Mockito.when(absentTypeDAO.findAbsentTypeById(Mockito.anyLong())).thenReturn(absentType);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong(), Mockito.any())).thenReturn(null);
        absentService.addAbsentDetail(request);
    }

    @Test
    public void addAbsentFailByIdentifyCardDuplicate() {
        exceptionRule.expect(RestApiException.class);
        exceptionRule.expectMessage("Số CCCD đã được đăng ký");
        AbsentRequest request = AbsentRequest.builder().build();
        request.setName("dfg");
        request.setDob(new Date());
        request.setHomeTown("hfdgh");
        request.setReason("dfg");
        request.setStartDate(new Date());
        request.setEndDate(new Date());
        request.setIdentifyCard("163446225");
        request.setAbsentType(1L);
        request.setAccountId(1L);
        AbsentType absentType = new AbsentType(1L, "df");
        Building building = new Building(1L, "A01", "aa", "cccc");
        FloorBlock floorBlock = new FloorBlock(1L, new Block(1L, "a"), new Floor(1L, "b"));
        RoomNumber roomNumber = new RoomNumber(1L, new TypeApartment(1L, "a"), floorBlock, "roomName");
        Account account = new Account(1L, "loi", "loi@gmail", "0983302977", true, "123456", "123456789"
                , "image.jpg", "22201202", true, "homeTown", "currentAddress", new Role(1L, "ROLE_ADMIN")
                , new Position(1L, "chủ hộ", true), "1213", true);
        Apartment apartment = new Apartment(1L, account, building, roomNumber);
        Mockito.when(absentTypeDAO.findAbsentTypeById(Mockito.anyLong())).thenReturn(absentType);
        Mockito.when(apartmentDao.getApartmentByAccountId(Mockito.anyLong(), Mockito.any())).thenReturn(apartment);
        Mockito.when(absentDao.getAbsentDetailByIdentityCardAndAbsentType(Mockito.anyString(), Mockito.any())).thenReturn(new AbsentDetail());
        absentService.addAbsentDetail(request);
    }

    private AbsentResponse covertAbsentDetailToDTO(AbsentDetail absentDetail) {
        Apartment apartment = absentDetail.getApartment();
        RoomNumber roomNumber = apartment.getRoomNumber();
        FloorBlock floorBlock = roomNumber.getFloorBlock();
        Block block = floorBlock.getBlock();
        AbsentType absentType = absentDetail.getAbsentType();
        String startDate = convertDateToStringWithTimezone(absentDetail.getStartDate(), DateTimeUtils.DD_MM_YYYY, null);
        String endDate = convertDateToStringWithTimezone(absentDetail.getEndDate(), DateTimeUtils.DD_MM_YYYY, null);

        AbsentResponse response = AbsentResponse.builder()
                .absentDetailId(absentDetail.getId())
                .name(absentDetail.getName())
                .identifyCard(absentDetail.getIdentifyCard())
                .homeTown(absentDetail.getHomeTown())
                .block(block.getBlockName())
                .roomNumber(roomNumber.getRoomName())
                .startDate(startDate)
                .endDate(endDate)
                .absentType(absentType.getAbsentType())
                .reason(absentDetail.getReason())
                .build();
        return response;
    }

}
