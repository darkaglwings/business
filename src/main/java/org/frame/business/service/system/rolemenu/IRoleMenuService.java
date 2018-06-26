package org.frame.business.service.system.rolemenu;

import java.util.List;

import org.frame.business.model.system.RoleMenu;
import org.frame.repository.sql.model.Page;


public interface IRoleMenuService {

	public String abandon(String ids, Integer flag);
	
	public String abandon(Long id, Long roleid, Long menuid, Integer flag);
	
	public String assign(Long roleid, String menus, Long menuid, String roles);
	
	public Long create(RoleMenu roleMenu);
	
	public Long edit(RoleMenu roleMenu);
	
	public Long modify(RoleMenu roleMenu);

	public Page pagination(Long roleid, String role, Long menuid, String menu, Integer flag, Page page);
	
	public List<RoleMenu> query(Long roleid, String role, Long menuid, String menu, Integer flag);
	
	public String remove(String ids);
	
	public String remove(Long id, Long roleid, Long menuid);
	
	public RoleMenu search(Long id);
	
	public boolean synchronization();
	
}
