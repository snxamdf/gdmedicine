/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 文件操作工具类.
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能.
 * 
 * @author YHY
 * @version 2013-05-15
 */
public abstract class Files {

	private static Logger log = LoggerFactory.getLogger(Files.class);

	/**
	 * 复制单个文件，如果目标文件存在，则不覆盖
	 * 
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String descFileName) {
		File file = new File("");
		file.list();
		file.listFiles();
		return Files.copyFileCover(srcFileName, descFileName, false);
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @param coverlay 如果目标文件已存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay) {
		File srcFile = new File(srcFileName);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			log.debug("复制文件失败，源文件" + srcFileName + "不存在!");
			return false;
		}
		// 判断源文件是否是合法的文件
		else if (!srcFile.isFile()) {
			log.debug("复制文件失败，" + srcFileName + "不是一个文件!");
			return false;
		}
		File descFile = new File(descFileName);
		// 判断目标文件是否存在
		if (descFile.exists()) {
			// 如果目标文件存在，并且允许覆盖
			if (coverlay) {
				log.debug("目标文件已存在，准备删除!");
				if (!Files.delFile(descFileName)) {
					log.debug("删除目标文件" + descFileName + "失败!");
					return false;
				}
			} else {
				log.debug("复制文件失败，目标文件" + descFileName + "已存在!");
				return false;
			}
		} else {
			if (!descFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				log.debug("目标文件所在的目录不存在，创建目录!");
				// 创建目标文件所在的目录
				if (!descFile.getParentFile().mkdirs()) {
					log.debug("创建目标文件所在的目录失败!");
					return false;
				}
			}
		}

		// 准备复制文件
		// 读取的位数
		int readByte = 0;
		InputStream ins = null;
		OutputStream outs = null;
		try {
			// 打开源文件
			ins = new FileInputStream(srcFile);
			// 打开目标文件的输出流
			outs = new FileOutputStream(descFile);
			byte[] buf = new byte[1024];
			// 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
			while ((readByte = ins.read(buf)) != -1) {
				// 将读取的字节流写入到输出流
				outs.write(buf, 0, readByte);
			}
			log.debug("复制单个文件" + srcFileName + "到" + descFileName + "成功!");
			return true;
		} catch (Exception e) {
			log.debug("复制文件失败：" + e.getMessage());
			return false;
		} finally {
			// 关闭输入输出流，首先关闭输出流，然后再关闭输入流
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException oute) {
					oute.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ine) {
					ine.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制整个目录的内容，如果目标目录存在，则不覆盖
	 * 
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录名
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String descDirName) {
		return Files.copyDirectoryCover(srcDirName, descDirName, false);
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录名
	 * @param coverlay 如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectoryCover(String srcDirName, String descDirName, boolean coverlay) {
		File srcDir = new File(srcDirName);
		// 判断源目录是否存在
		if (!srcDir.exists()) {
			log.debug("复制目录失败，源目录" + srcDirName + "不存在!");
			return false;
		}
		// 判断源目录是否是目录
		else if (!srcDir.isDirectory()) {
			log.debug("复制目录失败，" + srcDirName + "不是一个目录!");
			return false;
		}
		// 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		// 如果目标文件夹存在
		if (descDir.exists()) {
			if (coverlay) {
				// 允许覆盖目标目录
				log.debug("目标目录已存在，准备删除!");
				if (!Files.delFile(descDirNames)) {
					log.debug("删除目录" + descDirNames + "失败!");
					return false;
				}
			} else {
				log.debug("目标目录复制失败，目标目录" + descDirNames + "已存在!");
				return false;
			}
		} else {
			// 创建目标目录
			log.debug("目标目录不存在，准备创建!");
			if (!descDir.mkdirs()) {
				log.debug("创建目标目录失败!");
				return false;
			}

		}

		boolean flag = true;
		// 列出源目录下的所有文件名和子目录名
		File[] files = srcDir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				// 如果是一个单个文件，则直接复制
				if (files[i].isFile()) {
					flag = Files.copyFile(files[i].getAbsolutePath(), descDirName + files[i].getName());
					// 如果拷贝文件失败，则退出循环
					if (!flag) {
						break;
					}
				}
				// 如果是子目录，则继续复制目录
				if (files[i].isDirectory()) {
					flag = Files.copyDirectory(files[i].getAbsolutePath(), descDirName + files[i].getName());
					// 如果拷贝目录失败，则退出循环
					if (!flag) {
						break;
					}
				}
			}
		}

		if (!flag) {
			log.debug("复制目录" + srcDirName + "到" + descDirName + "失败!");
			return false;
		}
		log.debug("复制目录" + srcDirName + "到" + descDirName + "成功!");
		return true;

	}

	/**
	 * 删除文件，可以删除单个文件或文件夹
	 * 
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否是返回false
	 */
	public static boolean delFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			log.debug(fileName + "文件不存在!");
			return true;
		} else {
			if (file.isFile()) {
				return Files.deleteFile(fileName);
			} else {
				return Files.deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.debug("删除单个文件" + fileName + "成功!");
				return true;
			} else {
				log.debug("删除单个文件" + fileName + "失败!");
				return false;
			}
		} else {
			log.debug(fileName + "文件不存在!");
			return true;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dirName 被删除的目录所在的文件路径
	 * @return 如果目录删除成功，则返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dirName) {
		String dirNames = dirName;
		if (!dirNames.endsWith(File.separator)) {
			dirNames = dirNames + File.separator;
		}
		File dirFile = new File(dirNames);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.debug(dirNames + "目录不存在!");
			return true;
		}
		boolean flag = true;
		// 列出全部文件及子目录
		File[] files = dirFile.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					flag = Files.deleteFile(files[i].getAbsolutePath());
					// 如果删除文件失败，则退出循环
					if (!flag) {
						break;
					}
				}
				// 删除子目录
				else if (files[i].isDirectory()) {
					flag = Files.deleteDirectory(files[i].getAbsolutePath());
					// 如果删除子目录失败，则退出循环
					if (!flag) {
						break;
					}
				}
			}
		}

		if (!flag) {
			log.debug("删除目录失败!");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			log.debug("删除目录" + dirName + "成功!");
			return true;
		} else {
			log.debug("删除目录" + dirName + "失败!");
			return false;
		}

	}

	/**
	 * 创建单个文件
	 * 
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			log.debug("文件" + descFileName + "已存在!");
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			log.debug(descFileName + "为目录，不能创建目录!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				log.debug("创建文件所在的目录失败!");
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				log.debug(descFileName + "文件创建成功!");
				return true;
			} else {
				log.debug(descFileName + "文件创建失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(descFileName + "文件创建失败!");
			return false;
		}

	}

	/**
	 * 创建目录
	 * 
	 * @param descDirName 目录名,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			log.debug("目录" + descDirNames + "已存在!");
			return false;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			log.debug("目录" + descDirNames + "创建成功!");
			return true;
		} else {
			log.debug("目录" + descDirNames + "创建失败!");
			return false;
		}

	}

	/**
	 * 获取文件ContentType
	 * 
	 * @return 文件ContentType
	 */
	public static String getContentType(String fileName) {
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		Map<String, String> map = new HashMap<String, String>();
		map.put("ez", "application/andrew-inset");
		map.put("hqx", "application/mac-binhex40");
		map.put("cpt", "application/mac-compactpro");
		map.put("doc", "application/msword");
		map.put("rar", "application/octet-stream");
		map.put("bin", "application/octet-stream");
		map.put("dms", "application/octet-stream");
		map.put("lha", "application/octet-stream");
		map.put("lzh", "application/octet-stream");
		map.put("exe", "application/octet-stream");
		map.put("class", "application/octet-stream");
		map.put("so", "application/octet-stream");
		map.put("dll", "application/octet-stream");
		map.put("oda", "application/oda");
		map.put("pdf", "application/pdf");
		map.put("ai", "application/postscript");
		map.put("eps", "application/postscript");
		map.put("ps", "application/postscript");
		map.put("smi", "application/smil");
		map.put("smil", "application/smil");
		map.put("mif", "application/vnd.mif");
		map.put("xls", "application/vnd.ms-excel");
		map.put("ppt", "application/vnd.ms-powerpoint");
		map.put("wbxml", "application/vnd.wap.wbxml");
		map.put("wmlc", "application/vnd.wap.wmlc");
		map.put("wmlsc", "application/vnd.wap.wmlscriptc");
		map.put("bcpio", "application/x-bcpio");
		map.put("vcd", "application/x-cdlink");
		map.put("pgn", "application/x-chess-pgn");
		map.put("cpio", "application/x-cpio");
		map.put("csh", "application/x-csh");
		map.put("dcr", "application/x-director");
		map.put("dir", "application/x-director");
		map.put("dxr", "application/x-director");
		map.put("dvi", "application/x-dvi");
		map.put("spl", "application/x-futuresplash");
		map.put("gtar", "application/x-gtar");
		map.put("hdf", "application/x-hdf");
		map.put("js", "application/x-javascript");
		map.put("skp", "application/x-koan");
		map.put("skd", "application/x-koan");
		map.put("skt", "application/x-koan");
		map.put("skm", "application/x-koan");
		map.put("latex", "application/x-latex");
		map.put("nc", "application/x-netcdf");
		map.put("cdf", "application/x-netcdf");
		map.put("sh", "application/x-sh");
		map.put("shar", "application/x-shar");
		map.put("swf", "application/x-shockwave-flash");
		map.put("sit", "application/x-stuffit");
		map.put("sv4cpio", "application/x-sv4cpio");
		map.put("sv4crc", "application/x-sv4crc");
		map.put("tar", "application/x-tar");
		map.put("tcl", "application/x-tcl");
		map.put("tex", "application/x-tex");
		map.put("texinfo", "application/x-texinfo");
		map.put("texi", "application/x-texinfo");
		map.put("t", "application/x-troff");
		map.put("tr", "application/x-troff");
		map.put("roff", "application/x-troff");
		map.put("man", "application/x-troff-man");
		map.put("me", "application/x-troff-me");
		map.put("ms", "application/x-troff-ms");
		map.put("ustar", "application/x-ustar");
		map.put("src", "application/x-wais-source");
		map.put("xhtml", "application/xhtml+xml");
		map.put("xht", "application/xhtml+xml");
		map.put("zip", "application/zip");
		map.put("au", "audio/basic");
		map.put("snd", "audio/basic");
		map.put("mid", "audio/midi");
		map.put("midi", "audio/midi");
		map.put("kar", "audio/midi");
		map.put("mpga", "audio/mpeg");
		map.put("mp2", "audio/mpeg");
		map.put("mp3", "audio/mpeg");
		map.put("aif", "audio/x-aiff");
		map.put("aiff", "audio/x-aiff");
		map.put("aifc", "audio/x-aiff");
		map.put("m3u", "audio/x-mpegurl");
		map.put("ram", "audio/x-pn-realaudio");
		map.put("rm", "audio/x-pn-realaudio");
		map.put("rpm", "audio/x-pn-realaudio-plugin");
		map.put("ra", "audio/x-realaudio");
		map.put("wav", "audio/x-wav");
		map.put("pdb", "chemical/x-pdb");
		map.put("xyz", "chemical/x-xyz");
		map.put("bmp", "image/bmp");
		map.put("gif", "image/gif");
		map.put("ief", "image/ief");
		map.put("jpeg", "image/jpeg");
		map.put("jpg", "image/jpeg");
		map.put("jpe", "image/jpeg");
		map.put("png", "image/png");
		map.put("tiff", "image/tiff");
		map.put("tif", "image/tiff");
		map.put("djvu", "image/vnd.djvu");
		map.put("djv", "image/vnd.djvu");
		map.put("wbmp", "image/vnd.wap.wbmp");
		map.put("ras", "image/x-cmu-raster");
		map.put("pnm", "image/x-portable-anymap");
		map.put("pbm", "image/x-portable-bitmap");
		map.put("pgm", "image/x-portable-graymap");
		map.put("ppm", "image/x-portable-pixmap");
		map.put("rgb", "image/x-rgb");
		map.put("xbm", "image/x-xbitmap");
		map.put("xpm", "image/x-xpixmap");
		map.put("xwd", "image/x-xwindowdump");
		map.put("igs", "model/iges");
		map.put("iges", "model/iges");
		map.put("msh", "model/mesh");
		map.put("mesh", "model/mesh");
		map.put("silo", "model/mesh");
		map.put("wrl", "model/vrml");
		map.put("vrml", "model/vrml");
		map.put("css", "text/css");
		map.put("html", "text/html");
		map.put("htm", "text/html");
		map.put("asc", "text/plain");
		map.put("txt", "text/plain");
		map.put("rtx", "text/richtext");
		map.put("rtf", "text/rtf");
		map.put("sgml", "text/sgml");
		map.put("sgm", "text/sgml");
		map.put("tsv", "text/tab-separated-values");
		map.put("wml", "text/vnd.wap.wml");
		map.put("wmls", "text/vnd.wap.wmlscript");
		map.put("etx", "text/x-setext");
		map.put("xsl", "text/xml");
		map.put("xml", "text/xml");
		map.put("mpeg", "video/mpeg");
		map.put("mpg", "video/mpeg");
		map.put("mpe", "video/mpeg");
		map.put("qt", "video/quicktime");
		map.put("mov", "video/quicktime");
		map.put("mxu", "video/vnd.mpegurl");
		map.put("avi", "video/x-msvideo");
		map.put("movie", "video/x-sgi-movie");
		map.put("ice", "x-conference/x-cooltalk");
		if (Strings.isBlank(map.get(fileType))) {
			// return "application/octet-stream";
			return "image/bmp";
		} else {
			return map.get(fileType);
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @return 文件大小
	 */
	public static Long getFileSizes(File f) {
		long s = 0;
		FileInputStream fis = null;
		try {
			if (f.exists()) {
				fis = new FileInputStream(f);
				s = fis.available();
			} else {
				f.createNewFile();
				if (log.isDebugEnabled()) {
					log.debug("获取文件大小,文件不存在!");
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
		return s;
	}

	/**
	 * 获取目录下的所有文件名（不包含子目录）.
	 * 读取“e:/tmp/123456”目录下的所有文件名例子：Files.getFileNames("e:/tmp", "123456");
	 * 
	 * @param dir 指定读取的目录名
	 * @param suffix 指定读取的后缀目录名
	 * @return
	 * @author YHY
	 * @version 2014-10-11
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2014-10-11
	 */
	public static List<String> getFileNames(String dir, String suffix) {
		List<String> names = Lists.newArrayList();
		if (Strings.isBlank(dir)) {
			return names;
		}
		if (Strings.isNotBlank(suffix)) {
			if ("/".equals(Strings.right(dir, 1)) || "\\".equals(Strings.right(dir, 1))) {
				dir = dir + suffix;
			} else {
				dir = dir + "/" + suffix;
			}
		}
		File file = new File(dir);
		if (!file.isDirectory()) {
			return names;
		}
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				if (!f.isDirectory()) {
					names.add(f.getName());
				}
			}
		}
		return names;
	}

	/**
	 * 格式化文件路径.
	 * 
	 * @param path
	 * @return
	 * @author YHY
	 * @version 2016-04-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2016-04-13
	 */
	public static String formatPath(String path) {
		String formatPath = "";
		if (Strings.isBlank(path)) {
			formatPath = "/";
		} else {
			formatPath = Strings.replace(path, "//", "/");
			formatPath = Strings.replace(path, "\\", "/");
			if (!Strings.left(formatPath, 1).equals("/")) {
				formatPath = "/" + formatPath;
			}
			if (!Strings.right(formatPath, 1).equals("/")) {
				formatPath = formatPath + "/";
			}
		}
		return formatPath;
	}

}
