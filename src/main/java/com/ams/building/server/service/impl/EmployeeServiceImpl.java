package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.PositionDAO;
import com.ams.building.server.dao.RoleDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.EmployeeRequest;
import com.ams.building.server.response.ApiResponse;
import com.ams.building.server.response.EmployeeResponse;
import com.ams.building.server.service.EmployeeService;
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

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private AccountDAO accountDao;

    @Autowired
    private PositionDAO positionDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public ApiResponse searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(Integer page, Integer size, String name, String phoneNumber, String identifyCard, Long position, String roles) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts;
        if (position == -1) {
            accounts = accountDao.searchAccountByNamePhoneIdentifyCardAndRole(name, phoneNumber, identifyCard, roles, pageable);
        } else {
            accounts = accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(name, phoneNumber, identifyCard, position, roles, pageable);
        }
        List<EmployeeResponse> accountDTOS = new ArrayList<>();
        accounts.forEach(a -> accountDTOS.add(convertEmployee(a)));
        Long totalPage = accounts.getTotalElements();
        ApiResponse response = ApiResponse.builder().data(accountDTOS).totalElement(totalPage).build();
        return response;
    }

    @Override
    public void removeEmployee(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        accountDao.removeAccount(id);
    }

    @Override
    public void updateEmployee(Long accountId, EmployeeRequest request) {
        if (Objects.isNull(request)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account currentAccount = accountDao.getAccountById(accountId);
        if (Objects.isNull(currentAccount)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        if (StringUtils.isEmpty(request.getDob())) {
            throw new RestApiException(StatusCode.DOB_EMPTY);
        }
        if (StringUtils.isEmpty(request.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (StringUtils.isEmpty(request.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (StringUtils.isEmpty(request.getPhoneNumber())) {
            throw new RestApiException(StatusCode.PHONE_EMPTY);
        }
        if (!isPhoneNumber(request.getPhoneNumber())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
        }
        if (StringUtils.isEmpty(request.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (!isIdentifyCard(request.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
        }
        Account accountByIdentifyCard = accountDao.getAccountByIdentify(request.getIdentifyCard());
        if (Objects.nonNull(accountByIdentifyCard)) {
            if (!accountByIdentifyCard.getIdentifyCard().equalsIgnoreCase(currentAccount.getIdentifyCard())) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
            }
        }
        currentAccount.setDob(request.getDob());
        currentAccount.setGender(request.getGender());
        currentAccount.setHomeTown(request.getHomeTown());
        currentAccount.setPhone(request.getPhoneNumber());
        currentAccount.setCurrentAddress(request.getCurrentAddress());
        currentAccount.setName(request.getName());
        currentAccount.setIdentifyCard(request.getIdentifyCard());
        Position position = positionDAO.getOne(request.getPosition());
        currentAccount.setPosition(position);
        Role role = roleDAO.getOne(4L);
        currentAccount.setRole(role);
        accountDao.save(currentAccount);
    }

    @Override
    public void addEmployee(EmployeeRequest request) {
        if (Objects.isNull(request) || (StringUtils.isEmpty(request.getName()) && StringUtils.isEmpty(request.getGender())
                && StringUtils.isEmpty(request.getPhoneNumber()) && StringUtils.isEmpty(request.getEmail())
                && StringUtils.isEmpty(request.getIdentifyCard()) && StringUtils.isEmpty(request.getCurrentAddress())
                && StringUtils.isEmpty(request.getHomeTown()) && StringUtils.isEmpty(request.getPosition()))) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(request.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(request.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (StringUtils.isEmpty(request.getDob())) {
            throw new RestApiException(StatusCode.DOB_EMPTY);
        }
        if (StringUtils.isEmpty(request.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (StringUtils.isEmpty(request.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (StringUtils.isEmpty(request.getPhoneNumber())) {
            throw new RestApiException(StatusCode.PHONE_EMPTY);
        }
        if (StringUtils.isEmpty(request.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (StringUtils.isEmpty(request.getPosition())) {
            throw new RestApiException(StatusCode.POSITION_NOT_EXIST);
        }
        if (request.getPosition() < 0 || request.getPosition() > 4) {
            throw new RestApiException(StatusCode.POSITION_NOT_RIGHT_WITH_EMPLOYEE);
        }
        if (!isEmail(request.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        if (!isPhoneNumber(request.getPhoneNumber())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
        }
        if (!isIdentifyCard(request.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
        }
        Account searchAccountByIdentify = accountDao.getAccountByIdentify(request.getIdentifyCard());
        if (Objects.nonNull(searchAccountByIdentify)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        List<String> searchAccountByPhone = accountDao.getAccountByPhoneNumber(request.getPhoneNumber());
        if (!searchAccountByPhone.isEmpty()) {
            throw new RestApiException(StatusCode.PHONE_REGISTER_BEFORE);
        }
        Account searchAccountByEmail = accountDao.getAccountByEmail(request.getEmail());
        if (Objects.nonNull(searchAccountByEmail)) {
            throw new RestApiException(StatusCode.EMAIL_REGISTER_BEFORE);
        }
        Account account = new Account();
        account.setIdentifyCard(request.getIdentifyCard());
        account.setEmail(request.getEmail());
        account.setDob(request.getDob());
        account.setGender(request.getGender());
        account.setHomeTown(request.getHomeTown());
        account.setPhone(request.getPhoneNumber());
        account.setCurrentAddress(request.getCurrentAddress());
        account.setName(request.getName());
        account.setPassword(Constants.DEFAULT_PASSWORD);
        account.setImage(FileStore.getDefaultAvatar());
        account.setEnabled(true);
        Position position = positionDAO.getOne(request.getPosition());
        account.setPosition(position);
        Role role = roleDAO.getOne(4L);
        account.setRole(role);
        accountDao.save(account);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long accountId) {
        if (StringUtils.isEmpty(accountId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(accountId);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        EmployeeResponse response = convertEmployee(account);
        return response;
    }

    @Override
    public void downloadSearchEmployee(HttpServletResponse response, String name, String phoneNumber, String identifyCard, Long position, String roles) {
        try {
            Pageable pageable = PageRequest.of(0, 5000);
            Page<Account> accounts;
            if (position != -1) {
                accounts = accountDao.searchAccountByNamePhoneIdentifyCardAndRole(name, identifyCard, phoneNumber, identifyCard, pageable);
            } else {
                accounts = accountDao.searchAccountByNamePhoneIdentifyCardAndRoleAndPosition(name, identifyCard, phoneNumber, position, roles, pageable);
            }
            String csvFileName = "Employee.csv";
            response.setContentType(Constants.TEXT_CSV);
            // creates mock data
            String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
            response.setHeader(Constants.HEADER_KEY, headerValue);
            // uses the Super CSV API to generate CSV data from the model data
            final byte[] bom = new byte[]{(byte) 239, (byte) 187, (byte) 191};
            OutputStream os = response.getOutputStream();
            os.write(bom);
            final PrintWriter w = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            w.println("Tên,Giới tính,Số điện thoại,Email,Ngày tháng năm sinh,Chứng minh thư, Địa chỉ hiện tại, Quê quán, Vị trí ");
            if (!CollectionUtils.isEmpty((Collection<?>) accounts)) {
                for (Account account : accounts) {
                    w.println(writeEmployee(account));
                }
            }
            w.flush();
            w.close();
        } catch (Exception e) {
            logger.error("exportAbsentDetailList error: " + e);
            throw new RestApiException(StatusCode.ERROR_UNKNOWN);
        }
    }

    private EmployeeResponse convertEmployee(Account account) {
        EmployeeResponse response = EmployeeResponse.builder().build();
        response.setId(account.getId());
        response.setName(account.getName());
        response.setEmail(account.getEmail());
        response.setPhone(account.getPhone());
        response.setCurrentAddress(account.getCurrentAddress());
        response.setDob(account.getDob());
        response.setHomeTown(account.getHomeTown());
        response.setIdentifyCard(account.getIdentifyCard());
        response.setGender(account.getGender());
        if (Objects.isNull(account.getPosition())) {
            throw new RestApiException(StatusCode.POSITION_NOT_EXIST);
        }
        response.setPositionName(account.getPosition().getName());
        return response;
    }

    private String writeEmployee(Account account) {
        String content = "";
        try {
            String name = "";
            String gender = "";
            String phoneNumber = "";
            String email = "";
            String dob = "";
            String identifyCard = "";
            String currentAddress = "";
            String homeTown = "";
            String position = "";
            if (Objects.nonNull(account)) {
                name = account.getName();
                phoneNumber = account.getPhone();
                email = account.getEmail();
                dob = account.getDob();
                identifyCard = account.getIdentifyCard();
                currentAddress = account.getCurrentAddress();
                homeTown = account.getHomeTown();
                gender = account.getGender() == true ? "Male" : "Female";
                if (Objects.nonNull(account.getPosition())) {
                    position = account.getPosition().getName();
                }
            }
            content = name + "," + gender + "," + phoneNumber + "," + email + "," + dob + "," + identifyCard + "," + currentAddress + "," + homeTown + "," + position;

        } catch (Exception e) {
            logger.error("writeEmployee error", e);
        }
        return content;
    }
}
