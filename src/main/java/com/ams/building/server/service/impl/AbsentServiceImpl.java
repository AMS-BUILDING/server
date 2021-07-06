package com.ams.building.server.service.impl;

import com.ams.building.server.bean.AbsentDetail;
import com.ams.building.server.bean.AbsentType;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Block;
import com.ams.building.server.bean.FloorBlock;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AbsentDetailDAO;
import com.ams.building.server.dao.AbsentTypeDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.AbsentRequest;
import com.ams.building.server.response.AbsentResponse;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.service.AbsentService;
import com.ams.building.server.utils.DateTimeUtils;
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

import static com.ams.building.server.utils.DateTimeUtils.convertDateToStringWithTimezone;
import static com.ams.building.server.utils.ValidateUtil.isIdentifyCard;

@Transactional
@Service
public class AbsentServiceImpl implements AbsentService {

    private static final Logger logger = Logger.getLogger(AbsentServiceImpl.class);

    @Autowired
    private AbsentDetailDAO absentDao;

    @Autowired
    private AbsentTypeDAO absentTypeDAO;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Override
    public ApiResponse absentList(String name, String identifyCard, Long absentType, Integer page, Integer size) {
        List<AbsentResponse> absentResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<AbsentDetail> absentDetails;
        if (absentType == -1) {
            absentDetails = absentDao.absentListNotByAbsentType(name, identifyCard, pageable);
        } else {
            absentDetails = absentDao.absentList(name, identifyCard, absentType, pageable);
        }
        for (AbsentDetail ad : absentDetails) {
            AbsentResponse response = covertAbsentDetailToDTO(ad);
            absentResponses.add(response);
        }
        Long totalElement = absentDetails.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(absentResponses).totalElement(totalElement).build();
        return response;
    }

    @Override
    public void exportAbsentDetailList(HttpServletResponse response, String name, String identifyCard, Long absentType) {
        try {
            Pageable pageable = PageRequest.of(0, 50000);
            Page<AbsentDetail> absentDetails;
            if (absentType != -1) {
                absentDetails = absentDao.absentList(name, identifyCard, absentType, pageable);
            } else {
                absentDetails = absentDao.absentListNotByAbsentType(name, identifyCard, pageable);
            }
            String csvFileName = "Absent Detail.csv";
            response.setContentType(Constants.TEXT_CSV);
            // creates mock data
            String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
            response.setHeader(Constants.HEADER_KEY, headerValue);
            // uses the Super CSV API to generate CSV data from the model data
            final byte[] bom = new byte[]{(byte) 239, (byte) 187, (byte) 191};
            OutputStream os = response.getOutputStream();
            os.write(bom);
            final PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            w.println("Tên ,Chứng minh thư,Loại đăng kí,Lí do,Quê quán, Số block, Số phòng, Ngày bắt đầu,Ngày kết thúc");
            if (!CollectionUtils.isEmpty((Collection<?>) absentDetails)) {
                for (AbsentDetail absentDetail : absentDetails) {
                    w.println(writeAbsentDetail(absentDetail));
                }
            }
            w.flush();
            w.close();
        } catch (Exception e) {
            logger.error("exportAbsentDetailList error: " + e);
            throw new RestApiException(StatusCode.ERROR_UNKNOWN);
        }
    }

    @Override
    public void addAbsentDetail(AbsentRequest request) {
        if (Objects.isNull(request)) {
            throw new RestApiException(StatusCode.ABSENT_DETAIL_NOT_EXIST);
        }
        if (StringUtils.isEmpty(request.getName()) || StringUtils.isEmpty(request.getIdentifyCard()) || StringUtils.isEmpty(request.getDob()) || StringUtils.isEmpty(request.getAbsentType()) || StringUtils.isEmpty(request.getHomeTown())) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        AbsentType absentType = absentTypeDAO.findAbsentTypeById(request.getAbsentType());
        if (Objects.isNull(absentType)) {
            throw new RestApiException(StatusCode.ABSENT_TYPE_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentByAccountId(request.getAccountDetailId());
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        if (!isIdentifyCard(request.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
        }
        AbsentDetail findAbsent = absentDao.getAbsentDetailByIdentityCardAndAbsentType(request.getIdentifyCard(), request.getAbsentType());
        if (Objects.nonNull(findAbsent)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUILCATE);
        }
        AbsentDetail absentDetail = new AbsentDetail();
        absentDetail.setAbsentType(absentType);
        absentDetail.setName(request.getName());
        absentDetail.setReason(request.getReason());
        absentDetail.setHomeTown(request.getHomeTown());
        absentDetail.setIdentifyCard(request.getIdentifyCard());
        absentDetail.setDob(convertDateToStringWithTimezone(request.getDob(), DateTimeUtils.DD_MM_YYYY, null));
        absentDetail.setEndDate(request.getEndDate());
        absentDetail.setStartDate(request.getStartDate());
        absentDetail.setApartment(apartment);
        absentDao.save(absentDetail);
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

    private String writeAbsentDetail(AbsentDetail absentDetail) {
        String content = "";
        try {
            String name = "";
            String identifyCard = "";
            String absentType = "";
            String homeTown = "";
            String block = "";
            String roomNumber = "";
            String reason = "";
            String startDate = convertDateToStringWithTimezone(absentDetail.getStartDate(), DateTimeUtils.DD_MM_YYYY, null);
            String endDate = convertDateToStringWithTimezone(absentDetail.getEndDate(), DateTimeUtils.DD_MM_YYYY, null);

            if (Objects.nonNull(absentDetail)) {
                name = absentDetail.getName();
                identifyCard = absentDetail.getIdentifyCard();
                homeTown = absentDetail.getHomeTown();
                reason = absentDetail.getReason();
            }
            if (Objects.nonNull(absentDetail.getApartment()) &&
                    Objects.nonNull(absentDetail.getApartment().getRoomNumber()) &&
                    Objects.nonNull(absentDetail.getApartment().getRoomNumber().getFloorBlock()) &&
                    Objects.nonNull(absentDetail.getApartment().getRoomNumber().getFloorBlock().getFloor()) &&
                    Objects.nonNull(absentDetail.getApartment().getRoomNumber().getFloorBlock().getBlock())) {
                roomNumber = absentDetail.getApartment().getRoomNumber().getRoomName();
                block = absentDetail.getApartment().getRoomNumber().getFloorBlock().getBlock().getBlockName();
            }
            if (Objects.nonNull(absentDetail.getAbsentType())) {
                absentType = absentDetail.getAbsentType().getAbsentType();
            }
            content = name + "," + identifyCard + "," + absentType + "," + reason + "," + homeTown
                    + "," + block + "," + roomNumber + "," + startDate + "," + endDate;

        } catch (Exception e) {
            logger.error("writeAbsentDetail error", e);
        }
        return content;
    }
}

