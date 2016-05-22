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
import com.yhy.sys.domain.Role;

/**
 * 医药管理Repository接口.
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
public interface RoleRepository extends BaseRepository<Role, String> {
	@Query("select r from UserRoles ur,Role r where ur.roleId=r.id and ur.userId = ?1 and ur.deletion = " + Sys.DELETION_NO)
	List<Role> findByUserId(String userId);
}
