package org.frame.business.service.system.postmenu;

import java.util.List;

import org.frame.business.model.system.PostMenu;
import org.frame.repository.sql.model.Page;


public interface IPostMenuService {

	public String abandon(String ids, Integer flag);
	
	public String abandon(Long id, Long postid, Long menuid, Integer flag);
	
	public String assign(Long postid, String menus, Long menuid, String posts);
	
	public Long create(PostMenu postMenu);
	
	public Long edit(PostMenu postMenu);
	
	public Long modify(PostMenu postMenu);

	public Page pagination(Long postid, String post, Long menuid, String menu, Integer flag, Page page);
	
	public List<PostMenu> query(Long postid, String post, Long menuid, String menu, Integer flag);
	
	public String remove(String ids);
	
	public String remove(Long id, Long postid, Long menuid);
	
	public PostMenu search(Long id);
	
	public boolean synchronization();
	
}
