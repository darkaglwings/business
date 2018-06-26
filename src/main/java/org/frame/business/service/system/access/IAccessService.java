package org.frame.business.service.system.access;

import java.util.Map;

import org.frame.business.model.system.access.Account;


public interface IAccessService {
	
	public Map<String, Object> login(String username, String password);
	
	public Long logout(Account account);
	
}
