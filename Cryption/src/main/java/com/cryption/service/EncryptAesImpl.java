package com.cryption.service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service
public class EncryptAesImpl implements Encrypt {
	private int bitSize;
	private String keyType;
	private String encoding;
	private String encryptionKey;

	public void setBitSize(int bitSize) {
		this.bitSize = bitSize;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public int getBitSize() {
		return this.bitSize;
	}

	public String getKeyType() {
		return this.keyType;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public String getEncryptionKey() {
		return this.encryptionKey;
	}

	public EncryptAesImpl() {
		this.bitSize = 256;
		this.keyType = "AES";
		this.encoding = "UTF8";
	}

	public String toHex(byte[] buffer) {
		StringBuffer strbuf = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			if ((buffer[i] & 0xFF) < 16) {
				strbuf.append("0");
			}
			strbuf.append(Long.toString(buffer[i] & 0xFF, 16));
		}
		return strbuf.toString();
	}

	public byte[] fromHex(String s) {
		int n = s.length() / 2;
		byte[] res = new byte[n];
		for (int i = 0; i < n; i++) {
			res[i] = ((byte) Integer
					.parseInt(s.substring(2 * i, 2 * i + 2), 16));
		}
		return res;
	}

	public SecretKey generateSecretKey(int i) throws Exception {
		KeyGenerator kgen = null;
		kgen.init(this.bitSize);
		SecretKey skey = kgen.generateKey();
		try {
			kgen = KeyGenerator.getInstance(this.keyType);
		} catch (NoSuchAlgorithmException e) {

			throw new Exception(e);
		}
		/*
		 * KeyGenerator kgen; kgen.init(this.bitSize); SecretKey skey =
		 * kgen.generateKey()
		 */;

		return new SecretKeySpec(skey.getEncoded(), this.keyType);
	}

	public byte[] createKeyFromPassword(String password) throws Exception {
		byte[] keyBytes = new byte[16];
		byte[] b;
		try {
			b = password.getBytes(getEncoding());
		} catch (UnsupportedEncodingException e) {
			// byte[] b;
			throw new Exception(e);
		}

		int len = b.length;
		if (len > 16) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);
		return keyBytes;
	}

	public String encrypt(String text, String password) throws Exception {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(getKeyType() + "/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {

			throw new Exception(e);
		} catch (NoSuchPaddingException e) {
			throw new Exception(e);
		}

		byte[] keyBytes = new byte[16];
		byte[] b;
		try {
			b = password.getBytes(getEncoding());
		} catch (UnsupportedEncodingException e) {

			throw new Exception(e);
		}

		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);

		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, getKeyType());

		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		try {
			cipher.init(1, keySpec, ivSpec);
		} catch (InvalidKeyException e) {
			throw new Exception(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new Exception(e);
		}
		byte[] results;
		try {
			results = cipher.doFinal(text.getBytes(getEncoding()));
		} catch (IllegalBlockSizeException e) {

			throw new Exception(e);
		} catch (BadPaddingException e) {
			throw new Exception(e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
		// byte[] results;
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(results);
	}

	public String decrypt(String text, String password) throws Exception {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(getKeyType() + "/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {

			throw new Exception(e);
		} catch (NoSuchPaddingException e) {
			throw new Exception(e);
		}

		byte[] keyBytes = new byte[16];
		byte[] b;
		try {
			b = password.getBytes(getEncoding());
		} catch (UnsupportedEncodingException e) {

			throw new Exception(e);
		}

		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, getKeyType());

		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		try {
			cipher.init(2, keySpec, ivSpec);
		} catch (InvalidKeyException e) {
			throw new Exception(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw new Exception(e);
		}
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] results;
		try {
			results = cipher.doFinal(decoder.decodeBuffer(text));
		} catch (IllegalBlockSizeException e) {

			throw new Exception(e);
		} catch (BadPaddingException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
		try {
			return new String(results, getEncoding());
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		}
	}
}
