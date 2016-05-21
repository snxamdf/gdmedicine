/* 
 * 
 *
 * 
 */
package com.gdm.gen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.gdm.core.repository.BaseRepository;
import com.gdm.gen.domain.Columns;

public interface ColumnsRepository extends BaseRepository<Columns, String> {

	public List<Columns> findByTableName(String tablename);

	@Query("select count(t) from Columns t where t.types = 'datetime' and t.tableName = ?1")
	public Long countDatetime(String tablename);

	@Query("select count(t) from Columns t where t.types = 'datetime' and t.tableName = ?1 and t.name not in ('created','modified')")
	public Long sumDatetime(String tablename);

}
