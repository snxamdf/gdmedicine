/*
 * 
 *
 * 
 */
package com.yhy.app1.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.yhy.core.domain.Sys;

/**
 * 公告表Domain
 * 
 * @author yanghongyan
 * @version 2015-01-12
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yanghongyan
 * @updated at 2015-01-12
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_cms_affiche")
public class CmsAffiche extends Sys<String> {
	private static final long serialVersionUID = -4612145003980136767L;
	public static final String STATUS_YES = "1";//显示
	public static final String STATUS_NO = "0";//不显示
	public static final String STATE_YES = "1";//置顶
	public static final String STATE_NO = "0";//不置顶
	public static final String CT="0";//用来判断页面展示 内容判断
	public static final String TYPE="affiche";//用来判断是哪个详细页面

	@NotBlank(message = "标题不能为空")
	@Length(max = 60, message = "字数太多了，只能输入最多60个字符")
	private String title;
	@NotBlank(message = "正文不能为空")
	private String content;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date publishTime;
	private String state;
	private String status;

}
