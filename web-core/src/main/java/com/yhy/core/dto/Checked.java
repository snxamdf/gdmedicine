/*
 * 
 *
 * 
 */
package com.yhy.core.dto;

import lombok.Data;

/**
 * 带选中状态的DTO.
 * 
 * @author YHY
 * @version 2014-10-20
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-10-20
 */
@Data
public class Checked {

	private String id;
	private String name;
	private String genre;
	private String pId;
	private Boolean checked;
	private String memo;
	private Boolean open = true;

	public Checked() {
		super();
	}

	public Checked(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Checked(String id, String name, Boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.checked = checked;
	}

	public Checked(String id, String name, Boolean checked, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.checked = checked;
		this.memo = memo;
	}

	public Checked(String id, String name, String genre) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
	}

	public Checked(String id, String name, String pId, Boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.checked = checked;
	}

	public Checked(String id, String name, String pId, Boolean checked, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.checked = checked;
		this.memo = memo;
	}

	public Checked(String id, String name, String genre, String pId, Boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.pId = pId;
		this.checked = checked;
	}

	public Checked(String id, String name, String genre, String pId, Boolean checked, String memo) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.pId = pId;
		this.checked = checked;
		this.memo = memo;
	}

	public Checked(String id, String name, String pId, Boolean checked, Boolean open) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.checked = checked;
		this.open = open;
	}

	public Checked(String id, String name, String genre, String pId, Boolean checked, Boolean open) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.pId = pId;
		this.checked = checked;
		this.open = open;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

}
