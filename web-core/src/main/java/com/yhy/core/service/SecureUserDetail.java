/*
 * 
 *
 * 
 */
package com.yhy.core.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.google.common.collect.Lists;
import com.yhy.core.secure.UserDetail;

/**
 * 自定义UserDetailsService实现类.
 * 
 * @author YHY
 * @version 2014-12-12
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-12-12
 */
class SecureUserDetail implements UserDetail {

	private static final long serialVersionUID = -6891786896927899192L;

	List<GrantedAuthority> authorities = Lists.newArrayList();

	private String id = "";
	private String code = "";
	private String genre = "";
	private String name = "";
	private String passwd = "";

	SecureUserDetail(String id, String code, String genre, String name, String passwd) {
		super();
		this.id = id;
		this.code = code;
		this.genre = genre;
		this.name = name;
		this.passwd = passwd;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getGenre() {
		return this.genre;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public String getPassword() {
		return this.passwd;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
