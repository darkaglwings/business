package org.frame.business.service.system.dictionary;

import java.util.List;

import org.frame.business.model.system.Dictionary;
import org.frame.repository.sql.model.Page;


public interface IDictionaryService {
	
	public String abandon(String ids, Integer flag);
	
	public Long create(Dictionary dictionary);
	
	public Long edit(Dictionary dictionary);
	
	public List<Dictionary> group();
	
	public Long modify(Dictionary dictionary);
	
	public Page pagination(String parentname, String sort, Integer flag, Page page);
	
	public List<Dictionary> query(String sort);
	
	public List<Dictionary> query(Long parentid, String sort);
	
	public List<Dictionary> query(Long parentid, String parentname, String sort, Integer flag);
	
	public String remove(String ids);
	
	public Dictionary search(Long id);
	
	public boolean synchronization();
	
}
