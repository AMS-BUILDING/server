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
import org.apache.log4j.Logger;
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

@Transactional
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDAO accountDao;

    @Override
    public void add(LoginResponse loginResponse) {
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
    public void updateProfile(LoginResponse loginResponse) {
        if (StringUtils.isEmpty(loginResponse.getId())) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
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
        List<LoginResponse> loginRespons = new ArrayList<>();
        accountList.forEach(account -> {
            loginRespons.add(convertToAccountResponse(account));
        });
        return loginRespons;
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
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        boolean isEmail = isEmail(email);
        if (!isEmail) {
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
    public Long addApartmentOwner(ApartmentOwnerRequest ownerRequest) {
        Account account = new Account();
        account.setEmail(ownerRequest.getEmail());
        account.setPassword(Constants.AccountPassword.DEFAULT_PASSWORD);
        account.setGender(ownerRequest.getGender());
        account.setIdentifyCard(ownerRequest.getIdentifyCard());
        account.setEnabled(true);
        account.setPhone(ownerRequest.getPhone());
        account.setCurrentAddress(ownerRequest.getCurrentAddress());
        account.setName(ownerRequest.getName());
        account.setRole(new Role(3L));
        account.setDob(ownerRequest.getDob());
        account.setHomeTown(ownerRequest.getHomeTown());
        accountDao.save(account);
        return account.getId();
    }

    @Override
    public void disableAccount(Long id) {
        Account account = accountDao.getAccountById(id);
        account.setEnabled(false);
        accountDao.save(account);
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
        Account account = accountDao.getAccountById(residentRequest.getAccountId());
        if (account != null) {
            account.setName(residentRequest.getName());
            account.setGender(residentRequest.getGender());
            account.setDob(residentRequest.getDob());
            account.setPhone(residentRequest.getPhone());
            account.setEmail(residentRequest.getEmail());
            account.setIdentifyCard(residentRequest.getIdentifyCard());
        }
        accountDao.save(account);
    }

    private Long addResident(ResidentRequest residentRequest) {
        Account account = new Account();
        account.setEnabled(true);
        account.setName(residentRequest.getName());
        account.setPassword(Constants.AccountPassword.DEFAULT_PASSWORD);
        account.setIdentifyCard(residentRequest.getIdentifyCard());
        account.setRole(new Role(5L));
        account.setEmail(residentRequest.getEmail());
        account.setPhone(residentRequest.getPhone());
        account.setGender(residentRequest.getGender());
        account.setDob(residentRequest.getDob());
        Position position = new Position();
        position.setId(residentRequest.getPositionId());
        account.setPosition(position);
        accountDao.save(account);
        return account.getId();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
