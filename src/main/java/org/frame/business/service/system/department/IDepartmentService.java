package org.frame.business.service.system.department;

import java.util.List;

import org.frame.business.model.system.Department;
import org.frame.repository.sql.model.Page;


public interface IDepartmentService {
	
	public String abandon(String ids, Integer flag);
	
	public Long create(Department department);
	
	public Long edit(Department department);
	
	public Long modify(Department department);
	
	public Page pagination(Long parentid, String parentname, String title, Integer flag, Page page);

	public List<Department> query(Long userid);
	
	public List<Department> query(Long parentid, String parentname, String title, Integer flag);
	
	public String remove(String ids);
	
	public Department search(Long id);
	
	public boolean synchronization();
	
}
