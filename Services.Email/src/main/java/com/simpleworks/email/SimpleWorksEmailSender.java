package com.simpleworks.email;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import com.simpleworks.email.util.SimpleWorksTemplateEmailInfo;
import com.simpleworks.email.velocity.SimpleWorksEmailTemplateRendererService;
import com.simpleworks.store.BaseSimpleWorksService;

public class SimpleWorksEmailSender extends BaseSimpleWorksService
{

    /** The java mail sender. */
    @Autowired
    private JavaMailSender javaMailSender;

    /** The delivery interface email template renderer service. */
    @Autowired
    private SimpleWorksEmailTemplateRendererService simpleWorksEmailTemplateRendererService;

    /**
     * Send email.
     * 
     * @param emailInfo the email info
     */
    public void sendEmail(SimpleWorksTemplateEmailInfo emailInfo, String... emailsToList)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        emailInfo.setMimeMessage(mimeMessage);
        logDebug("emailsToList" + emailsToList);
        javaMailSender.send(simpleWorksEmailTemplateRendererService.createMessage(emailInfo, emailsToList));
    }

    public void sendTextMessage(String from, String to, String subject, String messageBody)
    {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);
            javaMailSender.send(message);
        } catch (Exception e) {
            logError(e.getMessage());
        }

    }
}
