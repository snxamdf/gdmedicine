/*
 * 
 *
 * 
 */
package com.yhy.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yhy.sys.domain.Users;
import com.yhy.sys.repository.UsersRepository;

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
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// boolean isBmsProfile = env.acceptsProfiles(PROFILES.BMS);
		Users user = usersRepository.findByLoginName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		List<String> userRoles = usersRepository.findPermissionsById(user.getId());
		System.out.println(userRoles);
		SecureUserDetail userDetail = new SecureUserDetail(user.getId(), user.getLoginname(), user.getPasswd());

		List<GrantedAuthority> authorities = Lists.newArrayList();
		for (String value : userRoles) {
			authorities.add(new SimpleGrantedAuthority(value));
		}
		userDetail.setAuthorities(authorities);
		return userDetail;
	}
}
