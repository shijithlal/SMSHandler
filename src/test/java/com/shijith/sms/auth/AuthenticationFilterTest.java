package com.shijith.sms.auth;

import com.shijith.sms.bean.Account;
import com.shijith.sms.bean.AuthenticatedUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;


public class AuthenticationFilterTest {

    @Test
    public void testDoFilter() {

        try {
            Account account = new Account();
            account.setId(1);
            account.setUsername("test");
            account.setAuth_id("AuthId");

            IAuthValidator authValidator = mock(IAuthValidator.class);
            when(authValidator.validate(any(String.class))).thenReturn(account);
            AuthenticationFilter filter = new AuthenticationFilter(authValidator);

            ServletRequest servletRequest = mock(HttpServletRequest.class);
            ServletResponse servletResponse = mock(HttpServletResponse.class);
            FilterChain filterChain = mock(FilterChain.class);

            filter.doFilter(servletRequest, servletResponse, filterChain);

            AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();

            Assert.assertEquals(new Integer(1),authenticatedUser.getUserId());

        }
        catch(Exception e) {
            Assert.fail();
        }

    }

    @Test
    public void testDoFilterAuthFailed() {

        try {
            Account account = new Account();
            account.setId(1);
            account.setUsername("test");
            account.setAuth_id("AuthId");

            IAuthValidator authValidator = mock(IAuthValidator.class);
            when(authValidator.validate(any(String.class))).thenThrow(new AuthException());
            AuthenticationFilter filter = new AuthenticationFilter(authValidator);

            ServletRequest servletRequest = mock(HttpServletRequest.class);
            ServletResponse servletResponse = mock(HttpServletResponse.class);
            FilterChain filterChain = mock(FilterChain.class);

            filter.doFilter(servletRequest, servletResponse, filterChain);

            AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
            Assert.assertNull(authenticatedUser.getUserId());
        }
        catch(Exception e) {
            Assert.fail();
        }
    }

}