/* 
 * 
 *
 * 
 */
package com.gdm.core.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * 
 * @author YHY
 * @version 2014-10-21
 */
public abstract class Digests {

	public static byte[] md5(byte[] data) {
		return DigestUtils.md5(data);
	}

	public static byte[] md5(String data) {
		return DigestUtils.md5(data);
	}

	public static String md5Hex(byte[] data) {
		return DigestUtils.md5Hex(data);
	}

	public static String md5Hex(String data) {
		return DigestUtils.md5Hex(data);
	}

	public static byte[] sha1(byte[] data) {
		return DigestUtils.sha1(data);
	}

	public static byte[] sha1(String data) {
		return DigestUtils.sha1(data);
	}

	public static String sha1Hex(byte[] data) {
		return DigestUtils.sha1Hex(data);
	}

	public static String sha1Hex(String data) {
		return DigestUtils.sha1Hex(data);
	}

}
