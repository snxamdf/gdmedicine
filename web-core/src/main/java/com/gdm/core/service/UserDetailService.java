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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gdm.core.constants.PROFILES;
import com.gdm.core.domain.Users;
import com.gdm.core.repository.UserRepository;
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
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		boolean isBmsProfile = env.acceptsProfiles(PROFILES.BMS);
		Users user = userRepository.findByLoginName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		SecureUserDetail userDetail = new SecureUserDetail(user.getId(), "001", "root", user.getLoginname(), user.getPasswd());
		List<GrantedAuthority> authorities = Lists.newArrayList();
		if (isBmsProfile) {
			authorities.add(new SimpleGrantedAuthority("bms:role:admin"));
		} else {
			authorities.add(new SimpleGrantedAuthority("web:role:admin"));
		}
		userDetail.setAuthorities(authorities);
		return userDetail;
	}
}
