package org.frame.business.model.system;

import org.frame.web.annotation.tree.Tree;
import org.frame.web.annotation.tree.TreeElement;

@Tree(simple = false)
public class Post {
	
	@TreeElement(type = TreeElement.ID)
	private Long id;
	
	@TreeElement(type = TreeElement.PARENT)
	private Long parentid;
	
	private String parentname;
	
	@TreeElement(type = TreeElement.OTHER)
	private Long rank;
	
	@TreeElement(type = TreeElement.HINT)
	private String fullname;
	
	@TreeElement(type = TreeElement.TITLE)
	private String title;
	
	private Integer flag;
	
	private String flagmeaning;
	
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
