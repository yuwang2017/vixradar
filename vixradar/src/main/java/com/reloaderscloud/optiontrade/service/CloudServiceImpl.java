package com.reloaderscloud.optiontrade.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CloudServiceImpl implements CloudService  {
	public void sendEmail(String emailAddress, String subject, String message) {
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
			e.printStackTrace();
		}
	}
}
