package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.RoleMenu;

@XmlRootElement
public class RoleMenus {
	
	private List<RoleMenu> roleMenus;

	public List<RoleMenu> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(List<RoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}
	
}
