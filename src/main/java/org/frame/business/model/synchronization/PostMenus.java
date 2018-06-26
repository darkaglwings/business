package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.PostMenu;

@XmlRootElement
public class PostMenus {
	
	private List<PostMenu> postMenus;

	public List<PostMenu> getPostMenus() {
		return postMenus;
	}

	public void setPostMenus(List<PostMenu> postMenus) {
		this.postMenus = postMenus;
	}
	
}
