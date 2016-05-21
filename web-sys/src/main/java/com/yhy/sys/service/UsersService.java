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
import com.yhy.sys.domain.Users;
import com.yhy.sys.repository.UsersRepository;

/**
 * 用户表Service.
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
public class UsersService extends BaseService<Users, String> {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UsersService.class);

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public BaseRepository<Users, String> getRepository() {
		return usersRepository;
	}

}
