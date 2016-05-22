/*
 * 
 *
 * 
 */
package com.yhy.medicine.repository;

import org.springframework.data.jpa.repository.Query;

import com.yhy.core.domain.Sys;
import com.yhy.core.repository.BaseRepository;
import com.yhy.medicine.domain.MedicineMed;

/**
 * 药品管理表Repository接口.
 * 
 * @author yhy
 * @version 2016-05-22
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by yhy
 * @updated at 2016-05-22
 */
public interface MedicineMedRepository extends BaseRepository<MedicineMed, String> {

	@Query("select mm from MedicineMed mm where mm.barcode = ?1 and mm.deletion = " + Sys.DELETION_NO)
	MedicineMed findByBarcode(String barcode);
}
