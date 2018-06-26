package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.UserPost;

@XmlRootElement
public class UserPosts {
	
	private List<UserPost> userPosts;

	public List<UserPost> getUserPosts() {
		return userPosts;
	}

	public void setUserPosts(List<UserPost> userPosts) {
		this.userPosts = userPosts;
	}
	
}
