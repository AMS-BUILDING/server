package com.ams.building.server.service;

import com.ams.building.server.service.impl.AuditorAwareImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.context.SecurityContextHolder;

@RunWith(PowerMockRunner.class)
public class AuditorAwareServiceTest {

    @InjectMocks
    AuditorAwareImpl auditorAware;

    @Test
    public void getCurrentAuditorTestSuccess() {
        SecurityContextHolder.getContext().getAuthentication();
        auditorAware.getCurrentAuditor();
    }

}
