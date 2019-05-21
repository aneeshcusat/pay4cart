package com.simpleworks.store.dashboard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.simpleworks.store.BaseSimpleWorksService;
import com.simpleworks.store.dashboard.manager.SimpleWorksDashboardManager;
import com.simpleworks.store.datatransferobject.OrderItem;
import com.simpleworks.store.datatransferobject.UserItem;
import com.simpleworks.store.security.SimpleWorksAuthenticationToken;
import com.simpleworks.store.security.hasher.SimpleWorksSecurityTokenManager;
import com.simpleworks.store.security.login.LoginResult.Status;
import com.simpleworks.store.security.login.UserSecurityContextBinder;

/**
 * The Class SimpleWorksUserController.
 */
@Controller
@SessionAttributes
public class SimpleWorksUserController extends BaseSimpleWorksService
{

    /** The simple works dashboard manager. */
    @Resource
    SimpleWorksDashboardManager simpleWorksDashboardManager;

    /** The authentication manager. */
    @Resource
    private AuthenticationManager authenticationManager;

    /** The user security context binder. */
    @Resource
    private UserSecurityContextBinder userSecurityContextBinder;

    
    /**
     * Login.
     *
     * @param path the path
     * @param model the model
     * @return the string
     */
    @RequestMapping("/{path}")
    public String login(@PathVariable("path") String path, Model model)
    {
        logDebug("Request path :" + path);
        return path;
    }
    
    /**
     * Login ajax.
     *
     * @param username the username
     * @param password the password
     * @return the string
     */
    @RequestMapping(value = "/loginAjax", method = RequestMethod.POST)
    @ResponseBody
    public String loginAjax(@RequestParam("email") String username, @RequestParam("password") String password)
    {

        SimpleWorksAuthenticationToken token = new SimpleWorksAuthenticationToken(username, password);
        SimpleWorksAuthenticationToken authentication =
            (SimpleWorksAuthenticationToken) authenticationManager.authenticate(token);
        if (authentication.getLoginResult().getStatus() == Status.SUCCESS) {
            userSecurityContextBinder.bindUserAuthentication(authentication);
            return "{\"status\": true}";
        } 

        return "{\"status\": false, \"error\": \"Bad Credentials\"}";
    }

    /**
     * Log out.
     *
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request)
    {
        userSecurityContextBinder.unbindUserAuthentication();
        request.getSession().invalidate();
        return "index";
    }

    /**
     * Creates the user.
     *
     * @param email the email
     * @param password the password
     * @param name the name
     * @return the string
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestParam("email") String email,
    		@RequestParam("password") String password, 
    		@RequestParam("name") String name)
    {
        try {
            simpleWorksDashboardManager.createUser(email, password, name);
            return loginAjax(email, password);
        } catch (Exception e) {
            return "{\"status\": false,\"errorCode\": \"Duplicate\"}";
        }
    }
    
    /**
     * Creates the order.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ModelAndView createOrder(HttpServletRequest request,
            HttpServletResponse response)
    {
    	
        OrderItem orderItem = simpleWorksDashboardManager.createOrder(request);
        return new ModelAndView("checkoutPage").addObject("order", orderItem);
    }
    
    /**
     * Checkout page.
     *
     * @return the model and view
     */
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView checkoutPage()
    {
    	OrderItem currentOrderItem = getSimpleWorksUserSessionConfiguration().getOrderItem();
        return new ModelAndView("checkoutPage").addObject("order", currentOrderItem);
    }
    
    /**
     * Order details page.
     *
     * @return the model and view
     */
    @RequestMapping(value = "/orderDetails", method = RequestMethod.GET)
    public ModelAndView orderDetailsPage()
    {
    	UserItem userItem = getSimpleWorksUserSessionConfiguration().getCurrentUser();
    	List<OrderItem> orderItemList = null;
    	if (userItem != null) {
    	 orderItemList = simpleWorksDashboardManager.orderDetailsPage(userItem);
    	}
        return new ModelAndView("orderDetailsPage").addObject("orders", orderItemList);
    }
    
    /**
     * Order confirmation.
     *
     * @param orderId the order id
     * @return the model and view
     */
    @RequestMapping(value = "/orderconfirmation/{orderId}", method = RequestMethod.GET)
    public ModelAndView orderConfirmation( @PathVariable(value = "orderId") Integer orderId)
    {
    	OrderItem currentOrderItem = (OrderItem) simpleWorksDashboardManager.getSimpleWorksDataAccessObjectManager().getItemById(orderId, OrderItem.class);
        return new ModelAndView("orderConfirmationPage").addObject("order", currentOrderItem);
    }
    
    /**
     * Payment page.
     *
     * @param paymentToken the payment token
     * @param request the request
     * @return the model and view
     */
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ModelAndView paymentPage(@RequestParam(name="paymentToken", defaultValue="") String paymentToken, HttpServletRequest request)
    {
    	OrderItem currentOrderItem = getSimpleWorksUserSessionConfiguration().getOrderItem();
    	String orderOwnerEmailId = null;
    	if (StringUtils.isNotBlank(paymentToken)) {
			String paymentTokenString = SimpleWorksSecurityTokenManager.decrypt(paymentToken, "aW4^06+iI=*MV^jl9AddsNxaR%4U2R^N");
			String[]tokens = paymentTokenString.split("#");
			if (tokens != null && tokens.length > 0) {
				String orderId = tokens[0];
				orderOwnerEmailId = tokens[1];
				UserItem currentUserItem = getSimpleWorksUserSessionConfiguration().getCurrentUser();
				if (currentUserItem != null && StringUtils.isNotBlank(orderOwnerEmailId) && orderOwnerEmailId.equalsIgnoreCase(currentUserItem.getUserId())) {
					 userSecurityContextBinder.unbindUserAuthentication();
				     request.getSession().invalidate();
				}
				
				currentOrderItem = (OrderItem) simpleWorksDashboardManager.getSimpleWorksDataAccessObjectManager().getItemById(Integer.parseInt(orderId), OrderItem.class);
			}
		} else {
			paymentToken = getPaymentToken();
			if (StringUtils.isNotBlank(paymentToken)) {
				paymentToken =  SimpleWorksSecurityTokenManager.encryptString(paymentToken, "aW4^06+iI=*MV^jl9AddsNxaR%4U2R^N");
			}
		}
    	try {
			paymentToken = URLEncoder.encode(paymentToken, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
        return new ModelAndView("paymentPage").addObject("order", currentOrderItem).addObject("orderOwnerEmailId", orderOwnerEmailId).addObject("paymentToken", paymentToken);
    }

	/**
	 * Gets the payment token.
	 *
	 * @return the payment token
	 */
	private String getPaymentToken() {
		String paymentToken = "";
		UserItem currentUserItem = getSimpleWorksUserSessionConfiguration().getCurrentUser();
		OrderItem orderItem = getSimpleWorksUserSessionConfiguration().getOrderItem();
		if (orderItem != null) {
			paymentToken =  "" + orderItem.getOrderId();
			 if (currentUserItem != null){
				 paymentToken += "#" + currentUserItem.getUserId();
			 }
		}
		return paymentToken;
	}
    
    /**
     * Send payment link.
     *
     * @param paymentToken the payment token
     * @param email1 the email 1
     * @param email2 the email 2
     * @param email3 the email 3
     * @param orderOwnerName the order owner name
     * @return the string
     */
    @RequestMapping(value = "/sendPaymentLink", method = RequestMethod.POST)
    @ResponseBody
    public String sendPaymentLink(@RequestParam(name="paymentToken", defaultValue="") String paymentToken
    		, @RequestParam(name="email1", defaultValue="") String email1
    		, @RequestParam(name="email2", defaultValue="") String email2
    		, @RequestParam(name="email3", defaultValue="") String email3
    		, @RequestParam(name="orderOwnerName", defaultValue="") String orderOwnerName)
    {
    	List<String> emailIdList = new ArrayList<>();
    	if (StringUtils.isNotBlank(email1)) {
    		emailIdList.add(email1);
    	}
    	
    	if (StringUtils.isNotBlank(email2)) {
    		emailIdList.add(email2);
    	}
    	if (StringUtils.isNotBlank(email3)) {
    		emailIdList.add(email3);
    	}
    	
    	simpleWorksDashboardManager.sendEmails(emailIdList, paymentToken,orderOwnerName);
		simpleWorksDashboardManager.addPayment("PBO", paymentToken);
		getSimpleWorksUserSessionConfiguration().setOrderItem(null);
		
        return "{\"status\": true}";
    }
    
    /**
     * Adds the billing address.
     *
     * @param fullName the full name
     * @param landMark the land mark
     * @param mobileNumber the mobile number
     * @param city the city
     * @param type the type
     * @return the string
     */
    @RequestMapping(value = "/addBillingAddress", method = RequestMethod.POST)
    @ResponseBody
    public String addBillingAddress(
    		@RequestParam("fullName") String fullName, 
    		@RequestParam("landMark") String landMark, 
    		@RequestParam("mobileNumber") String mobileNumber,
    		@RequestParam("city") String city,
    		@RequestParam("type") String type)
    {
        OrderItem orderItem = simpleWorksDashboardManager.addBillingAddress(fullName, landMark, mobileNumber, city, type);
        return "{\"status\": true}";
    }
    
    /**
     * Payment.
     *
     * @param paymentType the payment type
     * @param paymentToken the payment token
     * @return the string
     */
    @RequestMapping(value = "/addPayment", method = RequestMethod.POST)
    @ResponseBody
    public String payment(@RequestParam("paymentType") String paymentType,
    		@RequestParam(name="paymentToken", defaultValue="") String paymentToken)
    {
       OrderItem orderItem = simpleWorksDashboardManager.addPayment(paymentType, paymentToken);
       return "{\"status\": true}";
    }
}
