package org.frame.business.dao.system.userdepartment;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.UserDepartment;
import org.frame.repository.sql.model.Page;


public interface IUserDepartmentDao {

	final String SQL_ABANDON_TEMPLET = "update sys_userdepartment set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_userdepartment where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_userdepartment(id, user, department, flag) values(?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.user, c.title as username, a.department, d.title as departmentname, a.flag, b.meaning as flagmeaning from sys_userdepartment a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG', sys_user c, sys_department d where 1 = 1 and a.user = c.id and a.department = d.id";
	
	final String SQL_UPDATE_TEMPLET = "update sys_userdepartment set user = ?, department = ?, flag = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public UserDepartment find(Long id);

	public Long insert(UserDepartment userDepartment);

	public Page pagination(Long userid, String user, Long departmentid, String department, Integer flag, Page page);
	
	public List<UserDepartment> select(Long userid, String user, Long departmentid, String department, Integer flag);

	public Integer update(UserDepartment userDepartment);
	
}
