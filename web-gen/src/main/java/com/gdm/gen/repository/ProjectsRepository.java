/* 
 * 
 *
 * 
 */
package com.gdm.gen.repository;

import java.util.List;

import com.gdm.core.repository.BaseRepository;
import com.gdm.gen.domain.Projects;

public interface ProjectsRepository extends BaseRepository<Projects, String> {

	public List<Projects> findAll();

}
