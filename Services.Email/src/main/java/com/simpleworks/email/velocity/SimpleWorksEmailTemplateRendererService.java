package com.simpleworks.email.velocity;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.simpleworks.email.util.EmailMessage;
import com.simpleworks.email.util.SimpleWorksTemplateEmailInfo;
import com.simpleworks.store.BaseSimpleWorksService;

/**
 * The Class simpleWorksEmailTemplateRendererService.
 * 
 * @author Aneeshkumar
 * @version 1.0
 */
public class SimpleWorksEmailTemplateRendererService extends BaseSimpleWorksService {

	/** The Constant VELOCITY_TEMPLATES_EMAIL. */
	private static final String VELOCITY_TEMPLATES_EMAIL = "/velocity/templates/";

	/** The Constant TEMPLATE_NAME_PLACEHOLDER. */
	private static final String TEMPLATE_NAME_PLACEHOLDER = "@templateName@";

	/** The html template. */
	private final String htmlTemplate = VELOCITY_TEMPLATES_EMAIL + "email/@templateName@.vm";

	/** The text template. */
	private final String textTemplate = VELOCITY_TEMPLATES_EMAIL + "plaintext_email/@templateName@-pt.vm";

	/** The delivery interface velocity service. */
	@Autowired
	private SimpleWorksVelocityService simpleWorksVelocityService;

	/**
	 * Creates the message.
	 *
	 * @param emailInfo
	 *            the email info
	 * @param emailsToList
	 *            the emails to
	 * @return the mime message preparator
	 */
	public MimeMessagePreparator createMessage(SimpleWorksTemplateEmailInfo emailInfo, String... emailsToList) {

		String[] emailsTo = emailsToList;
		try {

			EmailMessage emailMessage = renderEmailMessage(emailInfo);
			if (emailsTo == null || emailsTo.length == 0) {
				emailsTo = new String[1];
				emailsTo[0] = emailInfo.getMailTo();
			}
			return mapPropertiesToMessage(emailInfo.getMailFrom(), emailMessage, emailInfo.getMailCc(),
					emailInfo.getMailBcc(), emailsTo);
		} catch (Exception e) {
			logError("unable to render the template " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Render email message.
	 *
	 * @param emailInfo
	 *            the email info
	 * @return the email message
	 */
	public EmailMessage renderEmailMessage(SimpleWorksTemplateEmailInfo emailInfo) {
		try {
			Map<String, Object> data = emailInfo.getTemplateParameters();
			String templateURL = emailInfo.getVelocityTemplateName();
			String body = "";
			try {
				body = simpleWorksVelocityService
						.evaluateTemplate(getTextTemplate().replaceFirst(TEMPLATE_NAME_PLACEHOLDER, templateURL), data);
			} catch (ResourceNotFoundException re) {
				logError("Unable to load template");
			}

			String htmlBody = simpleWorksVelocityService
					.evaluateTemplate(getHtmlTemplate().replaceFirst(TEMPLATE_NAME_PLACEHOLDER, templateURL), data);
			String subject = emailInfo.getMailSubject();
			return new EmailMessage(simpleWorksVelocityService.evaluateString(subject, data),
					simpleWorksVelocityService.evaluateString(body, data),
					simpleWorksVelocityService.evaluateString(htmlBody, data));
		} catch (Exception e) {
			logError("unable to render velocity template " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Map properties to message.
	 *
	 * @param messageFrom
	 *            the message from
	 * @param emailMessage
	 *            the email message
	 * @param emailsCC
	 *            the emails CC
	 * @param emailsBCC
	 *            the emails BCC
	 * @param emailsTo
	 *            the emails to
	 * @return the mime message preparator
	 * @throws MessagingException
	 *             the messaging exception
	 */
	protected MimeMessagePreparator mapPropertiesToMessage(final String messageFrom, final EmailMessage emailMessage,
			final String emailsCC, final String emailsBCC, final String... emailsTo) throws MessagingException {

		return new MimeMessagePreparator() {
			@Override
			public void prepare(final MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				messageHelper.setFrom(new InternetAddress(messageFrom));
				for (String emailTo : emailsTo) {
					messageHelper.addTo(emailTo);
				}
				messageHelper.setText(emailMessage.getBody(), emailMessage.getHtmlBody());
				messageHelper.setSubject(emailMessage.getSubject());
				if (StringUtils.isNotBlank(emailsCC)) {
					messageHelper.setCc(emailsCC);
				}
				if (StringUtils.isNotBlank(emailsBCC)) {
					messageHelper.setBcc(emailsBCC);
				}
			}
		};
	}

	/**
	 * Gets the html template.
	 *
	 * @return the html template
	 */
	public String getHtmlTemplate() {
		return htmlTemplate;
	}

	/**
	 * Gets the text template.
	 *
	 * @return the text template
	 */
	public String getTextTemplate() {
		return textTemplate;
	}
}
