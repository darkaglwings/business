package org.frame.business.model.synchronization;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.frame.business.model.system.Dictionary;

@XmlRootElement
public class Dictionarys {
	
	private List<Dictionary> dictionarys;

	public List<Dictionary> getDictionarys() {
		return dictionarys;
	}

	public void setDictionarys(List<Dictionary> dictionarys) {
		this.dictionarys = dictionarys;
	}
	
}
