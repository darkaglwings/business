package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.Menu;

@XmlRootElement
public class Menus {
	
	private List<Menu> menus;

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
}
