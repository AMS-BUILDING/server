package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Apartment;
import com.ams.building.server.bean.Notification;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.bean.RoomNumber;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.dao.ApartmentDAO;
import com.ams.building.server.dao.NotificationDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.PasswordRequest;
import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.request.UpdateResidentRequest;
import com.ams.building.server.response.AccountAppResponse;
import com.ams.building.server.response.LoginResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.utils.FileStore;
import com.ams.building.server.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ams.building.server.utils.ValidateUtil.isEmail;
import static com.ams.building.server.utils.ValidateUtil.isIdentifyCard;
import static com.ams.building.server.utils.ValidateUtil.isPassword;
import static com.ams.building.server.utils.ValidateUtil.isPhoneNumber;

@Transactional
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountDAO accountDao;

    @Autowired
    private ApartmentDAO apartmentDAO;

    @Autowired
    private NotificationDAO notificationDAO;

    @Override
    public void add(LoginResponse loginResponse) {
        if (Objects.isNull(loginResponse)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getPassword())) {
            throw new RestApiException(StatusCode.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (!isEmail(loginResponse.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        if (!isIdentifyCard(loginResponse.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (!isPhoneNumber(loginResponse.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
        }
        Account currentAccount = accountDao.getAccountByEmail(loginResponse.getEmail().trim());
        if (Objects.nonNull(currentAccount)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        Account account = new Account();
        account.setEmail(loginResponse.getEmail().trim());
        account.setPassword(loginResponse.getPassword().trim());
        account.setEnabled(loginResponse.getEnabled());
        account.setPhone(loginResponse.getPhone().trim());
        account.setCurrentAddress(loginResponse.getCurrentAddress().trim());
        account.setName(loginResponse.getName().trim());
        Role role = new Role();
        role.setId(loginResponse.getId());
        account.setRole(role);
        account.setDob(loginResponse.getDob());
        account.setHomeTown(loginResponse.getHomeTown().trim());
        account.setImage(loginResponse.getImage().trim());
        accountDao.save(account);
        loginResponse.setId(account.getId());
    }

    @Override
    public void update(LoginResponse loginResponse) {
        if (Objects.isNull(loginResponse)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getPassword())) {
            throw new RestApiException(StatusCode.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (!isEmail(loginResponse.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        if (!isIdentifyCard(loginResponse.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (!isPhoneNumber(loginResponse.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
        }
        Account account = accountDao.getAccountById(loginResponse.getId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        account.setEmail(loginResponse.getEmail().trim());
        account.setPassword(loginResponse.getPassword().trim());
        account.setEnabled(loginResponse.getEnabled());
        account.setPhone(loginResponse.getPhone().trim());
        account.setCurrentAddress(loginResponse.getCurrentAddress().trim());
        account.setName(loginResponse.getName().trim());
        Role role = new Role();
        role.setId(loginResponse.getId());
        account.setRole(role);
        account.setDob(loginResponse.getDob());
        account.setHomeTown(loginResponse.getHomeTown().trim());
        account.setIdentifyCard(loginResponse.getIdentifyCard().trim());
        if (account.getImage() != null) {
            String image = account.getImage();
            FileStore.deleteFile(image);
            account.setImage(loginResponse.getImage());
        }
        accountDao.save(account);
    }

    @Override
    public void changePassword(LoginResponse loginResponse) {
        if (Objects.isNull(loginResponse)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getPassword())) {
            throw new RestApiException(StatusCode.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (StringUtils.isEmpty(loginResponse.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (!isEmail(loginResponse.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        if (!isIdentifyCard(loginResponse.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (!isPhoneNumber(loginResponse.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
        }
        Account account = accountDao.getAccountById(loginResponse.getId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        if (!PasswordGenerator.checkHashStrings(loginResponse.getPassword(), account.getPassword())) {
            throw new RestApiException(StatusCode.PASSWORD_NOT_MATCH);
        }
        account.setPassword(PasswordGenerator.getHashString(loginResponse.getPassword()));
        accountDao.save(account);
    }

    @Override
    public void updateProfile(LoginResponse accountDTO) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Account account = accountDao.getAccountById(currentUser.getId());

        if (Objects.isNull(accountDTO)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(accountDTO.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(accountDTO.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (StringUtils.isEmpty(accountDTO.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (StringUtils.isEmpty(accountDTO.getDob())) {
            throw new RestApiException(StatusCode.DOB_EMPTY);
        }
        if (!StringUtils.isEmpty(accountDTO.getIdentifyCard())) {
            if (!isIdentifyCard(accountDTO.getIdentifyCard().trim())) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
            }
            if (!account.getIdentifyCard().equalsIgnoreCase(accountDTO.getIdentifyCard())) {
                Account currentAccount = accountDao.getAccountByIdentify(accountDTO.getIdentifyCard().trim());
                if (Objects.nonNull(currentAccount)) {
                    throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
                }
            }
        }
        if (!StringUtils.isEmpty(accountDTO.getPhone())) {
            if (!isPhoneNumber(accountDTO.getPhone())) {
                throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
            }
            if (!account.getPhone().equalsIgnoreCase(accountDTO.getPhone())) {
                List<String> currentAccount = accountDao.getAccountByPhoneNumber(accountDTO.getPhone().trim());
                if (!currentAccount.isEmpty()) {
                    throw new RestApiException(StatusCode.PHONE_REGISTER_BEFORE);
                }
            }
        }
        String image1 = FileStore.getFilePath(accountDTO.getMultipartFile(), "-user");
        if (image1 != null) {
            accountDTO.setImage(image1);
        }

        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        account.setName(accountDTO.getName().trim());
        account.setPhone(accountDTO.getPhone().trim());
        account.setIdentifyCard(accountDTO.getIdentifyCard().trim());
        account.setDob(accountDTO.getDob().trim());
        account.setCurrentAddress(accountDTO.getCurrentAddress().trim());
        account.setHomeTown(accountDTO.getHomeTown().trim());
        if (image1 != null) {
            if (account.getImage() != null) {
                String image = account.getImage();
                FileStore.deleteFile(image);
            }
            account.setImage(accountDTO.getImage());
        }
        accountDao.save(account);
    }

    @Transactional
    @Override
    public void delete(Long id, Long apartmentId) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(apartmentId)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Apartment apartment = apartmentDAO.getApartmentById(apartmentId);
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        if (account.getRole().getId() == 3) {
            throw new RestApiException(StatusCode.ACCOUNT_CAN_NOT_REMOVE);
        }
        List<Notification> listNotificationByAccount = notificationDAO.listNotificationByAccountId(id);
        if (!listNotificationByAccount.isEmpty()) {
            for (Notification n : listNotificationByAccount) {
                notificationDAO.deleteById(n.getId());
            }
        }
        apartmentDAO.deleteById(apartmentId);
        accountDao.deleteById(id);
    }

    @Override
    public LoginResponse getById(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        LoginResponse response = convertToAccountResponse(account);
        return response;
    }

    @Override
    public Long count() {
        return accountDao.count();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(email)) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (!isEmail(email)) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        Account account = accountDao.getAccountByEmail(email.trim());
        if (Objects.isNull(account)) {
            throw new UsernameNotFoundException(StatusCode.ACCOUNT_NOT_EXIST.getMessage());
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
        UserPrincipal accountDTO = new UserPrincipal(account.getEmail(), account.getPassword(), account.getEnabled(), true, true,
                true, authorities);
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setRoleId(account.getRole().getId());
        return accountDTO;
    }

    @Override
    public Long addApartmentOwner(ApartmentOwnerRequest ownerRequest) {
        if (Objects.isNull(ownerRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getDob())) {
            throw new RestApiException(StatusCode.DOB_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (!isEmail(ownerRequest.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        if (!isIdentifyCard(ownerRequest.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
        }
        if (!isPhoneNumber(ownerRequest.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
        }
        Account currentAccount = accountDao.getAccountByEmail(ownerRequest.getEmail().trim());
        if (Objects.nonNull(currentAccount)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        Account currentAccountDuplicate = accountDao.getAccountByIdentify(ownerRequest.getIdentifyCard().trim());
        if (Objects.nonNull(currentAccountDuplicate)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        Account account = new Account();
        account.setEmail(ownerRequest.getEmail().trim());
        account.setPassword(Constants.DEFAULT_PASSWORD);
        account.setGender(ownerRequest.getGender());
        account.setIdentifyCard(ownerRequest.getIdentifyCard().trim());
        account.setEnabled(true);
        account.setPhone(ownerRequest.getPhone().trim());
        account.setCurrentAddress(ownerRequest.getCurrentAddress().trim());
        account.setName(ownerRequest.getName().trim());
        account.setImage(Constants.DEFAULT_AVATAR);
        Role role = new Role();
        role.setId(3L);
        account.setRole(role);
        account.setDob(ownerRequest.getDob().trim());
        account.setHomeTown(ownerRequest.getHomeTown().trim());
        accountDao.save(account);
        Long id = account.getId();
        return id;
    }

    @Override
    public List<Long> addListResident(List<ResidentRequest> residentRequestList) {
        List<Long> residentIds = new ArrayList<>();
        residentRequestList.forEach(residentRequest ->
                residentIds.add(addResident(residentRequest))
        );
        return residentIds;
    }

    @Override
    public void updateResident(UpdateResidentRequest residentRequest) {
        if (Objects.isNull(residentRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(residentRequest.getIdentifyCard()))
            if (StringUtils.isEmpty(residentRequest.getName())) {
                throw new RestApiException(StatusCode.NAME_EMPTY);
            }
        if (StringUtils.isEmpty(residentRequest.getDob())) {
            throw new RestApiException(StatusCode.DOB_EMPTY);
        }
        Account account = accountDao.getAccountById(residentRequest.getAccountId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }

        Long roleId = account.getRole().getId();
        if (roleId == 3) {
            if (!StringUtils.isEmpty(residentRequest.getPositionId())) {
                throw new RestApiException(StatusCode.ACCOUNT_NOT_NEED_CHOOSE_POSITION);
            }
            if (StringUtils.isEmpty(residentRequest.getIdentifyCard())) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
            }
            if (!isIdentifyCard(residentRequest.getIdentifyCard())) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
            }
            if (!account.getIdentifyCard().equalsIgnoreCase(residentRequest.getIdentifyCard())) {
                Account accountCheck = accountDao.getAccountByIdentify(residentRequest.getIdentifyCard());
                if (Objects.nonNull(accountCheck)) {
                    throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
                }
            }
            if (StringUtils.isEmpty(residentRequest.getEmail())) {
                throw new RestApiException(StatusCode.EMAIL_EMPTY);
            }
            if (!isEmail(residentRequest.getEmail())) {
                throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
            }
            if (!account.getEmail().equalsIgnoreCase(residentRequest.getEmail())) {
                Account accountCheck = accountDao.getAccountByEmail(residentRequest.getEmail());
                if (Objects.nonNull(accountCheck)) {
                    throw new RestApiException(StatusCode.EMAIL_REGISTER_BEFORE);
                }
            }
            if (StringUtils.isEmpty(residentRequest.getPhone())) {
                throw new RestApiException(StatusCode.PHONE_EMPTY);
            }
            if (!isPhoneNumber(residentRequest.getPhone().trim())) {
                throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
            }
            if (!account.getPhone().equalsIgnoreCase(residentRequest.getPhone())) {
                List<String> accountsByPhone = accountDao.getAccountByPhoneNumber(residentRequest.getPhone());
                if (!accountsByPhone.isEmpty()) {
                    throw new RestApiException(StatusCode.PHONE_REGISTER_BEFORE);
                }
            }

        } else {
            if (!StringUtils.isEmpty(residentRequest.getIdentifyCard())) {
                if (!isIdentifyCard(residentRequest.getIdentifyCard().trim())) {
                    throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
                }
                if (!residentRequest.getIdentifyCard().equalsIgnoreCase(account.getIdentifyCard())) {
                    Account accountCheck = accountDao.getAccountByIdentify(residentRequest.getIdentifyCard());
                    if (Objects.nonNull(accountCheck)) {
                        throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
                    }
                }
                account.setIdentifyCard(residentRequest.getIdentifyCard().trim());
            } else {
                account.setIdentifyCard(null);
            }
            if (!StringUtils.isEmpty(residentRequest.getPhone())) {
                if (!isPhoneNumber(residentRequest.getPhone().trim())) {
                    throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
                }
                if (!residentRequest.getPhone().equalsIgnoreCase(account.getPhone())) {
                    List<String> accountsByPhone = accountDao.getAccountByPhoneNumber(residentRequest.getPhone());
                    if (!accountsByPhone.isEmpty()) {
                        throw new RestApiException(StatusCode.PHONE_REGISTER_BEFORE);
                    }
                }
                account.setPhone(residentRequest.getPhone().trim());
            } else {
                account.setPhone(null);
            }
            if (!StringUtils.isEmpty(residentRequest.getEmail())) {
                if (!isEmail(residentRequest.getEmail().trim())) {
                    throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
                }
                if (!residentRequest.getEmail().equalsIgnoreCase(account.getEmail())) {
                    Account accountCheck = accountDao.getAccountByEmail(residentRequest.getEmail());
                    if (Objects.nonNull(accountCheck)) {
                        throw new RestApiException(StatusCode.EMAIL_REGISTER_BEFORE);
                    }
                }
                account.setEmail(residentRequest.getEmail().trim());
            } else {
                account.setEmail(null);
            }
            if (StringUtils.isEmpty(residentRequest.getPositionId())) {
                throw new RestApiException(StatusCode.POSITION_NOT_EXIST);
            }
            if (residentRequest.getPositionId() < 5 || residentRequest.getPositionId() > 14) {
                throw new RestApiException(StatusCode.POSITION_MUST_BE_IN_HOME);
            }
        }

        account.setName(residentRequest.getName().trim());
        account.setGender(residentRequest.getGender());
        account.setDob(residentRequest.getDob().trim());
        account.setHomeTown(residentRequest.getHomeTown().trim());
        account.setCurrentAddress(residentRequest.getCurrentAddress().trim());
        if (roleId != 3) {
            Position position = new Position();
            position.setId(residentRequest.getPositionId());
            account.setPosition(position);
        } else {
            account.setPosition(null);
        }
        accountDao.save(account);
    }

    @Override
    public AccountAppResponse detailAccountApp(Long id) {
        if (Objects.isNull(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        AccountAppResponse response = convertToAccountApp(account);
        return response;
    }

    @Override
    public void changePassword(Long id, PasswordRequest request) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(request.getNewPassword()) || StringUtils.isEmpty(request.getOldPassword())) {
            throw new RestApiException(StatusCode.PASSWORD_EMPTY);
        }
        if (!isPassword(request.getNewPassword())) {
            throw new RestApiException(StatusCode.PASSWORD_NOT_RIGHT_FORMAT);
        }
        Account currenAccount = accountDao.getAccountById(id);
        if (Objects.isNull(currenAccount)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }

        if (PasswordGenerator.checkHashStrings(request.getOldPassword(), currenAccount.getPassword())) {
            if (PasswordGenerator.checkHashStrings(request.getNewPassword(), currenAccount.getPassword())) {
                throw new RestApiException(StatusCode.PASSWORD_USED);
            }
            currenAccount.setPassword(PasswordGenerator.getHashString(request.getNewPassword()));
            accountDao.save(currenAccount);
        }
    }

    @Override
    public void validateApartmentOwner(ApartmentOwnerRequest ownerRequest) {
        // Validate format
        if (Objects.isNull(ownerRequest)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getDob())) {
            throw new RestApiException(StatusCode.DOB_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getCurrentAddress())) {
            throw new RestApiException(StatusCode.CURRENT_ADDRESS_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
        }
        if (StringUtils.isEmpty(ownerRequest.getHomeTown())) {
            throw new RestApiException(StatusCode.HOME_TOWN_EMPTY);
        }
        if (!isEmail(ownerRequest.getEmail())) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        if (!isIdentifyCard(ownerRequest.getIdentifyCard())) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
        }
        if (!isPhoneNumber(ownerRequest.getPhone())) {
            throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
        }

        // Validate exist email or identify card in DB
        Account currentAccount = accountDao.getAccountByEmail(ownerRequest.getEmail());
        if (Objects.nonNull(currentAccount)) {
            throw new RestApiException(StatusCode.EMAIL_REGISTER_BEFORE);
        }
        Account currentAccountDuplicate = accountDao.getAccountByIdentify(ownerRequest.getIdentifyCard());
        if (Objects.nonNull(currentAccountDuplicate)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        List<String> phones = accountDao.getAccountByPhoneNumber(ownerRequest.getPhone());
        if (phones.size() > 0) {
            throw new RestApiException(StatusCode.PHONE_REGISTER_BEFORE);
        }
    }

    @Override
    public void validateListResident(List<ResidentRequest> residentRequestList, ApartmentOwnerRequest ownerRequest) {
        if (residentRequestList.isEmpty()) {
            return;
        }
        // Get identifyCar, email to check
        List<String> emailList = new ArrayList<>();
        List<String> identifyCardList = new ArrayList<>();
        List<String> phoneNumberList = new ArrayList<>();

        residentRequestList.forEach(request -> {
            // Validate request format
            if (Objects.isNull(request)) {
                throw new RestApiException(StatusCode.DATA_EMPTY);
            }
            if (StringUtils.isEmpty(request.getName())) {
                throw new RestApiException(StatusCode.NAME_EMPTY);
            }
            if (StringUtils.isEmpty(request.getDob())) {
                throw new RestApiException(StatusCode.DOB_EMPTY);
            }
            if (!StringUtils.isEmpty(request.getIdentifyCard())) {
                if (!isIdentifyCard(request.getIdentifyCard().trim())) {
                    throw new RestApiException(StatusCode.IDENTIFY_CARD_NOT_RIGHT);
                }
            }
            if (!StringUtils.isEmpty(request.getPhone())) {
                if (!isPhoneNumber(request.getPhone().trim())) {
                    throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
                }
            }
            if (!StringUtils.isEmpty(request.getEmail())) {
                if (!isEmail(request.getEmail())) {
                    throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
                }
            }
            if (!StringUtils.isEmpty(request.getEmail())) {
                emailList.add(request.getEmail());
            }
            if (!StringUtils.isEmpty(request.getIdentifyCard())) {
                identifyCardList.add(request.getIdentifyCard());
            }
            if (!StringUtils.isEmpty(request.getPhone())) {
                phoneNumberList.add(request.getPhone());
            }
        });

        // add owner to list
        emailList.add(ownerRequest.getEmail());
        identifyCardList.add(ownerRequest.getIdentifyCard());
        phoneNumberList.add(ownerRequest.getPhone());

        // remove element duplicate in list
        List<String> identifyCardListDistinct = identifyCardList.stream().distinct().collect(Collectors.toList());
        List<String> emailListDistinct = emailList.stream().distinct().collect(Collectors.toList());
        List<String> phoneNumberListDistinct = phoneNumberList.stream().distinct().collect(Collectors.toList());

        // check duplicate email , identify card in list resident
        if (emailList.size() != emailListDistinct.size()) {
            throw new RestApiException(StatusCode.DUPLICATE_EMAIL_IN_LIST_RESIDENT);
        }
        if (identifyCardList.size() != identifyCardListDistinct.size()) {
            throw new RestApiException(StatusCode.DUPLICATE_IDENTIFY_CARD_IN_LIST_RESIDENT);
        }
        if (phoneNumberList.size() != phoneNumberListDistinct.size()) {
            throw new RestApiException(StatusCode.DUPLICATE_PHONE_NUMBER_IN_LIST_RESIDENT);
        }
        // Get Map list email, identify
        Map<String, Account> accountMapByEmail = accountDao.getAccountByListEmail(emailListDistinct).stream()
                .collect(Collectors.toMap(Account::getEmail, account -> account));
        Map<String, Account> accountMapByIdentifyCard = accountDao.getAccountByListIdentifyCard(identifyCardListDistinct).stream()
                .collect(Collectors.toMap(Account::getIdentifyCard, account -> account));
        Map<String, Account> accountMapByPhoneNumber = accountDao.getAccountByPhoneNumberList(phoneNumberListDistinct).stream()
                .collect(Collectors.toMap(Account::getPhone, account -> account));

        for (ResidentRequest request : residentRequestList) {
            // Validate duplicate email or identify in DB
            Account accountByEmail = accountMapByEmail.get(request.getEmail());
            if (Objects.nonNull(accountByEmail)) {
                throw new RestApiException(StatusCode.EMAIL_REGISTER_BEFORE);
            }
            Account accountByIdentifyCard = accountMapByIdentifyCard.get(request.getIdentifyCard());
            if (Objects.nonNull(accountByIdentifyCard)) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
            }
            Account accountByPhoneNumber = accountMapByPhoneNumber.get(request.getPhone());
            if (Objects.nonNull(accountByPhoneNumber)) {
                throw new RestApiException(StatusCode.PHONE_REGISTER_BEFORE);
            }
        }
    }

    @Override
    public Long roleIdAccountByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        Account account = accountDao.getAccountByEmail(email.trim());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        Long roleId = account.getRole().getId();
        return roleId;
    }

    private AccountAppResponse convertToAccountApp(Account account) {
        AccountAppResponse response = AccountAppResponse.builder().build();
        Apartment apartment = apartmentDAO.getApartmentByAccountId(account.getId());
        if (Objects.isNull(apartment)) {
            throw new RestApiException(StatusCode.APARTMENT_NOT_EXIST);
        }
        RoomNumber roomNumber = apartment.getRoomNumber();
        if (Objects.isNull(roomNumber)) {
            throw new RestApiException(StatusCode.ROOM_NUMBER_NOT_EXIST);
        }
        response.setId(account.getId());
        response.setName(account.getName());
        response.setRoomNumber(roomNumber.getRoomName());
        response.setDob(account.getDob());
        response.setIdentifyCard(account.getIdentifyCard());
        response.setEmail(account.getEmail());
        response.setPhoneNumber(account.getPhone());
        response.setCurrentAddress(account.getCurrentAddress());
        response.setImageAvatar(account.getImage());
        response.setPassword(PasswordGenerator.getHashString(account.getPassword()));
        return response;
    }

    private Long addResident(ResidentRequest residentRequest) {
        Account account = new Account();
        account.setEnabled(true);
        account.setName(residentRequest.getName().trim());
        account.setPassword(Constants.DEFAULT_PASSWORD);
        if (!StringUtils.isEmpty(residentRequest.getIdentifyCard())) {
            account.setIdentifyCard(residentRequest.getIdentifyCard());
        }
        Role role = new Role();
        role.setId(5L);
        account.setRole(role);
        if (!StringUtils.isEmpty(residentRequest.getEmail())) {
            account.setEmail(residentRequest.getEmail());
        }
        account.setPhone(residentRequest.getPhone().trim());
        account.setGender(residentRequest.getGender());
        account.setDob(residentRequest.getDob().trim());
        account.setImage(Constants.DEFAULT_AVATAR);
        Position position = new Position();
        position.setId(residentRequest.getPositionId());
        account.setPosition(position);
        accountDao.save(account);
        return account.getId();
    }

    private LoginResponse convertToAccountResponse(Account account) {
        LoginResponse response = LoginResponse.builder().build();
        response.setId(account.getId());
        response.setName(account.getName());
        response.setEnabled(account.getEnabled());
        response.setEmail(account.getEmail());
        response.setPassword(account.getPassword());
        response.setRoleId(account.getRole().getId());
        response.setPhone(account.getPhone());
        response.setCurrentAddress(account.getCurrentAddress());
        response.setImage(account.getImage());
        response.setDob(account.getDob());
        response.setHomeTown(account.getHomeTown());
        response.setIdentifyCard(account.getIdentifyCard());
        response.setGender(account.getGender());
        return response;
    }

}
