package org.frame.business.service.system.userpost;

import java.util.List;

import org.frame.business.model.system.UserPost;
import org.frame.repository.sql.model.Page;


public interface IUserPostService {

	public String abandon(String ids, Integer flag);
	
	public String abandon(Long id, Long userid, Long postid, Integer flag);
	
	public String assign(Long userid, String posts, Long postid, String users);
	
	public Long create(UserPost userPost);
	
	public Long edit(UserPost userPost);
	
	public Long modify(UserPost userPost);

	public Page pagination(Long userid, String user, Long postid, String post, Integer flag, Page page);
	
	public List<UserPost> query(Long userid, String user, Long postid, String post, Integer flag);
	
	public String remove(String ids);
	
	public String remove(Long id, Long userid, Long postid);
	
	public UserPost search(Long id);
	
	public boolean synchronization();
	
}
