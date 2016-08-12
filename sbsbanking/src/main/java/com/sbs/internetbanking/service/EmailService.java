package com.sbs.internetbanking.service;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.sbs.internetbanking.model.User;

public class EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	private final static Properties properties = new Properties();
	private final String bankEmailAddress;
	private final String bankEmailPaswd;

	public EmailService(String host, String auth, String debug, String port, String bankEmailAddress, String bankEmailPaswd) {
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.debug", debug);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		properties.put("mail.smtp.starttls.enable", "true");
		this.bankEmailAddress = bankEmailAddress;
		this.bankEmailPaswd = bankEmailPaswd;

	}

	/**
	 * 
	 * @param mailid
	 * @param otp
	 */
	public void sendMail(String mailID, String subject, String bodyText) throws MessagingException {
		try {
			Session mailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(bankEmailAddress, bankEmailPaswd);
				}
			});
			mailSession.setDebug(true);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(bankEmailAddress));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailID));
			msg.setSentDate(new Date());
			msg.setSubject(subject);
			String txt = String.valueOf(bodyText);
			msg.setContent(bodyText, "text/html");
			// msg.setText(txt);
			Transport.send(msg);
		} catch (Exception exp) {
			logger.debug("An Error Occurred", exp);
		}
	}

	public void sendPasswordResetTokenMail(String contextPath, Locale locale, String token, User user) throws MessagingException {
		String url = contextPath + "/changePassword?username=" + user.getUsername() + "&token=" + token;
		String bodyText = "Copy and paste the following link in your browser to reset the password\n" + url;
		sendMail(user.getUserProfileInfo().getEmailId(), "Reset Your Password", bodyText);
	}

	public void sendEmailWithAttachments(String mailTo, String subject, String message, String[] attachFiles)
			throws AddressException, MessagingException, IOException {
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(bankEmailAddress, bankEmailPaswd);
			}
		});
		Message msg = new MimeMessage(session);
		Multipart multipart = new MimeMultipart();
		msg.setFrom(new InternetAddress(bankEmailAddress));
		InternetAddress[] toAddresses = { new InternetAddress(mailTo) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(message, "text/html");
				messageBodyPart.attachFile(filePath);
				multipart.addBodyPart(messageBodyPart);
			}
			msg.setContent(multipart);
			Transport.send(msg);

		}

	}
}
