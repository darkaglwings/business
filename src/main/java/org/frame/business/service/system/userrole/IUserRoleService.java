package org.frame.business.service.system.userrole;

import java.util.List;

import org.frame.business.model.system.UserRole;
import org.frame.repository.sql.model.Page;


public interface IUserRoleService {

	public String abandon(String ids, Integer flag);
	
	public String abandon(Long id, Long userid, Long roleid, Integer flag);
	
	public String assign(Long userid, String roles, Long roleid, String users);
	
	public Long create(UserRole userRole);
	
	public Long edit(UserRole userRole);
	
	public Long modify(UserRole userRole);

	public Page pagination(Long userid, String user, Long roleid, String role, Integer flag, Page page);
	
	public List<UserRole> query(Long userid, String user, Long roleid, String role, Integer flag);
	
	public String remove(String ids);
	
	public String remove(Long id, Long userid, Long roleid);
	
	public UserRole search(Long id);
	
	public boolean synchronization();
	
}
