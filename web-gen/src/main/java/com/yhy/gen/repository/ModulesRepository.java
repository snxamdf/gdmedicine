/* 
 * 
 *
 * 
 */
package com.yhy.gen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yhy.core.repository.BaseRepository;
import com.yhy.gen.domain.Modules;

public interface ModulesRepository extends BaseRepository<Modules, String> {

	@Query("from Modules t where t.projectId like ?1")
	public List<Modules> findByProjectId(String projectId);

}
