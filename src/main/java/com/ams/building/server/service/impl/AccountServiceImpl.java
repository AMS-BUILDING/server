package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.bean.Role;
import com.ams.building.server.dao.AccountDAO;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountDAO accountDao;

    @Override
    public void add(AccountResponse accountDTO) {
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        account.setEnabled(accountDTO.getEnabled());
        account.setPhone(accountDTO.getPhone());
        account.setName(accountDTO.getName());
        account.setRole(new Role(accountDTO.getRoleId()));
        account.setDob(accountDTO.getDob());
        account.setHomeTown(accountDTO.getHomeTown());
        account.setImage(accountDTO.getImage());
        accountDao.save(account);
        accountDTO.setId(account.getId());
    }

    @Override
    public void update(AccountResponse accountDTO) {
        Account account = accountDao.getAccountById(accountDTO.getId());
        if (account != null) {
            account.setEmail(accountDTO.getEmail());
            account.setPassword(accountDTO.getPassword());
            account.setEnabled(accountDTO.getEnabled());
            account.setPhone(accountDTO.getPhone());
            account.setName(accountDTO.getName());
            account.setRole(new Role(accountDTO.getRoleId()));
            account.setDob(accountDTO.getDob());
            account.setHomeTown(accountDTO.getHomeTown());
            account.setIdentityCard(accountDTO.getIdentityCard());

            if (account.getImage() != null) {
                String image = account.getImage();
                FileStore.deleteFile(image);
                account.setImage(accountDTO.getImage());
            }
            accountDao.save(account);
        }
    }

    @Override
    public void updateProfile(AccountResponse accountDTO) {
        Account account = accountDao.getAccountById(accountDTO.getId());
        if (account != null) {
            account.setName(accountDTO.getName());
            account.setPhone(accountDTO.getPhone());
        }
    }

    @Override
    public void delete(Long id) {
        Account account = accountDao.getAccountById(id);
        if (account != null) {
            accountDao.deleteById(id);
        }
    }

    @Override
    public List<AccountResponse> find() {
        List<Account> accountList = accountDao.findAll();
        List<AccountResponse> accountDTOList = new ArrayList<>();
        accountList.forEach(account -> {
            accountDTOList.add(convert(account));
        });
        return accountDTOList;
    }

    @Override
    public AccountResponse getById(Long id) {
        Account account = accountDao.getAccountById(id);
        if (account != null) {
            return convert(account);
        }
        return null;
    }

    @Override
    public AccountResponse getByEmail(String email) {
        Account account = accountDao.getAccountByEmail(email);

        if (account != null) {
            return convert(account);
        }
        return null;
    }

    @Override
    public Long count() {
        return accountDao.count();
    }

    @Override
    public Long counTotal() {
        return accountDao.count();
    }

    @Override
    public void changeAccountLock(long id) {
        Account account = accountDao.getAccountById(id);
        if (account != null) {
            account.setEnabled(!account.getEnabled());
            accountDao.save(account);
        }
    }

    private AccountResponse convert(Account account) {
        AccountResponse accountDTO = new AccountResponse();
        accountDTO.setId(account.getId());
        accountDTO.setEnabled(account.getEnabled());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setRoleId(account.getRole().getId());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setImage(account.getImage());
        accountDTO.setDob(account.getDob());
        accountDTO.setHomeTown(account.getHomeTown());
        accountDTO.setIdentityCard(account.getIdentityCard());
        return accountDTO;

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountDao.getAccountByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("not found");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
        UserPrincipal accountDTO = new UserPrincipal(account.getEmail(), account.getPassword(), account.getEnabled(), true, true,
                true, authorities);
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setRoleId(account.getRole().getId());
        return accountDTO;
    }


}
