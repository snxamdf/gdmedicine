package com.gdm.core.repository;

import org.springframework.data.jpa.repository.Query;

import com.gdm.core.domain.Sys;
import com.gdm.core.domain.Users;

/**
 * 用户资源类.
 * 
 * @author YHY
 * @version 2014-09-02
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2014-09-02
 */
public interface UserRepository extends BaseRepository<Users, String> {

	@Query("select u from Users u where u.loginname = ?1 and u.deletion = " + Sys.DELETION_NO)
	Users findByLoginName(String loginName);
}
