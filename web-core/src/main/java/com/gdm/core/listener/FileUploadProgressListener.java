/*
 * 
 *
 * 
 */
package com.gdm.core.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

/**
 * 上传文件进度监听类
 * 
 * @author yanghongyan
 * @version 2015-03-05
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yanghongyan
 * @updated at 2015-03-05
 */
public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;
	public final static String UPLOAD_PROGRESS_SESSION = "UPLOAD_PROGRESS_SESSION";

	public FileUploadProgressListener(HttpSession session) {
		this.session = session;
	}

	/**
	 * @param bytesRead 已经上传的大小
	 * @param contentLength 文件总大小
	 * @author yanghongyan
	 * @version 2015-03-05
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by yanghongyan
	 * @updated at 2015-03-05
	 */
	@Override
	public void update(long bytesRead, long contentLength, int items) {
		//System.out.println((double) bytesRead / contentLength);
		session.setAttribute(UPLOAD_PROGRESS_SESSION, (double) bytesRead / contentLength);
	}

}
