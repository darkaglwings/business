package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.UserMenu;

@XmlRootElement
public class UserMenus {
	
	private List<UserMenu> userMenus;

	public List<UserMenu> getUserMenus() {
		return userMenus;
	}

	public void setUserMenus(List<UserMenu> userMenus) {
		this.userMenus = userMenus;
	}
	
}
