package com.simpleworks.store.dashboard.manager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.simpleworks.email.SimpleWorksEmailSender;
import com.simpleworks.email.util.SimpleWorksTemplateEmailInfo;
import com.simpleworks.store.contants.HQLStrings;
import com.simpleworks.store.datatransferobject.AddressItem;
import com.simpleworks.store.datatransferobject.OrderItem;
import com.simpleworks.store.datatransferobject.PaymentItem;
import com.simpleworks.store.datatransferobject.SkuItem;
import com.simpleworks.store.datatransferobject.UserItem;
import com.simpleworks.store.employees.bean.UserDetails;
import com.simpleworks.store.manager.BaseSimpleWorksManager;
import com.simpleworks.store.manager.SimpleWorksUserProfileManager;
import com.simpleworks.store.security.hasher.SimpleWorksSecurityTokenManager;

/**
 * The Class SimpleWorksDashboardManager.
 */
@Component
public class SimpleWorksDashboardManager extends BaseSimpleWorksManager
{

    /** The user profile manager. */
    @Resource
    SimpleWorksUserProfileManager userProfileManager;
    
    /** The email sender. */
    @Resource
    SimpleWorksEmailSender emailSender;

	/**
	 * Creates the user.
	 *
	 * @param email the email
	 * @param password the password
	 * @param name the name
	 */
	public void createUser(String email, String password, String name) {
        userProfileManager.createUserItem(email, password, name);
        
        SimpleWorksTemplateEmailInfo emailInfo = new SimpleWorksTemplateEmailInfo();
		Map dataMap = new HashMap<>();
		dataMap.put("firstName", name);
        String[] emailIdList = new String[]{email};
		sendMail("You have been registed in EShoppy", "userRegistraion", dataMap, emailIdList);
		
	}

    /**
     * Gets the user item.
     *
     * @param emailId the email id
     * @return the user item
     */
    public UserItem getUserItem(String emailId)
    {
        return userProfileManager.getUserItem(emailId);
    }

	/**
	 * Creates the order.
	 *
	 * @param request the request
	 * @return the order item
	 */
	public OrderItem createOrder(HttpServletRequest request) 
	{
		OrderItem currentOrderItem = getSimpleWorksUserSessionConfiguration().getOrderItem();
		if (currentOrderItem == null) {
			currentOrderItem = new OrderItem();
			currentOrderItem.setStatus("INCOMPLETE");
			currentOrderItem.setUserId(getSimpleWorksUserSessionConfiguration().getCurrentUser().getId());
		} 
		
		Set<SkuItem> skuItems = currentOrderItem.getSkuItems();
		if (skuItems == null) {
			skuItems = new HashSet<>();
			currentOrderItem.setSkuItems(skuItems);
		} else {
			try {
				for (SkuItem skuItem : skuItems) {
					 getSimpleWorksDataAccessObjectManager().deleteItem(skuItem);
				}
			}catch(Exception e){
				
			}
		}
		
		skuItems.clear();
		int index = 1;
		boolean exit = false;
		Double orderSubTotal = 0d;
		do {
			String itemNumber = request.getParameter("item_number_" + index);
			String itemName = request.getParameter("item_name_" + index);
			String amount = request.getParameter("amount_" + index);
			String discountAmount = request.getParameter("discount_amount_" + index);
			String quantity = request.getParameter("quantity_" + index);
			logDebug("itemNumber : " + itemNumber );
			if (StringUtils.isBlank(itemNumber)){
				exit = true;
			}
			if (!exit){
				SkuItem skuItem = new SkuItem();
				Double amountDouble = amount != null ? Double.parseDouble(amount) : 0d;
				int quantityInt =quantity != null ? Integer.parseInt(quantity) : 0;
				Double discountDouble = discountAmount != null ? Double.parseDouble(discountAmount) : 0d;
				skuItem.setAmount(amountDouble);
				skuItem.setItemName(itemName);
				skuItem.setItemNumber(itemNumber);
				skuItem.setQuantity(quantityInt);
				skuItem.setDiscountAmount(discountDouble);
				skuItem.setOrderItem(currentOrderItem);
				currentOrderItem.getSkuItems().add(skuItem);
				orderSubTotal+=(amountDouble*quantityInt) - discountDouble;
			}
			index++;
		} while(!exit);
		currentOrderItem.setSubTotal(orderSubTotal);
		OrderItem savedOrderItem = (OrderItem) getSimpleWorksDataAccessObjectManager().saveOrUpdateItem(currentOrderItem);
		getSimpleWorksUserSessionConfiguration().setOrderItem(savedOrderItem);
		return savedOrderItem;
	}

	/**
	 * Adds the payment.
	 *
	 * @param paymentType the payment type
	 * @param paymentToken the payment token
	 * @return the order item
	 */
	public OrderItem addPayment(String paymentType, String paymentToken) {
		OrderItem currentOrderItem = getSimpleWorksUserSessionConfiguration().getOrderItem();
		
		try {
			paymentToken = java.net.URLDecoder.decode(paymentToken, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		if (StringUtils.isNotBlank(paymentToken)) {
			
			String paymentTokenString = SimpleWorksSecurityTokenManager.decrypt(paymentToken, "aW4^06+iI=*MV^jl9AddsNxaR%4U2R^N");
			String[]tokens = paymentTokenString.split("#");
			if (tokens != null && tokens.length > 0) {
				String orderId = tokens[0];
				currentOrderItem = (OrderItem) getSimpleWorksDataAccessObjectManager().getItemById(Integer.parseInt(orderId), OrderItem.class);
			} else {
				currentOrderItem = null;
			}
		}
		
		if (currentOrderItem != null) {
			PaymentItem paymentItem = currentOrderItem.getPaymentItem();
			if (paymentItem == null) {
				paymentItem = new PaymentItem();
			}
			paymentItem.setAmount(currentOrderItem.getSubTotal());
			
			Map dataMap = new HashMap();
			UserItem orderUserItem = (UserItem) getSimpleWorksDataAccessObjectManager().getItemById(currentOrderItem.getUserId(), UserItem.class);
			String emailId = orderUserItem.getUserId();
			dataMap.put("firstName", orderUserItem.getFirstName());
			if ("PBO".equalsIgnoreCase(paymentType)) {
				paymentItem.setStatus("PENDING_PBO");
				currentOrderItem.setStatus("PENDING_PAYMENT");
				dataMap.put("paymentStatus", "and pending for payment");
			} else {
				paymentItem.setStatus("DONE");
				currentOrderItem.setStatus("SUBMITED");
				dataMap.put("paymentStatus", "successfully");
				paymentItem.setPaidBy(getSimpleWorksUserSessionConfiguration().getCurrentUser().getId());
			}
			paymentItem = (PaymentItem) getSimpleWorksDataAccessObjectManager().saveOrUpdateItem(paymentItem);
			currentOrderItem.setPaymentItem(paymentItem);
			currentOrderItem = (OrderItem) getSimpleWorksDataAccessObjectManager().saveOrUpdateItem(currentOrderItem);
			String[] emailIdList = new String[]{emailId};
			sendMail("Your order has been placed", "orderConfirmation", dataMap, emailIdList);
			
		}
		return currentOrderItem;
	}
	
	/**
	 * Send mail.
	 *
	 * @param subject the subject
	 * @param templateName the template name
	 * @param dataMap the data map
	 * @param toEmail the to email
	 */
	@Async
	public void sendMail(String subject, String templateName, Map dataMap, String[] toEmail){
		SimpleWorksTemplateEmailInfo emailInfo = new SimpleWorksTemplateEmailInfo();
		emailInfo.setMailFrom("eshoppysupport1443@gmail.com");
		emailInfo.setMailSubject(subject);
        emailInfo.setVelocityTemplateName(templateName);
        emailInfo.setTemplateParameters(dataMap);
        
    	emailSender.sendEmail(emailInfo, toEmail);
	}

	/**
	 * Adds the billing address.
	 *
	 * @param fullName the full name
	 * @param landMark the land mark
	 * @param mobileNumber the mobile number
	 * @param city the city
	 * @param type the type
	 * @return the order item
	 */
	public OrderItem addBillingAddress(String fullName, String landMark,
			String mobileNumber, String city, String type) {
		
		OrderItem currentOrderItem = getSimpleWorksUserSessionConfiguration().getOrderItem();

		if (currentOrderItem != null) {
			AddressItem addressItem = currentOrderItem.getAddressItem();
			if (addressItem == null) {
				addressItem = new AddressItem();
			}
			addressItem.setCity(city);
			addressItem.setFullName(fullName);
			addressItem.setLandMark(landMark);
			addressItem.setMobileNumber(mobileNumber);
			addressItem.setType(type);
			addressItem = (AddressItem) getSimpleWorksDataAccessObjectManager().saveOrUpdateItem(addressItem);
			
			currentOrderItem.setAddressItem(addressItem);
			currentOrderItem = (OrderItem) getSimpleWorksDataAccessObjectManager().saveOrUpdateItem(currentOrderItem);
			getSimpleWorksUserSessionConfiguration().setOrderItem(currentOrderItem);
		}
		return currentOrderItem;
	}

	/**
	 * Send emails.
	 *
	 * @param emailIdList the email id list
	 * @param paymentToken the payment token
	 * @param orderOwnerName the order owner name
	 */
	@Async
	public void sendEmails(List<String> emailIdList, String paymentToken, String orderOwnerName) {
		SimpleWorksTemplateEmailInfo emailInfo = new SimpleWorksTemplateEmailInfo();
		Map dataMap = new HashMap<>();
		dataMap.put("paymentToken", paymentToken);
		dataMap.put("orderOwnerName", orderOwnerName);
		emailInfo.setMailFrom("eshoppysupport1443@gmail.com");
		emailInfo.setMailSubject("Payment Request from E Shoppy for the order placed by " + orderOwnerName);
        emailInfo.setVelocityTemplateName("paymentLink");
        emailInfo.setTemplateParameters(dataMap);
        
		emailSender.sendEmail(emailInfo, (String[]) emailIdList.toArray(new String[0]));
	}

	/**
	 * Order details page.
	 *
	 * @param userItem the user item
	 * @return the list
	 */
	public List<OrderItem> orderDetailsPage(UserItem userItem) {
		 Map<String, Object> dataMap = new HashMap<String, Object>();
	        dataMap.put("userId", userItem.getId());
	        List<?> orderItemList =
	            getSimpleWorksDataAccessObjectManager().executeAllGroupQuery(
	                HQLStrings.getString("getAllUserOrders"), dataMap);
		return (List<OrderItem>) orderItemList;
	}


}
