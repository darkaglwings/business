package org.frame.business.service.system.menu;

import java.util.List;

import org.frame.business.model.system.Menu;
import org.frame.repository.sql.model.Page;


public interface IMenuService {

	public String abandon(String ids, Integer flag);
	
	public Long create(Menu menu);
	
	public Long edit(Menu menu);
	
	public Long modify(Menu menu);
	
	public Page pagination(Long parentid, String parentname, String system, String title, Long rank, Integer flag, Page page);

	public List<Menu> query(Long userid, String system);
	
	public List<Menu> query(Long parentid, String parentname, String system, String title, Long rank, Integer flag);
	
	public String remove(String ids);
	
	public Menu search(Long id);
	
	public boolean synchronization();
	
}
