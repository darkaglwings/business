package org.frame.business.model.system;

public class PostMenu {
	
	private Long id;
	
	private Long post;
	
	private String postname;
	
	private Long menu;
	
	private String menuname;
	
	private Integer flag;

	private String flagmeaning;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPost() {
		return post;
	}

	public void setPost(Long post) {
		this.post = post;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public Long getMenu() {
		return menu;
	}

	public void setMenu(Long menu) {
		this.menu = menu;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
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
