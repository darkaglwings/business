package org.frame.business.service.system.userdepartment;

import java.util.List;

import org.frame.business.model.system.UserDepartment;
import org.frame.repository.sql.model.Page;


public interface IUserDepartmentService {

	public String abandon(String ids, Integer flag);
	
	public String abandon(Long id, Long userid, Long departmentid, Integer flag);
	
	public String assign(Long userid, String departments, Long departmentid, String users);
	
	public Long create(UserDepartment userDepartment);
	
	public Long edit(UserDepartment userDepartment);
	
	public Long modify(UserDepartment userDepartment);

	public Page pagination(Long userid, String user, Long departmentid, String department, Integer flag, Page page);
	
	public List<UserDepartment> query(Long userid, String user, Long departmentid, String department, Integer flag);
	
	public String remove(String ids);
	
	public String remove(Long id, Long userid, Long departmentid);
	
	public UserDepartment search(Long id);
	
	public boolean synchronization();
	
}
