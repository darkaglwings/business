package org.frame.business.service.system.post;

import java.util.List;

import org.frame.business.model.system.Post;
import org.frame.repository.sql.model.Page;


public interface IPostService {
	
	public String abandon(String ids, Integer flag);
	
	public Long create(Post post);
	
	public Long edit(Post post);
	
	public Long modify(Post post);
	
	public Page pagination(Long parentid, String parentname, String title, Integer flag, Page page);

	public List<Post> query(Long userid);
	
	public List<Post> query(Long parentid, String parentname, String title, Integer flag);
	
	public String remove(String ids);
	
	public Post search(Long id);
	
	public boolean synchronization();
	
}
