package com.cryption.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryption.model.Content;


@Service
public class CryptionDelegate {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	Encrypt encrypt;
	
	public Content decryptContent(Content content) {
		log.info("Start decryption"+content.toString());
		
		String coded = null;
		try {
			coded = encrypt.decrypt(content.getText(),
					"7888888878");
		} catch (Exception e) {
			coded = "Wrong string keyed in";
		}
		Content output = new Content(coded);
		log.info(output.toString());
		return output;
	}

	public  Content encryptContent(Content content) {
		log.info("Start encryption: "+content.toString());
		String coded = null;
		try {
			coded = encrypt.encrypt(content.getText(),
					"7888888878");
		} catch (Exception e) {
			coded = "Wrong string keyed in";
		}
		Content output = new Content(coded);
		log.info(output.toString());
		return output;
	}
	
}
