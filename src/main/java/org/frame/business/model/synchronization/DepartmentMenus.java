package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.DepartmentMenu;

@XmlRootElement
public class DepartmentMenus {
	
	private List<DepartmentMenu> departmentMenus;

	public List<DepartmentMenu> getDepartmentMenus() {
		return departmentMenus;
	}

	public void setDepartmentMenus(List<DepartmentMenu> departmentMenus) {
		this.departmentMenus = departmentMenus;
	}
	
}
