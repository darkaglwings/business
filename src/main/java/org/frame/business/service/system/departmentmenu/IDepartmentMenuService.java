package org.frame.business.service.system.departmentmenu;

import java.util.List;

import org.frame.business.model.system.DepartmentMenu;
import org.frame.repository.sql.model.Page;


public interface IDepartmentMenuService {

	public String abandon(String ids, Integer flag);
	
	public String abandon(Long id, Long departmentid, Long menuid, Integer flag);
	
	public String assign(Long departmentid, String menus, Long menuid, String departments);
	
	public Long create(DepartmentMenu departmentMenu);
	
	public Long edit(DepartmentMenu departmentMenu);
	
	public Long modify(DepartmentMenu departmentMenu);

	public Page pagination(Long departmentid, String department, Long menuid, String menu, Integer flag, Page page);
	
	public List<DepartmentMenu> query(Long departmentid, String department, Long menuid, String menu, Integer flag);
	
	public String remove(String ids);
	
	public String remove(Long id, Long departmentid, Long menuid);
	
	public DepartmentMenu search(Long id);
	
	public boolean synchronization();
	
}
