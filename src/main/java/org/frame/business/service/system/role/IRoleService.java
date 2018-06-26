package org.frame.business.service.system.role;

import java.util.List;

import org.frame.business.model.system.Role;
import org.frame.repository.sql.model.Page;


public interface IRoleService {

	public String abandon(String ids, Integer flag);
	
	public Long create(Role role);
	
	public Long edit(Role role);
	
	public Long modify(Role role);
	
	public Page pagination(String title, Long rank, Integer flag, Page page);

	public List<Role> query(Long userid);
	
	public List<Role> query(String title, Long rank, Integer flag);
	
	public String remove(String ids);
	
	public Role search(Long id);
	
	public boolean synchronization();
	
}
