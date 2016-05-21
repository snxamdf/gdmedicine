/*
 * 
 *
 * 
 */
package com.yhy.core.dto;

import lombok.Data;

/**
 * 文件DTO.
 * 
 * @author YHY
 * @version 2015-01-16
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-16
 */
@Data
public class Archive {

	private String id; // 文件名："374d24da-9d6c-11e4-a96d-005056a88ea8.doc"
	private String name; // 文件名："凭证模板.doc"
	private String ext; // 文件名：".doc"
	private String path;  // 文件下载地址："/vchr/templet/374d24da-9d6c-11e4-a96d-005056a88ea8.doc"

}
