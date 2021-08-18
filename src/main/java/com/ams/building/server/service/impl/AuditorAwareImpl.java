package com.ams.building.server.service.impl;

import com.ams.building.server.bean.Account;
import com.ams.building.server.response.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Account> {

    @Override
    public Optional<Account> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Account account = new Account();
            account.setId(currentUser.getId());
            return Optional.of(account);
        }
        return Optional.ofNullable(null);
    }

}
