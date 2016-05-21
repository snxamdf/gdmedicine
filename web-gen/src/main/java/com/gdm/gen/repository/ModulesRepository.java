/* 
 * 
 *
 * 
 */
package com.gdm.gen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.gdm.core.repository.BaseRepository;
import com.gdm.gen.domain.Modules;

public interface ModulesRepository extends BaseRepository<Modules, String> {

	@Query("from Modules t where t.projectId like ?1")
	public List<Modules> findByProjectId(String projectId);

}
