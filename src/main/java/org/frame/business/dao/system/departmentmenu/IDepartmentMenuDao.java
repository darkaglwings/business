package org.frame.business.dao.system.departmentmenu;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.DepartmentMenu;
import org.frame.repository.sql.model.Page;


public interface IDepartmentMenuDao {

	final String SQL_ABANDON_TEMPLET = "update sys_departmentmenu set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_departmentmenu where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_departmentmenu(id, department, menu, flag) values(?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.department, c.title as departmentname, a.menu, d.title as menuname, a.flag, b.meaning as flagmeaning from sys_departmentmenu a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG', sys_department c, sys_menu d where 1 = 1 and a.department = c.id and a.menu = d.id";
	
	final String SQL_UPDATE_TEMPLET = "update sys_departmentmenu set department = ?, menu = ?, flag = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public DepartmentMenu find(Long id);

	public Long insert(DepartmentMenu departmentMenu);

	public Page pagination(Long departmentid, String department, Long menuid, String menu, Integer flag, Page page);
	
	public List<DepartmentMenu> select(Long departmentid, String department, Long menuid, String menu, Integer flag);

	public Integer update(DepartmentMenu departmentMenu);
	
}
