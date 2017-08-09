package com.reloaderscloud.optiontrade.service;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

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
	public void saveFile(String fileName, byte[] content) {
		  // Instantiates a client
	    Storage storage = StorageOptions.getDefaultInstance().getService();

	    // The name for the new bucket
	    String bucketName = "vix-radar.appspot.com";  // "my-new-bucket";

	    // Creates the new bucket
	    Bucket bucket = storage.get(bucketName);
	    
	    bucket.create(fileName, content,  Bucket.BlobTargetOption.doesNotExist());

	    log.info("Bucket %s created at " +  bucket.getCreateTime());
	}
	
	public byte[] retrieveFile(String fileName) {
		  // Instantiates a client
	    Storage storage = StorageOptions.getDefaultInstance().getService();

	    // The name for the new bucket
	    String bucketName = "vix-radar.appspot.com";  // "my-new-bucket";

	    // Creates the new bucket
	    Bucket bucket = storage.get(bucketName);
	    
	    Blob data = bucket.get(fileName)	;  
	    if(data != null) {
	    	log.info(new String(data.getContent(BlobSourceOption.generationMatch())));
	    	return data.getContent(BlobSourceOption.generationMatch());
	    }
		return null;
	}
}
