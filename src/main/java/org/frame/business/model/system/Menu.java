package org.frame.business.model.system;

import org.frame.web.annotation.tree.Tree;
import org.frame.web.annotation.tree.TreeElement;

@Tree(simple = false)
public class Menu {
	
	@TreeElement(type = TreeElement.ID)
	private Long id;
	
	@TreeElement(type = TreeElement.PARENT)
	private Long parentid;
	
	private String parentname;
	
	private String system;
	
	@TreeElement(type = TreeElement.TITLE)
	private String title;
	
	@TreeElement(type = TreeElement.OTHER)
	private Long rank;
	
	private String uri;
	
	private Long display;
	
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

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getDisplay() {
		return display;
	}

	public void setDisplay(Long display) {
		this.display = display;
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
