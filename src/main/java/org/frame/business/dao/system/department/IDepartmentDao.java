package org.frame.business.dao.system.department;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.Department;
import org.frame.repository.sql.model.Page;


public interface IDepartmentDao {

	final String SQL_ABANDON_TEMPLET = "update sys_department set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_department where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_department(id, parentid, rank, fullname, title, flag, remark) values(?, ?, ?, ?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.parentid, b.title as parentname, a.rank, a.fullname, a.title, a.flag, c.meaning as flagmeaning, a.remark from sys_department a left join sys_department b on a.parentid = b.id left join sys_dictionary c on a.flag = c.code and c.sort='FLAG' where 1 = 1";
	
	final String SQL_UPDATE_TEMPLET = "update sys_department set parentid = ?, rank = ?, fullname = ?, title = ?, flag = ?, remark = ? where 1 = 1";
	
	final String SQL_USER_DEPARTMENT_MULTI_TEMPLET = "select a.id, a.parentid, '' as parentname, a.rank, a.fullname, a.title, a.flag, '' as flagmeaning, a.remark from sys_department a, sys_userdepartment b where a.id = b.department";
	
	final String SQL_USER_DEPARTMENT_SINGLE_TEMPLET = "select a.id, a.parentid, '' as parentname, a.rank, a.fullname, a.title, a.flag, '' as flagmeaning, a.remark from sys_department a, sys_user b where a.id = b.department";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public Department find(Long id);

	public Long insert(Department department);

	public Page pagination(Long parentid, String parentname, String title, Integer flag, Page page);
	
	public List<Department> select(Long userid);
	
	public List<Department> select(Long parentid, String parentname, String title, Integer flag);

	public Integer update(Department department);
	
}
