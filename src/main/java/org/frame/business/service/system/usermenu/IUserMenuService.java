package org.frame.business.service.system.usermenu;

import java.util.List;

import org.frame.business.model.system.UserMenu;
import org.frame.repository.sql.model.Page;


public interface IUserMenuService {

	public String abandon(String ids, Integer flag);
	
	public String abandon(Long id, Long menuid, Long userid, Integer flag);
	
	public String assign(Long userid, String menus, Long menuid, String users);
	
	public Long create(UserMenu userMenu);
	
	public Long edit(UserMenu userMenu);
	
	public Long modify(UserMenu userMenu);

	public Page pagination(Long userid, String user, Long menuid, String menu, Integer flag, Page page);
	
	public List<UserMenu> query(Long userid, String user, Long menuid, String menu, Integer flag);
	
	public String remove(String ids);
	
	public String remove(Long id, Long userid, Long menuid);
	
	public UserMenu search(Long id);
	
	public boolean synchronization();
	
}
