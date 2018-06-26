package org.frame.business.dao.system.usermenu;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.UserMenu;
import org.frame.repository.sql.model.Page;


public interface IUserMenuDao {

	final String SQL_ABANDON_TEMPLET = "update sys_usermenu set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_usermenu where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_usermenu(id, user, menu, flag) values(?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.user, c.title as username, a.menu, d.title as menuname, a.flag, b.meaning as flagmeaning from sys_usermenu a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG', sys_user c, sys_menu d where 1 = 1 and a.user = c.id and a.menu = d.id";
	
	final String SQL_UPDATE_TEMPLET = "update sys_usermenu set user = ?, menu = ?, flag = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public UserMenu find(Long id);

	public Long insert(UserMenu userMenu);

	public Page pagination(Long userid, String user, Long menuid, String menu, Integer flag, Page page);
	
	public List<UserMenu> select(Long userid, String user, Long menuid, String menu, Integer flag);

	public Integer update(UserMenu userMenu);
	
}
