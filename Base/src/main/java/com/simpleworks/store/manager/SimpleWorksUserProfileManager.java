package com.simpleworks.store.manager;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.simpleworks.store.contants.HQLStrings;
import com.simpleworks.store.datatransferobject.UserItem;
import com.simpleworks.store.employees.bean.UserDetails;
import com.simpleworks.store.security.SimpleWorksAuthenticationToken;
import com.simpleworks.store.security.hasher.SimpleWorksSecurityTokenManager;
import com.simpleworks.store.security.hasher.generator.PasswordTokenGenerator;
import com.simpleworks.store.security.login.LoginResult;
import com.simpleworks.store.security.login.LoginResult.Status;

/**
 * The Class UserProfileManager.
 * 
 * @author Aneeshkumar
 * @version 1.0
 */
@Component
public class SimpleWorksUserProfileManager extends BaseSimpleWorksManager
{

    @Resource
    PasswordTokenGenerator passwordTokenGenerator;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Login.
     * 
     * @param name the name
     * @param password the password
     * @return the login result
     */
    public LoginResult login(String name, String password)
    {
        LoginResult loginResult = new LoginResult();
        UserItem userItem = getUserItem(name);
        if (userItem != null) {
            String encryptPassword = SimpleWorksSecurityTokenManager.encryptString(password, userItem.getHashkey());
            if (userItem.getPassword().equals(encryptPassword)) {
                loginResult.setStatus(Status.SUCCESS);
                loginResult.setUserItem(userItem);
                return loginResult;
            }
        }
        loginResult.setStatus(Status.FAILED);
        return loginResult;

    }

    /**
     * Gets the user token.
     * 
     * @return the user token
     */
    public Authentication getUserToken()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Gets the login result.
     * 
     * @return the login result
     */
    public LoginResult getLoginResult()
    {
        Authentication authentication = getUserToken();
        if (authentication instanceof SimpleWorksAuthenticationToken) {
            return ((SimpleWorksAuthenticationToken) authentication).getLoginResult();
        }
        return null;
    }

    public void createUserItem(UserDetails userDetails)
    {
        
    }
    
    public void createUserItem(String email, String password, String name) {
    	String hashKey = passwordTokenGenerator.generate(32);
        UserItem userItem = new UserItem();
        String encryptedPassword = SimpleWorksSecurityTokenManager.encryptString(password, hashKey);
        userItem.setHashkey(hashKey);
        userItem.setPassword(encryptedPassword);
        userItem.setUserId(email);
        userItem.setFirstName(name);
        getSimpleWorksDataAccessObjectManager().saveOrUpdateItem(userItem);
	}

    public void updateUserItem(UserDetails userDetails)
    {
        UserItem userItem = getUserItem(userDetails.getEmail());
        if (userItem != null) {
            saveUserItem(userDetails, userItem);
        }
    }

    private void saveUserItem(UserDetails userDetails, UserItem userItem)
    {
        userItem.setUserId(userDetails.getEmail());
        userItem.setFirstName(userDetails.getFirstName());
        getSimpleWorksDataAccessObjectManager().saveOrUpdateItem(userItem);
    }

    public UserItem getUserItem(String userId)
    {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("userId", userId);
        List<?> userItemList =
            getSimpleWorksDataAccessObjectManager().executeAllGroupQuery(
                HQLStrings.getString("SimpleWorksQueryStringsusersByUserId"), dataMap);
        if (!userItemList.isEmpty()) {
            UserItem userItem = (UserItem) userItemList.get(0);
            return  userItem;
        }
        return null;
    }

	
}
