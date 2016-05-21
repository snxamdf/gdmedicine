/*
 * 
 *
 * 
 */
package com.yhy.sys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.core.repository.BaseRepository;
import com.yhy.core.service.BaseService;
import com.yhy.sys.domain.UserRoles;
import com.yhy.sys.repository.UserRolesRepository;

/**
 * 用户角色关系表Service.
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
@Service
@Transactional
public class UserRolesService extends BaseService<UserRoles, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserRolesService.class);

	@Autowired
	private UserRolesRepository userRolesRepository;

	@Override
	public BaseRepository<UserRoles, String> getRepository() {
		return userRolesRepository;
	}

}
