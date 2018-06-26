package org.frame.business.service.system.user;

import java.util.List;

import org.frame.business.model.system.User;
import org.frame.repository.sql.model.Page;


public interface IUserService {
	
	public String abandon(String ids, Integer flag);
	
	public Long create(User user);
	
	public Long edit(User user);
	
	public Long modify(User user);
	
	public Page pagination(String title, String username, String sex, String department, String post, Integer flag, Page page);

	public List<User> query(Long id, String department, String post, Integer flag);
	
	public List<User> query(String title, String username, String sex, String department, String post, Integer flag);
	
	public String remove(String ids);
	
	public User search(Long id);
	
	public boolean synchronization();
	
}
