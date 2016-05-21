/*
 * 
 *
 * 
 */
package com.gdm.core.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

/**
 * 图片工具类.
 * https://github.com/coobird/thumbnailator
 * 
 * @author YHY
 * @version 2013-6-13
 * @----------------------------------------------------------------------------------------
 * @updated 创建.
 * @updated by YHY
 * @updated at 2013-6-13
 */
public abstract class Images {

	private static Logger logger = LoggerFactory.getLogger(Images.class);

	/**
	 * 通过文件扩展名判断是否是图片文件.
	 * 
	 * @param fileName
	 * @return
	 * @author YHY
	 * @version 2013-8-26
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013-8-26
	 */
	public static boolean isPicture(String fileName) {
		String ext = Files.getFileExtension(fileName);
		if (Strings.isBlank(ext)) {
			return false;
		}
		// 目前只判断以下类型
		String imgArray[] = { "bmp", "gif", "png", "jpg", "jpeg" };
		for (int i = 0; i < imgArray.length; i++) {
			if (ext.toLowerCase().equals(imgArray[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是合法图片.
	 * 
	 * @param fileName
	 * @return
	 * @author YHY
	 * @version 2013-8-26
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013-8-26
	 */
	public static boolean isImage(String fileName) {
		File file = new File(fileName);
		if (file.exists() == false) {
			logger.debug("isImage:exists(" + fileName + ") = false");
			return false;
		}
		if (Strings.isNotBlank((Files.getFileExtension(fileName))) && isPicture(fileName) == false) {
			logger.debug("isImage:isNotBlank|isPicture = false");
			return false;
		}
		try {
			return isImage(Files.toByteArray(file));
		} catch (IOException e) {
			logger.error("判断是否是合法图片:isImage:" + fileName + "\n" + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 判断是否是合法图片.
	 * 
	 * @param imageContent
	 * @return
	 * @author YHY
	 * @version 2013-8-26
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2013-8-26
	 */
	public static boolean isImage(byte[] imageContent) {
		if (imageContent == null || imageContent.length == 0) {
			return false;
		}
		Image img = null;
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(imageContent);
			img = ImageIO.read(is);
			if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("判断是否是合法图片:isImage2:\n" + e.getMessage(), e);
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("判断是否是合法图片:isImage2:close:\n" + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 对图片裁剪，并把裁剪新图片保存.
	 * 
	 * @param fromFile 读取源图片路径
	 * @param toFile 写入图片路径
	 * @param x 剪切起始点x坐标
	 * @param y 剪切起始点y坐标
	 * @param width 剪切宽度
	 * @param height 剪切高度
	 * @author YHY
	 * @version 2013-6-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 改用Thumbnailator开源组件实现.
	 * @updated by YHY
	 * @updated at 2014-09-04
	 */
	public static void crop(String fromFile, String toFile, int x, int y, int width, int height) {
		crop(fromFile, toFile, x, y, width, height, width, height);
	}

	/**
	 * 对图片裁剪，并把裁剪新图片保存.
	 * 
	 * @param fromFile 读取源图片路径
	 * @param toFile 写入图片路径
	 * @param x 剪切起始点x坐标
	 * @param y 剪切起始点y坐标
	 * @param width 剪切宽度
	 * @param height 剪切高度
	 * @param sizeWidth 缩放宽度
	 * @param sizeHeight 缩放高度
	 * @author YHY
	 * @version 2013-6-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 改用Thumbnailator开源组件实现.
	 * @updated by YHY
	 * @updated at 2014-09-04
	 */
	public static void crop(String fromFile, String toFile, int x, int y, int width, int height, int sizeWidth, int sizeHeight) {
		try {
			Files.createParentDirs(new File(toFile));
			Thumbnails.of(fromFile).sourceRegion(x, y, width, height).size(sizeWidth, sizeHeight).outputQuality(0.85f).toFile(toFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 缩放图像（按比例缩放）.
	 * 
	 * @param fromFile 源图像文件地址
	 * @param toFile 缩放后的图像地址
	 * @param scale 缩放比例
	 * @author YHY
	 * @version 2013-6-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 改用Thumbnailator开源组件实现.
	 * @updated by YHY
	 * @updated at 2014-09-04
	 */
	public final static void scale(String fromFile, String toFile, double scale) {
		try {
			Files.createParentDirs(new File(toFile));
			Thumbnails.of(fromFile).scale(scale).toFile(toFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 缩放图像（按高度和宽度缩放）.
	 * 若图片横比width小，高比height小，不变
	 * 若图片横比width小，高比height大，高缩小到height，图片比例不变
	 * 若图片横比width大，高比height小，横缩小到width，图片比例不变
	 * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
	 * 
	 * @param fromFile 源图像文件地址
	 * @param toFile 缩放后的图像地址
	 * @param width 缩放后的宽度
	 * @param height 缩放后的高度
	 * @author YHY
	 * @version 2013-6-13
	 * @----------------------------------------------------------------------------------------
	 * @updated 改用Thumbnailator开源组件实现.
	 * @updated by YHY
	 * @updated at 2014-09-04
	 */
	public final static void scale(String fromFile, String toFile, int width, int height) {
		try {
			Files.createParentDirs(new File(toFile));
			Thumbnails.of(fromFile).size(width, height).toFile(toFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 缩放图像（按高度和宽度缩放）.
	 * 若图片横比width小，高比height小，不变
	 * 若图片横比width小，高比height大，高缩小到height，图片比例不变
	 * 若图片横比width大，高比height小，横缩小到width，图片比例不变
	 * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
	 * 
	 * @param fromFile 源图像文件地址
	 * @param toFile 缩放后的图像地址
	 * @param maxWidth 缩放后的宽度(最大值)
	 * @param maxHeight 缩放后的高度(最大值)
	 * @param fileExt 扩展名
	 * @author sdh
	 * @version 2015-03-27
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by sdh
	 * @updated at 2015-03-27
	 */
	public final static void scale(String fromFile, String toFile, int maxWidth, int maxHeight, String fileExt) {
		try {
			Files.createParentDirs(new File(toFile));

			BufferedImage image = ImageIO.read(new File(fromFile));
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			if(logger.isDebugEnabled()){
				logger.debug("图片尺寸:" + imageWidth + "X" + imageHeight);
			}
			Builder<BufferedImage> builder = null;
			if (imageWidth <= maxWidth && imageHeight <= maxHeight) {
				//若图片横比width小，高比height小，不变
				Thumbnails.of(fromFile).size(imageWidth, imageHeight).toFile(toFile);
			} else if (imageWidth >= maxWidth && imageHeight >= maxHeight) {
				//若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
				if (imageHeight * 1000 / imageWidth != 3000 / 4) {
					if (imageWidth > imageHeight) {
						image = Thumbnails.of(fromFile).height(maxHeight).asBufferedImage();
					} else {
						image = Thumbnails.of(fromFile).width(maxWidth).asBufferedImage();
					}
					builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, maxWidth, maxHeight).size(maxWidth, maxHeight);
					builder.outputFormat(fileExt).toFile(toFile);
				} else {
					Thumbnails.of(fromFile).size(maxWidth, maxHeight).toFile(toFile);
				}
			} else {
				//若图片横比width小，高比height大，高缩小到height，图片比例不变
				//若图片横比width大，高比height小，横缩小到width，图片比例不变
				if (imageWidth > maxWidth) {
					image = Thumbnails.of(fromFile).width(maxWidth).asBufferedImage();
				} else {
					image = Thumbnails.of(fromFile).height(maxHeight).asBufferedImage();
				}
				builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, maxWidth, maxHeight).size(maxWidth, maxHeight);
				builder.outputFormat(fileExt).toFile(toFile);
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
