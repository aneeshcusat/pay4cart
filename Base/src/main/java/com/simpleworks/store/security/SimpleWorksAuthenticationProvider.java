package com.simpleworks.store.security;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.simpleworks.store.BaseSimpleWorksService;
import com.simpleworks.store.manager.SimpleWorksUserProfileManager;
import com.simpleworks.store.security.login.LoginResult;
import com.simpleworks.store.security.login.LoginResult.Status;

/**
 * The Class simpleWorksAuthenticationProvider.
 * 
 * @author Aneeshkumar
 * @version 1.0
 */
public class SimpleWorksAuthenticationProvider extends BaseSimpleWorksService implements AuthenticationProvider {

    @Resource
    SimpleWorksUserProfileManager userProfileManager;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SimpleWorksAuthenticationToken token = (SimpleWorksAuthenticationToken) authentication;
        LoginResult loginResult;
        loginResult = userProfileManager.login(token.getName(), token.getPassword());
        if (loginResult.getStatus() != Status.SUCCESS) {
            token.setAuthenticated(false);
        }
        getSimpleWorksUserSessionConfiguration().setLoginResult(loginResult);
        token.setLoginResult(loginResult);
        return token;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.authentication.AuthenticationProvider#supports
     * (java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(SimpleWorksAuthenticationToken.class);
    }
}
