/* 
 * 
 *
 * 
 */
package com.yhy.gen.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.yhy.core.domain.Pk;
import com.yhy.core.utils.Strings;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "v_gen_columns")
public class Columns extends Pk<String> {

	private static final long serialVersionUID = 731059744398113077L;
	private String tableName = "";
	private String name = "";
	private String comment = "";
	private String types = "";
	private int length = 0;
	private int scale = 0;
	private String nullable = "N";
	private String defaults = "";
	private String pkey = "";

	public String getIgnore(String extend) {
		if ("_id".equals(Strings.right(name, 3))) {
			return "true";
		}
		return getIsSys(extend);
	}

	public String getIsSys(String extend) {
		if ("id".equals(name) || "memo".equals(name)) {
			return "true";
		}
		if ("creater".equals(name) || "created".equals(name) || "modifier".equals(name) || "modified".equals(name)) {
			return "true";
		}
		if ("version".equals(name) || "deletion".equals(name) || "history".equals(name)) {
			return "true";
		}
		if ("tree".equals(extend)) {
			if ("code".equals(name) || "name".equals(name) || "fullname".equals(name)) {
				return "true";
			}
			if ("genre".equals(name) || "ordinal".equals(name) || "leaf".equals(name) || "grade".equals(name)) {
				return "true";
			}
			if ("parent_id".equals(name) || "parent_ids".equals(name) || "parent_codes".equals(name) || "parent_names".equals(name)) {
				return "true";
			}
		}
		return "false";
	}

	public String getClassName() {
		return Strings.camel(name, true);
	}

	public String getBeanName() {
		return Strings.camel(name);
	}

	public String getJavaType() {
		if ("varchar".equals(types) || "varchar2".equals(types) || "text".equals(types)) {
			return "String";
		} else if ("date".equals(types) || "datetime".equals(types)) {
			return "Date";
		} else if ("number".equals(types) || "decimal".equals(types) || "int".equals(types) || "tinyint".equals(types) || "smallint".equals(types) || "bigint".equals(types)) {
			if (scale > 0) {
				return "Double";
			} else {
				if (length > 10) { // mysql的int类型长度为10
					return "Long";
				} else if (length > 0 && length <= 5) {
					return "Short";
				}
				return "Integer";
			}
		}
		return "String";
	}

	public String getJdbcType() {
		if ("varchar".equals(types) || "varchar2".equals(types) || "text".equals(types)) {
			return "VARCHAR";
		} else if ("char".equals(types)) {
			return "CHAR";
		} else if ("date".equals(types) || "datetime".equals(types)) {
			return "TIMESTAMP";
		} else if ("number".equals(types) || "decimal".equals(types) || "int".equals(types) || "tinyint".equals(types) || "smallint".equals(types) || "bigint".equals(types)) {
			return "DECIMAL";
		}
		return types;
	}

}
