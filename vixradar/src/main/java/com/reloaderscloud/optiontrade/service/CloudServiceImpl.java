package com.reloaderscloud.optiontrade.service;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CloudServiceImpl implements CloudService  {
	
	private static final Logger log = Logger.getLogger(CloudServiceImpl.class.getName());
	
	public void sendEmail(String emailAddress, String subject, String message) {
		log.info("Send mail to " + emailAddress);
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("service.reloaderscloud@gmail.com", "VIX Alert Service"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			msg.setSubject(subject);
			msg.setText(message);
			Transport.send(msg);

		} catch (Exception e) {
			log.warning(e.toString());
		}
	}
}
