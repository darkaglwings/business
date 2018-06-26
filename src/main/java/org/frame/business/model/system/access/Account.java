package org.frame.business.model.system.access;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.Department;
import org.frame.business.model.system.Menu;
import org.frame.business.model.system.Post;
import org.frame.business.model.system.Role;
import org.frame.business.model.system.User;


@XmlRootElement
public class Account {
	
	private String ip;
	
	private User user;
	
	private List<Department> departments;
	
	private List<Post> posts;
	
	private List<Role> roles;
	
	private List<Menu> menus;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}
