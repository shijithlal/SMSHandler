package com.shijith.sms.auth;

import com.shijith.sms.bean.Account;
import com.shijith.sms.bean.AuthenticatedUser;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shijith.sms.Constants.AUTH_HEADER;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationFilter extends GenericFilterBean {

    @Autowired
    private IAuthValidator authValidator;

    public AuthenticationFilter() {

    }
    /**
     * Constructor for Unit test cases
     * @param authValidator
     */
    protected AuthenticationFilter(IAuthValidator authValidator) {
        this.authValidator = authValidator;
    }

    /**
     * Validate auth credentials and create a request level authenticated user object
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String authString = httpRequest.getHeader(AUTH_HEADER);

        try {
            Account account = authValidator.validate(authString);
            AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
            authenticatedUser.setUserId(account.getId());
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
            AuthenticatedUser.remove();
        }
    }

}
