/*
 * 
 *
 * 
 */
package com.yhy.core.extend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yhy.core.listener.FileUploadProgressListener;

/**
 * 重写 CommonsMultipartResolver 类 parseRequest方法
 * 上传文件进度获取类
 * 
 * @author yanghongyan
 * @version 2015-03-05
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yanghongyan
 * @updated at 2015-03-05
 */
public class CommonsMultipartResolverProgress extends CommonsMultipartResolver {
	private static Logger logger = LoggerFactory.getLogger(CommonsMultipartResolverProgress.class);

	/**
	 * 重写 parseRequest
	 * 为获取FileUpload并设置setProgressListener监听
	 * 
	 * @author yanghongyan
	 * @version 2015-03-05
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by yanghongyan
	 * @updated at 2015-03-05
	 */
	@Override
	protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		//设置监听器
		fileUpload.setProgressListener(new FileUploadProgressListener(request.getSession()));
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			logger.error("上传文件超出允许最大限制 当前上传文件大小:" + fileUpload.getSizeMax(), ex);
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		} catch (FileUploadException ex) {
			logger.error("Could not parse multipart servlet request ", ex);
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}
}
