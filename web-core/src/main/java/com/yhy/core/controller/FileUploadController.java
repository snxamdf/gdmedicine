/*
 * 
 *
 * 
 */
package com.yhy.core.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yhy.core.annotation.BmsEnv;
import com.yhy.core.api.ArchiveService;
import com.yhy.core.dto.Archive;
import com.yhy.core.dto.Result;
import com.google.common.io.Files;

/**
 * 文件上传
 * 
 * @author yanghongyan
 * @version 2015-01-27
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yanghongyan
 * @updated at 2015-01-27
 */
@BmsEnv
@Controller
@RequestMapping(value = "/file")
public class FileUploadController {

	private static Logger logger = Logger.getLogger(FileUploadController.class);

	@Value("${core.assets.uploads.path}")
	private String downloadPath;

	@Value("${core.upload.max.size}")
	private Integer maxSize;

	@Autowired
	private ArchiveService archiveService;

	/**
	 * ckedit 上传文件方法
	 * 
	 * @param ext 文件扩展名 限制
	 * @param size 文件大小 限制
	 * @param modePath 上传文件目录(子项目缩写/业务名称) MODULE
	 * @param upload 上传的文件
	 * @param CKEditorFuncNum ckedit 默认切换tab
	 * @param response
	 * @author yanghongyan
	 * @version 2015-01-27
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by yanghongyan
	 * @updated at 2015-01-27
	 */
	@RequestMapping(value = "/ckedit/upload", method = RequestMethod.POST)
	public void ckeditUpload(String ext, String size, @RequestParam("modePath") String modePath, @RequestParam("upload") MultipartFile upload, String CKEditorFuncNum, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			Result<Archive> result = this.upload(ext, size, upload, modePath);
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			String script = "<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(";
			if (result.getData() != null) {
				script = script + "'" + CKEditorFuncNum + "','" + downloadPath + result.getData().getPath() + "')</script>";
				logger.info("上传图片成功 ckeditUpload");
			} else {
				script = script + "'" + CKEditorFuncNum + "','上传图片失败:" + result.getMessage() + "')</script>";
				logger.error("上传图片失败 ckeditUpload" + result.getMessage());
			}
			out.print(script);
		} catch (Exception e) {
			logger.error("上传图片失败 ckeditUpload", e);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 上传文件方法
	 * 
	 * @param ext 限制的扩展名
	 * @param size 限制文件大小
	 * @param modePath 上传文件目录(子项目缩写/业务名称) MODULE
	 * @param file 上传的文件
	 * @return
	 * @author yanghongyan
	 * @version 2015-01-26
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by yanghongyan
	 * @updated at 2015-01-26
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result<Archive> upload(String ext, String size, @RequestParam("modePath") String modePath, MultipartFile file) {
		Result<Archive> result;
		try {
			//掉用上传方法
			result = this.upload(ext, size, file, modePath);
			logger.info("ext:" + ext + " size:" + ext + modePath + "文件上传");
		} catch (Exception e) {
			logger.error("文件上传失败 upload", e);
			result = new Result<Archive>();
			result.setSuccess(false);
			result.setMessage("文件上传失败");
		}
		return result;
	}

	//上传文件
	private Result<Archive> upload(String ext, String size, MultipartFile file, String modePath) {
		//检查文件扩展名限制
		if (ext == null || "".equals(ext.trim())) {
			ext = "rar,zip,txt,doc,docx,pdf,ppt,xls,xlsx,jpg,png,gif,jpeg,bmp";
		}
		//检查上传大小限制
		if (size == null || "".equals(size)) {
			size = maxSize.toString();
		}
		Result<Archive> resultArchive = null;
		long lsize = file.getSize();//获取大小
		if (lsize > Integer.parseInt(size)) {
			resultArchive = new Result<Archive>();
			resultArchive.setStatus(2);//大小不合法
			lsize = Long.parseLong(size) / 1024l / 1024l;
			resultArchive.setMessage("文件大小超过" + lsize + "M 无法上传");
			resultArchive.setSuccess(false);
			return resultArchive;
		}
		String fileName = file.getOriginalFilename();
		String fileExt = Files.getFileExtension(fileName);
		String[] exts = ext.split(",");
		boolean bool = false;
		for (String e : exts) {
			if (fileExt.equalsIgnoreCase(e)) {
				bool = true;
				break;
			}
		}
		//符合上传文件扩展名规则
		if (bool) {
			//上传文件
			resultArchive = archiveService.upload(file, modePath);
			resultArchive.setSuccess(true);
		} else {
			resultArchive = new Result<Archive>();
			resultArchive.setMessage("不支持上传该后缀的文件");
			resultArchive.setSuccess(false);
		}
		return resultArchive;
	}

	/**
	 * 删除文件方法
	 * 
	 * @param request
	 * @param path 上传文件目录(子项目缩写/业务名称) MODULE
	 * @author yanghongyan
	 * @version 2015-01-19
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by yanghongyan
	 * @updated at 2015-01-19
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Result<Archive> deleteFile(String fileName) {
		Result<Archive> result;
		try {
			result = new Result<Archive>();
			result.setSuccess(true);
			logger.info("删除文件成功 deleteFile " + fileName);
		} catch (Exception e) {
			logger.error("删除文件失败 deleteFile ", e);
			result = new Result<Archive>();
			result.setSuccess(false);
		}
		return result;
	}

}
