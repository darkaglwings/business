package org.frame.business.dao.system.userrole;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.UserRole;
import org.frame.repository.sql.model.Page;


public interface IUserRoleDao {

	final String SQL_ABANDON_TEMPLET = "update sys_userrole set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_userrole where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_userrole(id, user, role, flag) values(?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.user, c.title as username, a.role, d.title as rolename, a.flag, b.meaning as flagmeaning from sys_userrole a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG', sys_user c, sys_role d where 1 = 1 and a.user = c.id and a.role = d.id";
	
	final String SQL_UPDATE_TEMPLET = "update sys_userrole set user = ?, role = ?, flag = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public UserRole find(Long id);

	public Long insert(UserRole userRole);

	public Page pagination(Long userid, String user, Long roleid, String role, Integer flag, Page page);
	
	public List<UserRole> select(Long userid, String user, Long roleid, String role, Integer flag);

	public Integer update(UserRole userRole);
	
}
