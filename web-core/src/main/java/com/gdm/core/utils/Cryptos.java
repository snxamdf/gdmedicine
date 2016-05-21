/* 
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 支持HMAC-SHA1消息签名 及 DES/AES对称加密的工具类.
 * 支持Hex与Base64两种编码方式.
 * 
 * @author YHY
 * @version 2014-10-21
 */
public abstract class Cryptos {

	public static final String AES_KEY = "YND_AES_KEY";

	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String HMACSHA1 = "HmacSHA1";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160; // RFC2401
	private static final int DEFAULT_AES_KEYSIZE = 128;
	private static final int DEFAULT_IVSIZE = 16;

	// -- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * 
	 * @param input 原始输入字符数组
	 * @param key HMAC-SHA1密钥
	 */
	public static byte[] hmacSha1(byte[] input, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 校验HMAC-SHA1签名是否正确.
	 * 
	 * @param expected 已存在的签名
	 * @param input 原始输入字符串
	 * @param key 密钥
	 */
	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节).
	 * HMAC-SHA1算法对密钥无特殊要求, RFC2401建议最少长度为160位(20字节).
	 */
	public static byte[] generateHmacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	// -- AES funciton --//
	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static String aesEncrypt(String input, String key) {
		byte[] aesKey = generateAesKey(key);
		byte[] encryptResult = aes(input.getBytes(Charsets.UTF8), aesKey, Cipher.ENCRYPT_MODE);
		return Encodes.encodeHex(encryptResult);
	}

	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String aesDecrypt(byte[] input, byte[] key) {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return new String(decryptResult, Charsets.UTF8);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String aesDecrypt(String input, String key) {
		byte[] aesKey = generateAesKey(key);
		byte[] decryptFrom = Encodes.decodeHex(input);
		byte[] decryptResult = aes(decryptFrom, aesKey, Cipher.DECRYPT_MODE);
		return new String(decryptResult, Charsets.UTF8);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量
	 */
	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
		return new String(decryptResult, Charsets.UTF8);
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 原始字节数组
	 * @param key 符合AES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 原始字节数组
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
	 */
	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	/**
	 * 生成AES密钥,可选长度为128,192,256位.
	 */
	public static byte[] generateAesKey(int keysize) {
		return generateAesKey(keysize, AES_KEY);
	}

	/**
	 * 生成AES密钥,可选长度为128,192,256位.
	 */
	public static byte[] generateAesKey(String key) {
		return generateAesKey(DEFAULT_AES_KEYSIZE, key);
	}

	/**
	 * 生成AES密钥,可选长度为128,192,256位.
	 */
	public static byte[] generateAesKey(int keysize, String key) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			//防止linux下 随机生成key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
			secureRandom.setSeed(key.getBytes());
			keyGenerator.init(keysize, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
	 */
	public static byte[] generateIV() {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
			byte[] bytes = new byte[DEFAULT_IVSIZE];
			secureRandom.nextBytes(bytes);
			return bytes;
		} catch (NoSuchAlgorithmException e) {
			throw Exceptions.unchecked(e);
		} catch (NoSuchProviderException e) {
			throw Exceptions.unchecked(e);
		}
	}
}