/* 
 * 
 *
 * 
 */
package com.yhy.gen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yhy.gen.domain.Tables;
import com.yhy.core.repository.BaseRepository;

public interface TablesRepository extends BaseRepository<Tables, String> {

	@Query("from Tables t where t.moduleId like ?1")
	public List<Tables> findByModuleId(String moduleId);

}
