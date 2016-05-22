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
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
public interface UserRolesRepository extends BaseRepository<UserRoles, String> {
	@Query("select r from UserRoles r where r.userId = ?1 and r.deletion = " + Sys.DELETION_NO)
	List<UserRoles> findByUserId(String userId);
}
