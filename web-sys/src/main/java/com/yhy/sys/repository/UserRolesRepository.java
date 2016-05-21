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
import com.yhy.sys.domain.UserRoles;

/**
 * 用户角色关系表Repository接口.
 * 
 * @author yhy
 * @version 2016-05-21
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-21
 */
public interface UserRolesRepository extends BaseRepository<UserRoles, String> {

	@Query("select u from UserRoles u where u.userId = ?1 and u.deletion = " + Sys.DELETION_NO)
	List<UserRoles> findByUserId(String userId);
}
