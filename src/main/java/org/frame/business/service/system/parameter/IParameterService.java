package org.frame.business.service.system.parameter;

import java.util.List;

import org.frame.business.model.system.Parameter;
import org.frame.repository.sql.model.Page;


public interface IParameterService {

	public String abandon(String ids, Integer flag);
	
	public Long create(Parameter parameter);
	
	public Long edit(Parameter parameter);
	
	public Long modify(Parameter parameter);
	
	public Page pagination(String title, Integer flag, Page page);

	public List<Parameter> query(String title, Integer flag);
	
	public String remove(String ids);
	
	public Parameter search(Long id);
	
	public boolean synchronization();
	
}
