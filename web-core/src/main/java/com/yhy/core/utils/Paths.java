/* 
 * 
 *
 * 
 */
package com.yhy.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 路径工具类.
 * 
 * @author YHY
 * @version 2015-01-09
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-09
 */
public abstract class Paths {

	/**
	 * 获取当前项目路径
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getProjectPath() {
		File directory = new File(".");
		//获取当前路径
		try {
			return directory.getCanonicalPath().replace("\\", "/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取当前运行程序jar包所在路径
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getPath() {
		URL url = Paths.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = "";
		try {
			filePath = URLDecoder.decode(url.getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (filePath.endsWith(".jar"))
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		return filePath;
	}

	/**
	 * 获取一个Class的绝对路径
	 * 
	 * @param clazz
	 * @return Class的绝对路径
	 */
	public static String getPathByClass(Class<?> clazz) {
		String path = null;
		try {
			URI uri = clazz.getResource("").toURI();
			File file = new File(uri);
			path = file.getCanonicalPath();
			return path.replace("\\", "/");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 在本项目路径的基础上通过相对路径方式获取新路径
	 * 
	 * @param clazz Class对象
	 * @param relativePath Class对象的相对路径
	 * @return 文件绝对路径
	 */
	public static String getProjectPathByRelative(String relativePath) {
		return getPathByRelative(getProjectPath(), relativePath);
	}

	/**
	 * 在jar包所在路径的基础上通过相对路径方式获取新路径
	 * 
	 * @param clazz Class对象
	 * @param relativePath Class对象的相对路径
	 * @return 文件绝对路径
	 */
	public static String getPathByRelative(String relativePath) {
		return getPathByRelative(getPath(), relativePath);
	}

	/**
	 * 获取一个文件相对于一个Class相对的绝对路径
	 * 
	 * @param clazz Class对象
	 * @param relativePath Class对象的相对路径
	 * @return 文件绝对路径
	 */
	public static String getPathByRelative(Class<?> clazz, String relativePath) {
		return getPathByRelative(getPathByClass(clazz), relativePath);
	}

	/**
	 * 在一个路径的基础上通过相对路径方式获取新路径
	 * 
	 * @param clazz Class对象
	 * @param relativePath Class对象的相对路径
	 * @return 文件绝对路径
	 */
	public static String getPathByRelative(String path, String relativePath) {
		String filePath = null;
		StringBuffer sbPath = new StringBuffer(path);
		sbPath.append(File.separator);
		sbPath.append(relativePath);
		File file = new File(sbPath.toString());
		try {
			filePath = file.getCanonicalPath();
			return filePath.replace("\\", "/");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

}
