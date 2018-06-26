package org.frame.business.dao.system.dictionary;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.Dictionary;
import org.frame.repository.sql.model.Page;


public interface IDictionaryDao {
	
	final String SQL_ABANDON_TEMPLET = "update sys_dictionary set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_dictionary where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_dictionary(id, parentid, sort, code, meaning, display, flag, remark) values(?, ?, ?, ?, ?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.parentid, b.meaning as parentname, a.sort, a.code, a.meaning, a.display, a.flag, c.meaning as flagmeaning, a.remark from sys_dictionary a left join sys_dictionary b on a.parentid = b.id left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' where 1 = 1";
	
	final String SQL_UPDATE_TEMPLET = "update sys_dictionary set parentid = ?, sort = ?, code = ?, meaning = ?, display = ?, flag = ?, remark = ? where 1 = 1";
	
	final String SQL_SELECT_SORT_TEMPLET = "select distinct a.sort form sys_dictionary a where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public Dictionary find(Long id);

	public List<Dictionary> group();
	
	public Long insert(Dictionary dictionary);

	public Page pagination(String parentname, String sort, Integer flag, Page page);
	
	public List<Dictionary> select(Long parentid, String parentname, String sort, Integer flag);

	public Integer update(Dictionary dictionary);
	
}
