/*
 * 
 *
 * 
 */
package com.gdm.core.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdm.core.annotation.BmsEnv;
import com.gdm.core.listener.FileUploadProgressListener;

/**
 * 获取上传文件进度
 * 
 * @author yanghongyan
 * @version 2015-03-05
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yanghongyan
 * @updated at 2015-03-05
 */
@BmsEnv
@Controller
@RequestMapping(value = "/upload")
public class FileUploadProgresController {

	/**
	 * 获取上传文件进度.
	 * 
	 * @param session
	 * @return
	 * @author yanghongyan
	 * @version 2015-03-05
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by yanghongyan
	 * @updated at 2015-03-05
	 */
	@ResponseBody
	@RequestMapping(value = "/progres", method = RequestMethod.GET)
	public String progres(HttpSession session) {
		Object progres = session.getAttribute(FileUploadProgressListener.UPLOAD_PROGRESS_SESSION);
		double pro = 0.00;
		String res = null;
		if (progres != null) {
			pro = Double.parseDouble(progres.toString());
			pro = pro * 100;
		}
		if (pro == 100.00) {
			res = "100";
		} else {
			res = String.format("%.2f", pro);
		}
		return res;
	}
}
