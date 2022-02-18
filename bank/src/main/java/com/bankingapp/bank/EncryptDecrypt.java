package com.bankingapp.bank;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program to encrypt and decrypt passwords(AES).
 * 
 * @author mohamed.jalloh. E-mail address: mohamed.jalloh@we-plus.eu. Bank API
 *         task. Last changed 14/12/2021.
 */

/*
 * Possible key values are 128,192 and 256. possible T-LEN values are 128,
 * 120,112, 104 and 96.
 */
public class EncryptDecrypt {
	private SecretKey key;
	private int Key_SIZE = 128;
	private int T_LEN = 128;
	private byte[] IV;

	public void init() throws Exception {
		KeyGenerator generator;

		generator = KeyGenerator.getInstance("AES");

		generator.init(Key_SIZE);
		key = generator.generateKey();

	}
	
	public void initFromStrings(String secretKey, String IV) {
		
		// Removed algorithm: next to "AES"
		key=new SecretKeySpec(decode(secretKey),  "AES");
		this.IV=decode(IV);
	}
	
	public String encryptOld(String message) throws Exception {
		byte[] messageInBytes = message.getBytes();
		Cipher encryptionCipher = Cipher.getInstance("AES/GCM/noPadding");
		encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
		IV=encryptionCipher.getIV();

		byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
		return encode(encryptedBytes);

	}
	public String encrypt(String message) throws Exception {
		byte[] messageInBytes = message.getBytes();
		Cipher encryptionCipher = Cipher.getInstance("AES/GCM/noPadding");
		GCMParameterSpec spec=new GCMParameterSpec(T_LEN, IV);
		encryptionCipher.init(Cipher.ENCRYPT_MODE, key,spec);
		IV=encryptionCipher.getIV();

		byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
		return encode(encryptedBytes);

	}

	public String decrypt(String encryptedMessage) throws Exception {
		byte[] messageInBytes = decode(encryptedMessage);
		Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(T_LEN,IV);
		decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
		return new String(decryptedBytes);
	}

	private String encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}

void exportKeys() {
	System.err.println("SecretKey: "+encode(key.getEncoded()));
	System.err.println("IV: "+encode(IV));
	}
}
	

