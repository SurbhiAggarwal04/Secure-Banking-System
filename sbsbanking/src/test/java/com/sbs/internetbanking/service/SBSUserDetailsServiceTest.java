/*
package com.sbs.internetbanking.service;

import com.sbs.internetbanking.enums.AccountStatus;
import com.sbs.internetbanking.persistence.UserManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SBSUserDetailsServiceTest {

    @Mock
    private UserManager userManager;
    @InjectMocks
    private SBSUserDetailsService service;

    @Test(expected = UsernameNotFoundException.class)
    public void usernameNotFound() {
        when(userManager.getUserByUserName(anyString()))
                .thenThrow(new UsernameNotFoundException("Username not found"));

        service.loadUserByUsername(anyString());
    }

    @Test
    public void userAccountLocked() {
        com.sbs.internetbanking.model.User testUser = new com.sbs.internetbanking.model.User();
        testUser.setAccountStatus(AccountStatus.LOCKED.name());
        testUser.setUsername("Test Username");
        testUser.setPassword("Test Password");
        when(userManager.getUserByUserName(anyString()))
                .thenReturn(testUser);

        UserDetails springUser = service.loadUserByUsername(anyString());

        assertFalse(springUser.isAccountNonLocked());
    }
}
*/
