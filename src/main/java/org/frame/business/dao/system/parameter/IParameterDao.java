package org.frame.business.dao.system.parameter;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.Parameter;
import org.frame.repository.sql.model.Page;

public interface IParameterDao {
	
	final String SQL_ABANDON_TEMPLET = "update sys_parameter set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_parameter where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_parameter(id, title, code, flag, remark) values(?, ?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.title, a.code, a.flag, b.meaning as flagmeaning, a.remark from sys_parameter a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG' where 1 = 1";
	
	final String SQL_UPDATE_TEMPLET = "update sys_parameter set title = ?, code = ?, flag = ?, remark = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);
	
	public Parameter find(Long id);

	public Long insert(Parameter parameter);
	
	public Page pagination(String title, Integer flag, Page page);
	
	public List<Parameter> select(String title, Integer flag);

	public Integer update(Parameter parameter);

}
