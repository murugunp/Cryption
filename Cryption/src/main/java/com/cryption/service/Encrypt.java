package com.cryption.service;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

@Service
public abstract interface Encrypt {
	public static final int BITSIZE = 256;
	public static final String KEYTYPE = "AES";
	public static final String ENCODING = "UTF8";
	public static final String PADDING = "/CBC/PKCS5Padding";
	public static final int PASSWORD_KEY_LENGTH = 16;

	public abstract int getBitSize();

	public abstract String getKeyType();

	public abstract String getEncoding();

	public abstract String getEncryptionKey();

	public abstract String toHex(byte[] paramArrayOfByte);

	public abstract byte[] fromHex(String paramString);

	public abstract SecretKey generateSecretKey(int paramInt) throws Exception;

	public abstract String encrypt(String paramString1, String paramString2)
			throws Exception;

	public abstract String decrypt(String paramString1, String paramString2)
			throws Exception;
}
