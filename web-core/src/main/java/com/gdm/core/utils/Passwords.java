/* 
 * 
 *
 * 
 */
package com.gdm.core.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 密码工具类.
 * 
 * @author YHY
 * @version 2014-12-17
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-12-17
 */
public abstract class Passwords {

	private static final String SECRET = "ynd2-secret-key";
	private static final PasswordEncoder encoder = new StandardPasswordEncoder(SECRET);

	/**
	 * 密码加密.
	 * 
	 * @param rawPassword 原始密码
	 * @return 加密后的密码
	 * @author YHY
	 * @version 2014-12-17
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-12-17
	 */
	public static String encrypt(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	/**
	 * 密码是否匹配.
	 * 
	 * @param rawPassword 原始密码
	 * @param password 加密后的密码
	 * @return
	 * @author YHY
	 * @version 2014-12-17
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-12-17
	 */
	public static boolean match(String rawPassword, String password) {
		return encoder.matches(rawPassword, password);
	}

	/**
	 * 获取默认的PasswordEncoder.
	 * 
	 * @return
	 * @author YHY
	 * @version 2014-12-17
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-12-17
	 */
	public static PasswordEncoder getPasswordEncoder() {
		return encoder;
	}

}
