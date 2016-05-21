/*
 * 
 *
 * 
 */
package com.yhy.core.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于Base64进行两次编码解码工具类.
 */
public final class BASE64 {

	private static final String CHARSET = "UTF-8";

	private static Logger logger = LoggerFactory.getLogger(BASE64.class);

	/**
	 * 基于Base64进行两次编码.
	 */
	public static String encode2Base64(String src) {
		try {
			String result = Base64.encodeBase64String(src.getBytes(CHARSET));
			return Base64.encodeBase64String(result.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 基于Base64进行两次解码.
	 */
	public static String decode2Base64(String src) {
		try {
			String result = new String(Base64.decodeBase64(src), CHARSET);
			return new String(Base64.decodeBase64(result), CHARSET);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(encode2Base64("测试abc"));
		System.out.println(decode2Base64("NXJXTDZLK1ZZV0pq"));
		System.out.println(encode2Base64("{\"subProductCode\":\"11831229\",\"channelBizNoList\":\"201408060344600031\"}"));
		System.out.println(encode2Base64("{\"maxAmount\":\"5000.0\",\"channelBizNo\":\"234729384729\",\"cardNo\":\"330421198710230019\"}"));
		System.out.println(decode2Base64("ZXlkdFlYaEJiVzkxYm5Rbk9qVXdNREF1TUN3blkyaGhibTVsYkVKcGVrNXZKem95TXpRM01qa3pPRFEzTWprc0oyTmhjbVJPYnljNk16TXdOREl4TVRrNE56RXdNak13TURFNWZRPT0="));
		System.out.println(encode2Base64("{\"subProductCode\":\"11791231\",\"channelBizNoList\":\"234729384729\"}"));
		System.out.println(decode2Base64("ZXlKallYSmtUbThpT2lJME1qQTFNalV4T1RjNU1USXhOakF3TWpraUxDSmphR0Z1Ym1Wc1FtbDZUbThpT2lJeU1ERTBNRGd3TkRNNU9EQTJOakF3TURFaUxDSnRZWGhCYlc5MWJuUWlPakkxTURBdU1IMD0="));
	}

}
