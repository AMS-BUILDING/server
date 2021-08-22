package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.TypeApartment;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.RoleEnum;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.BlockDAO;
import com.ams.building.server.dao.FloorBlockDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.ApartmentResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.BlockResponse;
import com.ams.building.server.response.FloorResponse;
import com.ams.building.server.response.RoomNumberResponse;
import com.ams.building.server.service.ApartmentService;
import com.ams.building.server.utils.FileStore;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.ValidateUtil.isEmail;
import static com.ams.building.server.utils.ValidateUtil.isIdentifyCard;
import static com.ams.building.server.utils.ValidateUtil.isPhoneNumber;

@Transactional
@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final Logger logger = Logger.getLogger(ApartmentServiceImpl.class);

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private FloorBlockDAO floorBlockDAO;

    @Autowired
    private BlockDAO blockDAO;

    @Override
    public ApiResponse apartmentList(String roomName, String householderName, Integer page, Integer size) {
        List<ApartmentResponse> apartmentResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Apartment> apartments = apartmentDAO.searchApartmentByRoomNumberHouseholderName(roomName, householderName, pageable);

        for (Apartment a : apartments) {
            ApartmentResponse response = covertApartmentToDTO(a);
            apartmentResponses.add(response);
        }
        Long totalElements = apartments.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(apartmentResponses).totalElement(totalElements).build();

        return response;
    }

    @Override
    public void exportApartmentList(HttpServletResponse response, String roomName, String householderName) {
        try {
            Pageable pageable = PageRequest.of(0, 50000);
            Page<Apartment> apartments = apartmentDAO.searchApartmentByRoomNumberHouseholderName(roomName, householderName, pageable);
            String csvFileName = "ApartmentList.csv";
            response.setContentType(Constants.TEXT_CSV);
            // creates mock data
            String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
            response.setHeader(Constants.HEADER_KEY, headerValue);
            // uses the Super CSV API to generate CSV data from the model data
            final byte[] bom = new byte[]{(byte) 239, (byte) 187, (byte) 191};
            OutputStream os = response.getOutputStream();
            os.write(bom);
            final PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            w.println("Tòa , Số Phòng , Tên chủ hộ");
            if (!CollectionUtils.isEmpty((Collection<?>) apartments)) {
                for (Apartment apartment : apartments) {
                    w.println(writeApartment(apartment));
                }
            }
            w.flush();
            w.close();
        } catch (Exception e) {
            logger.error("exportApartmentList error: " + e);
            throw new RestApiException(StatusCode.ERROR_UNKNOWN);
        }
    }

    @Override
    public ApiResponse accountOfApartment(String name, String roomNumber, String phone, Integer page, Integer size) {
        List<AccountResponse> residentResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Apartment> apartments = apartmentDAO.searchResidentByNameRoomNumberAndPhone(name, roomNumber, phone, pageable);

        for (Apartment apartment : apartments) {
            AccountResponse response = convertApartmentToAccountResponse(apartment);
            residentResponses.add(response);
        }
        Long totalElements = apartments.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(residentResponses).totalElement(totalElements).build();
        return response;
    }

    @Override
    public void disableApartment(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        List<Apartment> apartmentList = apartmentDAO.searchAccountByRoomNumberId(id);
        if (apartmentList.isEmpty()) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        apartmentList.forEach(apartment -> disableAccount(apartment));
    }

    @Override
    public void addOwnerToApartment(Long apartmentId, Long ownerId) {
        if (StringUtils.isEmpty(apartmentId) || StringUtils.isEmpty(ownerId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(ownerId);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentById(apartmentId);
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        apartment.setAccount(account);
        apartmentDAO.save(apartment);
    }

    @Override
    public void addListResidentToApartment(Long apartmentId, List<Long> residentId) {
        if (StringUtils.isEmpty(apartmentId) || StringUtils.isEmpty(residentId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        residentId.forEach(id -> addResident(apartmentId, id));
    }

    @Override
    public List<BlockResponse> blockList() {
        List<Block> blocks = blockDAO.findAll();
        List<BlockResponse> responses = new ArrayList<>();
        blocks.forEach(s -> responses.add(convertBlock(s)));
        return responses;
    }

    @Override
    public List<FloorResponse> floorList(Long blockId) {
        List<FloorResponse> floors = floorBlockDAO.floorBlockByBlockId(blockId);
        return floors;
    }

    @Override
    public String typeApartmentByAccountId(Long accountId) {
        if (Objects.isNull(accountId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(accountId, String.valueOf(RoleEnum.ROLE_LANDLORD));
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_RIGHT_ROLE);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        TypeApartment typeApartment = roomNumber.getTypeApartment();
        if (Objects.isNull(typeApartment)) {
            throw new RestApiException(StatusCode.TYPE_APARTMENT_NOT_EXIST);
        }
        String typeName = typeApartment.getTypeName();
        return typeName;
    }

    @Override
    public List<RoomNumberResponse> roomNumberList(Long blockId, Long floorId) {
        List<Apartment> apartments = apartmentDAO.searchRoomNumberByBlockAndFloorNullAccount(blockId, floorId);
        List<RoomNumberResponse> responses = new ArrayList<>();
        apartments.forEach(s -> responses.add(convertRoomNumberToDTO(s)));
        return responses;
    }

    @Override
    public List<AccountResponse> dependentPerson(Long id) {
        if (Objects.isNull(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(id);
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        String roles = RoleEnum.ROLE_TENANT.toString();
        List<Apartment> apartments = apartmentDAO.searchResidentByNameRoomNumberAndPhoneList("", roomNumber.getRoomName(), "", roles);
        List<AccountResponse> residentResponses = new ArrayList<>();
        for (Apartment a : apartments) {
            AccountResponse response = convertApartmentToAccountResponse(a);
            residentResponses.add(response);
        }
        return residentResponses;
    }

    @Override
    public void addResidentToApartment(Long apartmentId, ResidentRequest request) {
        if (StringUtils.isEmpty(apartmentId) || Objects.isNull(request)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Apartment apartment = apartmentDAO.getApartmentById(apartmentId);
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        Account account = addAccountRoleResident(request);
        Apartment newApartment = new Apartment();
        newApartment.setAccount(account);
        newApartment.setRoomNumber(apartment.getRoomNumber());
        newApartment.setBuilding(apartment.getBuilding());
        apartmentDAO.save(newApartment);
    }


    private Account addAccountRoleResident(ResidentRequest request) {
        Account account = new Account();
        if (Objects.isNull(request)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(request.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (!StringUtils.isEmpty(request.getIdentifyCard())) {
            if (!isIdentifyCard(request.getIdentifyCard())) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
            }
            Account currentAccount = accountDAO.getAccountByIdentify(request.getIdentifyCard().trim());
            if (Objects.nonNull(currentAccount)) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
            }
            account.setIdentifyCard(request.getIdentifyCard());
        } else {
            account.setIdentifyCard(null);
        }
        if (!StringUtils.isEmpty(request.getPhone())) {
            if (!isPhoneNumber(request.getPhone())) {
                throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
            }
            List<String> phones = accountDAO.getAccountByPhoneNumber(request.getPhone().trim());
            if (phones.size() > 0) {
                throw new RestApiException(StatusCode.PHONE_REGISTER_BEFORE);
            }
        }
        if (!StringUtils.isEmpty(request.getEmail())) {
            if (!isEmail(request.getEmail())) {
                throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
            }
            Account currentAccount = accountDAO.getAccountByEmail(request.getEmail().trim());
            if (Objects.nonNull(currentAccount)) {
                throw new RestApiException(StatusCode.EMAIL_REGISTER_BEFORE);
            }
            account.setEmail(request.getEmail());
        } else {
            account.setEmail(null);
        }
        if (StringUtils.isEmpty(request.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (StringUtils.isEmpty(request.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        account.setName(request.getName().trim());
        account.setPhone(request.getPhone().trim());
        account.setGender(request.getGender());
        account.setPassword(Constants.DEFAULT_PASSWORD);
        account.setImage(FileStore.getDefaultAvatar());
        account.setDob(request.getDob());
        account.setEnabled(true);
        account.setHomeTown(request.getHomeTown().trim());
        account.setCurrentAddress(request.getCurrentAddress().trim());
        Role role = new Role();
        role.setId(5L);
        account.setRole(role);
        Position position = new Position();
        position.setId(request.getPositionId());
        account.setPosition(position);
        accountDAO.save(account);
        return account;
    }

    private RoomNumberResponse convertRoomNumberToDTO(Apartment apartment) {
        RoomNumberResponse response = RoomNumberResponse.builder()
                .apartmentId(apartment.getId())
                .roomName(apartment.getRoomNumber().getRoomName())
                .id(apartment.getRoomNumber().getId())
                .build();
        return response;
    }

    private ApartmentResponse covertApartmentToDTO(Apartment apartment) {
        Account account = apartment.getAccount();
        RoomNumber roomNumber = apartment.getRoomNumber();
        FloorBlock floorBlock = roomNumber.getFloorBlock();
        Block block = floorBlock.getBlock();

        ApartmentResponse response = ApartmentResponse.builder()
                .apartmentId(apartment.getId())
                .roomNumberId(roomNumber.getId())
                .accountId(account.getId())
                .ownerName(account.getName())
                .blockName(block.getBlockName())
                .roomName(roomNumber.getRoomName())
                .build();
        return response;
    }

    private String writeApartment(Apartment apartment) {
        String content = "";
        try {
            String block = "";
            String roomNumber = "";
            String apartmentOwnerName = "";

            if (Objects.nonNull(apartment.getAccount()) &&
                    Objects.nonNull(apartment.getAccount().getName())) {
                apartmentOwnerName = apartment.getAccount().getName();
            }

            if (Objects.nonNull(apartment.getRoomNumber()) &&
                    Objects.nonNull(apartment.getRoomNumber().getRoomName()) &&
                    Objects.nonNull(apartment.getRoomNumber().getFloorBlock()) &&
                    Objects.nonNull(apartment.getRoomNumber().getFloorBlock().getBlock()) &&
                    Objects.nonNull(apartment.getRoomNumber().getFloorBlock().getBlock().getBlockName())) {
                roomNumber = apartment.getRoomNumber().getRoomName();
                block = apartment.getRoomNumber().getFloorBlock().getBlock().getBlockName();
            }
            content = block + " , " + roomNumber + " , " + apartmentOwnerName;
        } catch (Exception e) {
            logger.error("writeApartment error", e);
        }
        return content;
    }

    private void addResident(Long apartmentId, Long id) {
        if (StringUtils.isEmpty(apartmentId) || StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDAO.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentById(apartmentId);
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        Apartment newApartment = new Apartment();
        newApartment.setAccount(account);
        newApartment.setRoomNumber(apartment.getRoomNumber());
        newApartment.setBuilding(apartment.getBuilding());

        apartmentDAO.save(newApartment);
    }

    private AccountResponse convertApartmentToAccountResponse(Apartment apartment) {
        Account account = apartment.getAccount();
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        FloorBlock floorBlock = roomNumber.getFloorBlock();
        if (Objects.isNull(floorBlock)) {
            throw new RestApiException(StatusCode.FLOOR_BLOCK_NOT_EXIST);
        }
        Block block = floorBlock.getBlock();
        if (Objects.isNull(block)) {
            throw new RestApiException(StatusCode.BLOCK_NOT_EXIST);
        }
        Position position = account.getPosition();
        AccountResponse response = AccountResponse.builder()
                .apartmentId(apartment.getId())
                .accountId(account.getId())
                .roomNumber(roomNumber.getRoomName())
                .blockName(block.getBlockName())
                .name(account.getName())
                .phone(account.getPhone())
                .dob(account.getDob())
                .gender(account.getGender())
                .identifyCard(account.getIdentifyCard())
                .homeTown(account.getHomeTown())
                .currentAddress(account.getCurrentAddress())
                .email(account.getEmail())
                .relationShip(position == null ? "Chủ hộ" : position.getName())
                .build();
        return response;
    }

    private void disableAccount(Apartment apartment) {
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        Account account = accountDAO.getAccountById(apartment.getAccount().getId());
        account.setEnabled(false);
        accountDAO.save(account);
    }

    private BlockResponse convertBlock(Block block) {
        if (Objects.isNull(block)) {
            throw new RestApiException(StatusCode.BLOCK_NOT_EXIST);
        }
        BlockResponse response = BlockResponse.builder().blockName(block.getBlockName()).id(block.getId()).build();
        return response;
    }

}
