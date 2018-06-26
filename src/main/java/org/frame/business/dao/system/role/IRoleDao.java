package org.frame.business.dao.system.role;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.Role;
import org.frame.repository.sql.model.Page;


public interface IRoleDao {

	final String SQL_ABANDON_TEMPLET = "update sys_role set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_role where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_role(id, code, title, rank, flag, remark) values(?, ?, ?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.code, a.title, a.rank, a.flag, b.meaning as flagmeaning, a.remark from sys_role a left join sys_dictionary b on a.flag = b.code and b.sort = 'FLAG' where 1 = 1";
	
	final String SQL_UPDATE_TEMPLET = "update sys_role set code = ?, title = ?, rank = ?, flag = ?, remark = ? where 1 = 1";
	
	final String SQL_USER_ROLE_TEMPLET = "select a.id, a.code, a.title, a.rank, a.flag, b.meaning as flagmeaning, a.remark from sys_role a left join sys_dictionary b on a.flag = b.code and b.sort = 'FLAG', sys_userrole c where a.id = c.role and a.flag = ? and c.user = ?";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);
	
	public Role find(Long id);
	
	public Long insert(Role role);
	
	public Page pagination(String title, Long rank, Integer flag, Page page);
	
	public List<Role> select(Long userid);
	
	public List<Role> select(String title, Long rank, Integer flag);
	
	public Integer update(Role role);
	
}
