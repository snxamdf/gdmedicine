/*
 * 
 *
 * 
 */
package com.gdm.core.dto;

import lombok.Data;

/**
 * CTL对应的DTO.
 * 
 * @author YHY
 * @version 2015-01-19
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-19
 */
@Data
public class Ctl {

	private String path; // 根据当前环境（BMS或WEB）存放CTL.BMS_PATH或CTL.WEB_PATH
	private String env; // 根据当前环境（BMS或WEB）存放CTL.BMS或CTL.WEB
	private String profile; // [production:生产环境|development:开发环境|test:测试环境]
	private String apis; // 接口URL前缀
	private String assets; // 资源URL前缀
	private String statics; // 静态资源URL前缀
	private String developers; // 开发维护的资源URL前缀
	private String operations; // 运营维护的资源URL前缀
	private String uploads; // 上传文件URL前缀

}
