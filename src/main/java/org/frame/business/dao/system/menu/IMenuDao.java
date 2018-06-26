package org.frame.business.dao.system.menu;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.Menu;
import org.frame.repository.sql.model.Page;

public interface IMenuDao {
	
	final String SQL_ABANDON_TEMPLET = "update sys_menu set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_menu where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_menu(id, parentid, system, title, rank, uri, display, flag, remark) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	final String SQL_MENU_DEPARTMENT_MULTI_TEMPLET = "select distinct a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id and b.flag = :flag left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' and c.flag = :flag, sys_departmentmenu d, sys_userdepartment e, sys_department f, sys_user g where 1 = 1 and a.id = d.menu and d.department = e.department and e.department = f.id and e.user = g.id and a.flag = :flag and d.flag = :flag and e.flag = :flag and f.flag = :flag and g.flag = :flag and g.id = :id and (:system is null or a.system like :system_name)";
	
	final String SQL_MENU_DEPARTMENT_SINGLE_TEMPLET = "select distinct a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id and b.flag = :flag left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' and c.flag = :flag, sys_departmentmenu d, sys_user e, sys_department f where 1 = 1 and a.id = d.menu and d.department = e.department and e.department = f.id and a.flag = :flag and d.flag = :flag and e.flag = :flag and f.flag = :flag and e.id = :id and (:system is null or a.system like :system_name)";
	
	final String SQL_MENU_NONE_TEMPLET = "select distinct a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id and b.flag = :flag left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' and c.flag = :flag where 1 = 1 and a.flag = :flag and (:system is null or a.system like :system_name)";
	
	final String SQL_MENU_POST_MULTI_TEMPLET = "select distinct a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id and b.flag = :flag left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' and c.flag = :flag, sys_postmenu d, sys_userpost e, sys_post f, sys_user g where 1 = 1 and a.id = d.menu and d.post = e.post and e.post = f.id and e.user = g.id and a.flag = :flag and d.flag = :flag and e.flag = :flag and f.flag = :flag and g.flag = :flag and g.id = :id and (:system is null or a.system like :system_name)";
	
	final String SQL_MENU_POST_SINGLE_TEMPLET = "select distinct a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id and b.flag = :flag left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' and c.flag = :flag, sys_postmenu d, sys_user e, sys_post f where 1 = 1 and a.id = d.menu and d.post = e.post and e.post = f.id and a.flag = :flag and d.flag = :flag and e.flag = :flag and f.flag = :flag and e.id = :id and (:system is null or a.system like :system_name)";
	
	final String SQL_MENU_ROLE_TEMPLET = "select distinct a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id and b.flag = :flag left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' and c.flag = :flag, sys_rolemenu d, sys_userrole e, sys_role f, sys_user g where 1 = 1 and a.id = d.menu and d.role = e.role and e.role = f.id and e.user = g.id and a.flag = :flag and d.flag = :flag and e.flag = :flag and f.flag = :flag and g.flag = :flag and g.id = :id and (:system is null or a.system like :system_name)";
	
	final String SQL_MENU_USER_TEMPLET = "select distinct a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id and b.flag = :flag left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' and c.flag = :flag, sys_usermenu d, sys_user e where 1 = 1 and a.id = d.menu and d.user = e.id and a.flag = :flag and d.flag = :flag and e.flag = :flag and e.id = :id and (:system is null or a.system like :system_name)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.parentid, b.title as parentname, a.system, a.title, a.rank, a.uri, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_menu a left join sys_menu b on a.parentid = b.id left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' where 1 = 1";
	
	final String SQL_UPDATE_TEMPLET = "update sys_menu set parentid = ?, system = ?, title = ?, rank = ?, uri = ?, display = ?, flag = ?, remark = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);
	
	public Menu find(Long id);
	
	public Long insert(Menu menu);
	
	public Page pagination(Long parentid, String parentname, String system, String title, Long rank, Integer flag, Page page);
	
	public List<Menu> select(Long userid, String system);
	
	public List<Menu> select(Long parentid, String parentname, String system, String title, Long rank, Integer flag);
	
	public Integer update(Menu menu);
	
}
