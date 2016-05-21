/*
 * 
 *
 * 
 */
package com.yhy.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yhy.core.domain.Sys;
import com.yhy.core.repository.BaseRepository;
import com.yhy.sys.domain.Users;

/**
 * 用户表Repository接口.
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
public interface UsersRepository extends BaseRepository<Users, String> {
	@Query("select u from Users u where u.loginname = ?1 and u.deletion = " + Sys.DELETION_NO)
	Users findByLoginName(String loginName);

	@Query("select distinct r.genre from Users u, UserRoles ur, Role r where u.id=ur.userId and ur.roleId=r.id and u.id = ?1")
	List<String> findPermissionsById(String userId);
}
