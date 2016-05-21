/*
 * 
 *
 * 
 */
package com.gdm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

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
@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {

	@Autowired
	private Environment env;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		SecureUserDetail userDetail = new SecureUserDetail("1", "001", "true", "", "");
		List<GrantedAuthority> authorities = Lists.newArrayList();
		userDetail.setAuthorities(authorities);

		return userDetail;
	}

}
