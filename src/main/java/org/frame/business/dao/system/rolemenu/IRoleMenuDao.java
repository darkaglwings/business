package org.frame.business.dao.system.rolemenu;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.RoleMenu;
import org.frame.repository.sql.model.Page;


public interface IRoleMenuDao {

	final String SQL_ABANDON_TEMPLET = "update sys_rolemenu set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_rolemenu where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_rolemenu(id, role, menu, flag) values(?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.role, c.title as rolename, a.menu, d.title as menuname, a.flag, b.meaning as flagmeaning from sys_rolemenu a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG', sys_role c, sys_menu d where 1 = 1 and a.role = c.id and a.menu = d.id";
	
	final String SQL_UPDATE_TEMPLET = "update sys_rolemenu set role = ?, menu = ?, flag = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public RoleMenu find(Long id);

	public Long insert(RoleMenu roleMenu);

	public Page pagination(Long roleid, String role, Long menuid, String menu, Integer flag, Page page);
	
	public List<RoleMenu> select(Long roleid, String role, Long menuid, String menu, Integer flag);

	public Integer update(RoleMenu roleMenu);
	
}
