package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.BlockDAO;
import com.ams.building.server.dao.FloorBlockDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.AccountDetailResponse;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.ApartmentResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.BlockResponse;
import com.ams.building.server.response.FloorResponse;
import com.ams.building.server.service.ApartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    public AccountDetailResponse getAccountDetail(Long accountId, Long apartmentId) {
        Apartment apartment = apartmentDAO.getAccountDetail(accountId, apartmentId);
        AccountDetailResponse accountDetailResponse = AccountDetailResponse.builder()
                .name(apartment.getAccount().getName())
                .gender(apartment.getAccount().getGender())
                .dob(apartment.getAccount().getDob())
                .phone(apartment.getAccount().getPhone())
                .email(apartment.getAccount().getEmail())
                .identityCard(apartment.getAccount().getIdentifyCard())
                .blockName(apartment.getRoomNumber().getFloorBlock().getBlock().getBlockName())
                .roomNumber(apartment.getRoomNumber().getRoomName())
                .currentAddress(apartment.getAccount().getCurrentAddress())
                .homeTown(apartment.getAccount().getHomeTown())
                .build();
        return accountDetailResponse;
    }

    @Override
    public List<Long> disableApartment(Long id) {
        List<Apartment> apartmentList = apartmentDAO.searchAccountByRoomNumberId(id);
        if (apartmentList != null) {
            apartmentList.forEach(apartment -> disableAccount(apartment));
        }
        return null;
    }

    @Override
    public void addOwnerToApartment(Long apartmentId, Long ownerId) {
        Account account = accountDAO.getAccountById(ownerId);
        Apartment apartment = apartmentDAO.getApartmentById(apartmentId);
        apartment.setAccount(account);
        apartmentDAO.save(apartment);
    }

    @Override
    public void addListResidentToApartment(Long apartmentId, List<Long> residentId) {
        residentId.forEach(id -> addResident(apartmentId, id));
    }

    @Override
    public List<BlockResponse> blockList() {
        List<Block> blocks = blockDAO.findAll();
        List<BlockResponse> responses = new ArrayList<>();
        blocks.forEach(s -> responses.add(convertBlock(s)));
        return responses;
    }

    private BlockResponse convertBlock(Block block) {
        BlockResponse response = BlockResponse.builder().blockName(block.getBlockName()).id(block.getId()).build();
        return response;
    }

    @Override
    public List<FloorResponse> floorList(Long blockId) {
        List<FloorBlock> floors = floorBlockDAO.floorBlockByBlockId(blockId);
        List<FloorResponse> responses = new ArrayList<>();
        floors.forEach(s -> responses.add(convertFloor(s)));
        return responses;
    }

    private FloorResponse convertFloor(FloorBlock floor) {
        FloorResponse response = FloorResponse.builder().floorName(floor.getFloor().getFloorName()).id(floor.getFloor().getId()).build();
        return response;
    }

    private ApartmentResponse covertApartmentToDTO(Apartment apartment) {
        Account account = apartment.getAccount();
        RoomNumber roomNumber = apartment.getRoomNumber();
        FloorBlock floorBlock = roomNumber.getFloorBlock();
        Block block = floorBlock.getBlock();

        ApartmentResponse response = ApartmentResponse.builder()
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
        Account account = accountDAO.getAccountById(id);
        Apartment apartment = apartmentDAO.getApartmentById(apartmentId);

        Apartment newApartment = Apartment.builder()
                .account(account)
                .building(apartment.getBuilding())
                .roomNumber(apartment.getRoomNumber())
                .build();

        apartmentDAO.save(newApartment);
    }

    private AccountResponse convertApartmentToAccountResponse(Apartment apartment) {
        AccountResponse response = AccountResponse.builder()
                .apartmentId(apartment.getId())
                .accountId(apartment.getAccount().getId())
                .roomNumber(apartment.getRoomNumber().getRoomName())
                .blockName(apartment.getRoomNumber().getFloorBlock().getBlock().getBlockName())
                .name(apartment.getAccount().getName())
                .phone(apartment.getAccount().getPhone())
                .build();
        return response;
    }

    private void disableAccount(Apartment apartment) {
        Account account = accountDAO.getAccountById(apartment.getAccount().getId());
        account.setEnabled(false);
        accountDAO.save(account);
    }

}
