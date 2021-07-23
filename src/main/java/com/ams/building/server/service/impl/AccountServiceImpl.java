package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Position;
import com.ams.building.server.bean.Role;
import com.ams.building.server.constant.Constants;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.request.ApartmentOwnerRequest;
import com.ams.building.server.request.ResidentRequest;
import com.ams.building.server.request.UpdateResidentRequest;
import com.ams.building.server.response.LoginResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.utils.FileStore;
import com.ams.building.server.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ams.building.server.utils.ValidateUtil.isEmail;
import static com.ams.building.server.utils.ValidateUtil.isIdentifyCard;
import static com.ams.building.server.utils.ValidateUtil.isPhoneNumber;

@Transactional
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountDAO accountDao;

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
        Account currentAccount = accountDao.getAccountByEmail(loginResponse.getEmail());
        if (Objects.nonNull(currentAccount)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        Account account = new Account();
        account.setEmail(loginResponse.getEmail());
        account.setPassword(loginResponse.getPassword());
        account.setEnabled(loginResponse.getEnabled());
        account.setPhone(loginResponse.getPhone());
        account.setCurrentAddress(loginResponse.getCurrentAddress());
        account.setName(loginResponse.getName());
        Role role = Role.builder().id(loginResponse.getId()).build();
        account.setRole(role);
        account.setDob(loginResponse.getDob());
        account.setHomeTown(loginResponse.getHomeTown());
        account.setImage(loginResponse.getImage());
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
        account.setEmail(loginResponse.getEmail());
        account.setPassword(loginResponse.getPassword());
        account.setEnabled(loginResponse.getEnabled());
        account.setPhone(loginResponse.getPhone());
        account.setCurrentAddress(loginResponse.getCurrentAddress());
        account.setName(loginResponse.getName());
        Role role = Role.builder().id(loginResponse.getId()).build();
        account.setRole(role);
        account.setDob(loginResponse.getDob());
        account.setHomeTown(loginResponse.getHomeTown());
        account.setIdentifyCard(loginResponse.getIdentifyCard());
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
    public void updateProfile(LoginResponse loginResponse) {
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
        account.setName(loginResponse.getName());
        account.setPhone(loginResponse.getPhone());
    }

    @Override
    public void delete(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        accountDao.deleteById(id);
    }

    @Override
    public List<LoginResponse> find() {
        List<Account> accountList = accountDao.findAll();
        List<LoginResponse> loginResponse = new ArrayList<>();
        accountList.forEach(account -> {
            loginResponse.add(convertToAccountResponse(account));
        });
        return loginResponse;
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
    public LoginResponse getByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (!isEmail(email)) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        Account account = accountDao.getAccountByEmail(email);
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
    public void changeAccountLock(long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        account.setEnabled(!account.getEnabled());
        accountDao.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(email)) {
            throw new RestApiException(StatusCode.EMAIL_EMPTY);
        }
        if (!isEmail(email)) {
            throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
        }
        Account account = accountDao.getAccountByEmail(email);
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
        Account currentAccount = accountDao.getAccountByEmail(ownerRequest.getEmail());
        if (Objects.nonNull(currentAccount)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        Account currentAccountDuplicate = accountDao.getAccountByIdentify(ownerRequest.getIdentifyCard());
        if (Objects.nonNull(currentAccountDuplicate)) {
            throw new RestApiException(StatusCode.IDENTIFY_CARD_DUPLICATE);
        }
        Account account = new Account();
        account.setEmail(ownerRequest.getEmail());
        account.setPassword(Constants.DEFAULT_PASSWORD);
        account.setGender(ownerRequest.getGender());
        account.setIdentifyCard(ownerRequest.getIdentifyCard());
        account.setEnabled(true);
        account.setPhone(ownerRequest.getPhone());
        account.setCurrentAddress(ownerRequest.getCurrentAddress());
        account.setName(ownerRequest.getName());
        account.setImage(Constants.DEFAULT_AVATAR);
        Role role = new Role();
        role.setId(3L);
        account.setRole(role);
        account.setDob(ownerRequest.getDob());
        account.setHomeTown(ownerRequest.getHomeTown());
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
        if (StringUtils.isEmpty(residentRequest.getName())) {
            throw new RestApiException(StatusCode.NAME_EMPTY);
        }
        if (StringUtils.isEmpty(residentRequest.getDob())) {
            throw new RestApiException(StatusCode.DOB_EMPTY);
        }
        if (!StringUtils.isEmpty(residentRequest.getPhone())) {
            if (!isPhoneNumber(residentRequest.getPhone())) {
                throw new RestApiException(StatusCode.PHONE_NUMBER_NOT_RIGHT_FORMAT);
            }
        }
        if (!StringUtils.isEmpty(residentRequest.getEmail())) {
            if (!isEmail(residentRequest.getEmail())) {
                throw new RestApiException(StatusCode.EMAIL_NOT_RIGHT_FORMAT);
            }
        }
        if (!StringUtils.isEmpty(residentRequest.getIdentifyCard())) {
            if (!isIdentifyCard(residentRequest.getIdentifyCard())) {
                throw new RestApiException(StatusCode.IDENTIFY_CARD_EMPTY);
            }
        }
        Account account = accountDao.getAccountById(residentRequest.getAccountId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        account.setName(residentRequest.getName());
        account.setGender(residentRequest.getGender());
        account.setDob(residentRequest.getDob());
        account.setPhone(residentRequest.getPhone());
        account.setEmail(residentRequest.getEmail());
        account.setIdentifyCard(residentRequest.getIdentifyCard());
        accountDao.save(account);
    }

    @Override
    public void forwardPassword(String email) {
    }

    private Long addResident(ResidentRequest residentRequest) {
        Account account = new Account();
        account.setEnabled(true);
        account.setName(residentRequest.getName());
        account.setPassword(Constants.DEFAULT_PASSWORD);
        account.setIdentifyCard(residentRequest.getIdentifyCard());
        Role role = new Role();
        role.setId(5L);
        account.setRole(role);
        account.setEmail(residentRequest.getEmail());
        account.setPhone(residentRequest.getPhone());
        account.setGender(residentRequest.getGender());
        account.setDob(residentRequest.getDob());
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
        return response;
    }

}
