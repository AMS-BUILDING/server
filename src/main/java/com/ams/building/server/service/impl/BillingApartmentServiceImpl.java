package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.ApartmentBilling;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.bean.StatusApartmentBilling;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.ApartmentBillingDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.BillingApartmentTotalResponse;
import com.ams.building.server.service.BillingApartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.HelperUtils.formatDoubleNUmber;

@Service
public class BillingApartmentServiceImpl implements BillingApartmentService {

    private static final Logger logger = Logger.getLogger(BillingApartmentServiceImpl.class);

    @Autowired
    private ApartmentBillingDAO apartmentBillingDAO;

    @Override
    public ApiResponse searchBuildingApartmentByMonth(Integer page, Integer size, String month) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApartmentBilling> apartmentBillings = apartmentBillingDAO.searchApartmentBillingByMonth(month, pageable);
        List<BillingApartmentTotalResponse> billingTotalResponse = new ArrayList<>();
        apartmentBillings.forEach(billing -> billingTotalResponse.add(convertToBillingTotalResponse(billing)));
        Long totalPage = apartmentBillings.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(billingTotalResponse).totalElement(totalPage).build();
        return response;
    }

    private BillingApartmentTotalResponse convertToBillingTotalResponse(ApartmentBilling billing) {
        BillingApartmentTotalResponse response = BillingApartmentTotalResponse.builder().build();
        Apartment apartment = billing.getApartment();
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
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
        StatusApartmentBilling status= billing.getStatusApartmentBilling();
        if(Objects.isNull(status)){
            throw new RestApiException(StatusCode.STATUS_NOT_EXIST);
        }
        response.setId(billing.getId());
        response.setBlockName(block.getBlockName());
        response.setRoomNumber(roomNumber.getRoomName());
        response.setTotalPrice(formatDoubleNUmber(billing.getTotalPrice()));
        response.setBillingMonth(billing.getBillingMonth());
        response.setStatusName(status.getStatusName());
        return response;
    }
}
