package com.reloaderscloud.optiontrade.service;

public interface CloudService {
	public void sendEmail(String emailAddress, String subject, String message);
	
	public void saveFile(String fileName, byte[] content);
	
	public byte[] retrieveFile(String fileName);
}
