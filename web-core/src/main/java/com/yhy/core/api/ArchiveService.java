/*
 * 
 *
 * 
 */
package com.yhy.core.api;

import org.springframework.web.multipart.MultipartFile;

import com.yhy.core.dto.Archive;
import com.yhy.core.dto.Result;

/**
 * 文件上传下载服务接口.
 * 
 * @author YHY
 * @version 2015-01-16
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-16
 */
public interface ArchiveService {

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
	Result<Archive> upload(MultipartFile file, String path);

}
