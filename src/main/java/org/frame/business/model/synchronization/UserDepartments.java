package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.UserDepartment;

@XmlRootElement
public class UserDepartments {
	
	private List<UserDepartment> userDepartments;

	public List<UserDepartment> getUserDepartments() {
		return userDepartments;
	}

	public void setUserDepartments(List<UserDepartment> userDepartments) {
		this.userDepartments = userDepartments;
	}
	
}
