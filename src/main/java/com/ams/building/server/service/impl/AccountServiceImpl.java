package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Role;
import com.ams.building.server.constant.StatusCode;
import com.ams.building.server.dao.AccountDAO;
import com.ams.building.server.exception.RestApiException;
import com.ams.building.server.response.AccountResponse;
import com.ams.building.server.response.UserPrincipal;
import com.ams.building.server.service.AccountService;
import com.ams.building.server.utils.FileStore;
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

    @Autowired
    private AccountDAO accountDao;

    @Override
    public void add(AccountResponse accountResponse) {
        Account account = new Account();
        account.setEmail(accountResponse.getEmail());
        account.setPassword(accountResponse.getPassword());
        account.setEnabled(accountResponse.getEnabled());
        account.setPhone(accountResponse.getPhone());
        account.setCurrentAddress(accountResponse.getCurrentAddress());
        account.setName(accountResponse.getName());
        Role role = Role.builder().id(accountResponse.getId()).build();
        account.setRole(role);
        account.setDob(accountResponse.getDob());
        account.setHomeTown(accountResponse.getHomeTown());
        account.setImage(accountResponse.getImage());
        accountDao.save(account);
        accountResponse.setId(account.getId());
    }

    @Override
    public void update(AccountResponse accountResponse) {
        Account account = accountDao.getAccountById(accountResponse.getId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        account.setEmail(accountResponse.getEmail());
        account.setPassword(accountResponse.getPassword());
        account.setEnabled(accountResponse.getEnabled());
        account.setPhone(accountResponse.getPhone());
        account.setCurrentAddress(accountResponse.getCurrentAddress());
        account.setName(accountResponse.getName());
        Role role = Role.builder().id(accountResponse.getId()).build();
        account.setRole(role);
        account.setDob(accountResponse.getDob());
        account.setHomeTown(accountResponse.getHomeTown());
        account.setIdentifyCard(accountResponse.getIdentifyCard());
        if (account.getImage() != null) {
            String image = account.getImage();
            FileStore.deleteFile(image);
            account.setImage(accountResponse.getImage());
        }
        accountDao.save(account);
    }

    @Override
    public void updateProfile(AccountResponse accountResponse) {
        if (StringUtils.isEmpty(accountResponse.getId())) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(accountResponse.getId());
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        account.setName(accountResponse.getName());
        account.setPhone(accountResponse.getPhone());
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
    public List<AccountResponse> find() {
        List<Account> accountList = accountDao.findAll();
        List<AccountResponse> accountResponses = new ArrayList<>();
        accountList.forEach(account -> {
            accountResponses.add(convertToAccountResponse(account));
        });
        return accountResponses;
    }

    @Override
    public AccountResponse getById(Long id) {
        if (StringUtils.isEmpty(id)) {
            throw new RestApiException(StatusCode.DATA_EMPTY);
        }
        Account account = accountDao.getAccountById(id);
        if (Objects.isNull(account)) {
            throw new RestApiException(StatusCode.ACCOUNT_NOT_EXIST);
        }
        AccountResponse response = convertToAccountResponse(account);
        return response;
    }

    @Override
    public AccountResponse getByEmail(String email) {
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
        AccountResponse response = convertToAccountResponse(account);
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

    private AccountResponse convertToAccountResponse(Account account) {
        AccountResponse response = AccountResponse.builder().build();
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
