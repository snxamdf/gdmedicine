/* 
 * 
 *
 * 
 */
package com.yhy.gen.repository;

import java.util.List;

import com.yhy.core.repository.BaseRepository;
import com.yhy.gen.domain.Projects;

public interface ProjectsRepository extends BaseRepository<Projects, String> {

	public List<Projects> findAll();

}
