package org.frame.business.model.system;

public class UserDepartment {
	
	private Long id;
	
	private Long user;
	
	private String username;
	
	private Long department;
	
	private String departmentname;

	private Integer flag;
	
	private String flagmeaning;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getFlagmeaning() {
		return flagmeaning;
	}

	public void setFlagmeaning(String flagmeaning) {
		this.flagmeaning = flagmeaning;
	}

}
