package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.UserRole;

@XmlRootElement
public class UserRoles {
	
	private List<UserRole> userRoles;

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
}
