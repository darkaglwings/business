package org.frame.business.model.system;

import org.frame.web.annotation.tree.Tree;
import org.frame.web.annotation.tree.TreeElement;

@Tree(simple = false)
public class User {
	
	@TreeElement(type = TreeElement.ID)
	private Long id;
	
	private String username;
	
	private String pwd;
	
	private String password;
	
	@TreeElement(type = TreeElement.TITLE)
	private String title;
	
	@TreeElement(type = TreeElement.OTHER)
	private String sex;
	
	private String sexname;
	
	@TreeElement(type = TreeElement.PARENT)
	private String department;
	
	private String departmentname;
	
	private String post;
	
	private String postname;
	
	private String telephone;
	
	private String address;
	
	private String cellphone;
	
	private String email;
	
	private Integer flag;
	
	private String flagmeaning;
	
	private Integer online;
	
	private String onlinestate;

	private String remark;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getOnlinestate() {
		return onlinestate;
	}

	public void setOnlinestate(String onlinestate) {
		this.onlinestate = onlinestate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
