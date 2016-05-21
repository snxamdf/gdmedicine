/*
 * 
 *
 * 
 */
package com.yhy.core.api;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yhy.core.dto.Archive;
import com.yhy.core.dto.Result;
import com.yhy.core.exception.UploadException;
import com.yhy.core.utils.Identities;
import com.yhy.core.utils.Results;
import com.google.common.io.Files;

/**
 * 文件上传下载服务实现类.
 * 
 * @author YHY
 * @version 2015-01-16
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-16
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

	@Value("${core.upload.path}")
	private String uploadPath;

	@Value("${core.upload.max.size}")
	private int uploadMaxSize;

	private final String pathPrefix = "/archive";

	/**
	 * 文件上传.
	 * .
	 * 上传文件目录命名规则：
	 * 子项目缩写/业务名称
	 * 示例：sys/help 、 cms/news 、 vchr/templet 、 mbr/apply
	 * 不要包含“大写字母”、“-”、“_”等字符
	 * 
	 * @param file 上传的文件
	 * @param path 上传文件目录(子项目缩写/业务名称)：sys/templet
	 * @return
	 * @author YHY
	 * @version 2015-01-16
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-16
	 */
	public Result<Archive> upload(MultipartFile file, String path) {
		boolean isMatches = matcherPath(path);
		if (!isMatches) {
			return Results.fault(-1, "文件上传路径不合法!", null);
		}
		if (file == null) {
			return Results.fault(-2, "上传文件不能为空!", null);
		}
		if (file.getSize() <= 0) {
			return Results.fault(-3, "上传文件内容不能为空!", null);
		}
		if (file.getSize() > uploadMaxSize) {
			return Results.fault(-4, "上传文件大小超过最大值限制!", null);
		}
		// 上传文件
		String formatPath = com.yhy.core.utils.Files.formatPath(path);
		String fileId = Identities.uuid2();
		String fileName = file.getOriginalFilename();
		String fileExt = Files.getFileExtension(fileName);
		String fileIdName = fileId + "." + fileExt;
		String filePath = uploadPath + pathPrefix + formatPath + fileIdName;
		File uploadFile = new File(filePath);
		try {
			Files.createParentDirs(uploadFile);
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (IOException e) {
			throw new UploadException(e);
		}
		// 返回上传文件信息
		Archive archive = new Archive();
		archive.setId(fileIdName);
		archive.setName(fileName);
		archive.setExt(fileExt);
		archive.setPath(formatPath + fileIdName);
		return Results.success(archive);
	}

	/**
	 * 判断路径是否合法.
	 * 
	 * @param path
	 * @author YHY
	 * @version 2015-01-16
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-16
	 */
	private boolean matcherPath(String path) {
		Pattern pattern = Pattern.compile("^[a-z]+[a-z](/[a-z]+[a-z]){1,}$");
		Matcher matcher = pattern.matcher(path);
		boolean isMatches = matcher.matches();
		return isMatches;
	}

}
