/* 
 * 
 *
 * 
 */
package com.gdm.gen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.gdm.gen.domain.Tables;
import com.gdm.core.repository.BaseRepository;

public interface TablesRepository extends BaseRepository<Tables, String> {

	@Query("from Tables t where t.moduleId like ?1")
	public List<Tables> findByModuleId(String moduleId);

}
