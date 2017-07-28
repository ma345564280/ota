package com.ota.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSATool {
	/** 指定加密算法为RSA */
	public static final String ALGORITHM = "RSA";
	/** 密钥长度，用来初始化 */
	public static final int KEYSIZE = 1024;
	/** 指定公钥存放文件 */
	public static String PUBLIC_KEY_FILE = "LocalPublicKey";
	/** 指定私钥存放文件 */
	public static String PRIVATE_KEY_FILE = "LocalPrivateKey";
	/** 指定密文存放文件 */
	public static String RSA_CODE = "LocalEncryptFile";
	
	/**
     * 生成密钥对
	 * @param localPirvateKey 
	 * @param localPulbicKey 
     * @throws Exception
     */
	public static void generateKeyPair(String localPulbicKey, String localPirvateKey) throws Exception {
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
		/** 初始化这个KeyPairGenerator对象 */
		keyPairGenerator.initialize(KEYSIZE);
		/** 生成密匙对 */
		KeyPair keypair = keyPairGenerator.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = (Key) keypair.getPublic();
		/** 得到私钥 */
		Key privateKey = (Key) keypair.getPrivate();
		ObjectOutputStream oos1 = null;
		ObjectOutputStream oos2 = null;
		
		try {
			/** 用对象流将生成的密钥写入文件 */
			oos1 = new ObjectOutputStream(new FileOutputStream(localPulbicKey));
			oos2 = new ObjectOutputStream(new FileOutputStream(localPirvateKey));
			
			oos1.writeObject(publicKey);
			oos2.writeObject(privateKey);
		} catch (Exception e) {
			throw e;
		} finally {
			oos1.close();
			oos2.close();
		}
	
	}
	
	/**
     * 加密方法
     * @param source 源数据
     * @return
     * @throws Exception
     */
	public static String encryptByPublicKey(String source) throws Exception{
		Key publicKey;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			/** 将文件中的公钥对象读出 */
			ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
			publicKey = (Key) ois.readObject();
			/** 得到Cipher对象来实现对源数据的RSA加密 */
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, (java.security.Key) publicKey);
			byte[] b = source.getBytes();
			/** 执行加密操作 */
			byte[] b1 = cipher.doFinal(b);
			BASE64Encoder encoder = new BASE64Encoder();
			String rsacode = encoder.encode(b1);
			oos = new ObjectOutputStream(new FileOutputStream(RSA_CODE));
			oos.writeObject(rsacode);
			return rsacode;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ois.close();
			oos.close();
		}
		
		return "Error";
	}
	
	public static String encryptByPrivateKey(String source) throws Exception{
		Key privateKey;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
			privateKey = (Key) ois.readObject();
			/** 得到Cipher对象来实现对源数据的RSA加密 */
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, (java.security.Key) privateKey);
			byte[] b = source.getBytes();
			/** 执行加密操作 */
			byte[] b1 = cipher.doFinal(b);
			BASE64Encoder encoder = new BASE64Encoder();
			String rsacode = encoder.encode(b1);
			oos = new ObjectOutputStream(new FileOutputStream(RSA_CODE));
			oos.writeObject(rsacode);
			return rsacode;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ois.close();
			oos.close();
		}
		
		return "Error";
	}
	 /**
     * 解密算法
     * @param cryptograph    密文
     * @return
     * @throws Exception
     */
	public static String decryptByPrivateKey(String cryptograph) throws Exception {
		Key privateKey;
		ObjectInputStream ois = null;
		try {
			/** 将文件中的私钥对象读出 */
			ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
			privateKey = (Key) ois.readObject();

			/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, (java.security.Key) privateKey);
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b1 = decoder.decodeBuffer(cryptograph);
			/** 执行解密操作 */
			byte[] b = cipher.doFinal(b1);
			return new String(b);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ois.close();
		}
		return "Error";
	}
	
	public static String decryptByPublicKey(String cryptograph, String realPath) throws Exception {
		Key publicKey;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(realPath));
		
			publicKey = (Key) ois.readObject();

			/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, (java.security.Key) publicKey);
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b1 = decoder.decodeBuffer(cryptograph);
			/** 执行解密操作 */
			byte[] b = cipher.doFinal(b1);
			return new String(b);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ois.close();
		}
		return "Error";
	}
	
	
	public static String getPlaintext(String pathRSACODE, String pathPublicKey) throws IOException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(pathRSACODE));
			String encryptedgraph = (String) ois.readObject();
			String decryptedgraph = RSATool.decryptByPublicKey(encryptedgraph, pathPublicKey);
			return decryptedgraph;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ois.close();
		}
		return "Error";
	}
	
	
	public static int parsePlaintext(String plaintext) {
		//0表示license过期，正数表示表示license还可以用的天数，负数表示license不合法
		String[] resolver = plaintext.split("-");
		String webUrl = "http://www.baidu.com";
		
		if(resolver.length != 2) 
			return -1;
		if(!resolver[0].equals("datamax"))
			return -1;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date end = sdf.parse(resolver[1]);
			Date now = sdf.parse(OTAToolKit.getWebsiteDatetime(webUrl));
			long endTime = end.getTime();
			long nowTime = now.getTime();
			long remainingTime = endTime - nowTime; 
			if(remainingTime  <= 0) return 0;
			long remainingDay = remainingTime / (1000 * 60 * 60 * 24);
			return (int) remainingDay;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return -1;
		
	}
}
