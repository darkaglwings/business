package org.frame.business.dao.system.access.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.access.IAccessDao;
import org.frame.common.algorithm.Hash;
import org.frame.common.algorithm.Hash.HASH_TYPE;
import org.frame.common.constant.ISymbolConstant;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Repository("accessDao")
@Scope("singleton")
public class AccessDao extends BaseDao implements IAccessDao {
	
	private Log logger = LogFactory.getLog(AccessDao.class);

	@SuppressWarnings("unchecked")
	public Long login(String username, String password) {
		Long result = null;

		List<?> data = select(SQL_LOGIN_TEMPLET, new Object[]{ISymbolConstant.FLAG_AVAILABLE, username});
		if (data == null || data.size() < 1) {
			result = -1l;
		} else if (data != null && data.size() > 1) {
			result = -3l;
		} else if (data != null && data.size() == 1) {
			Map<String, Object> map = (Map<String, Object>) data.get(0);
			if (password == null) {
				if (map.get("password") == null) {
					result = (Long) ((Map<String, Object>) data.get(0)).get("id");
				} else {
					result = -2l;
				}
			} else {
				if (new Hash().hash(password, HASH_TYPE.MD5).equals((String) map.get("password"))) {
					result = (Long) ((Map<String, Object>) data.get(0)).get("id");
				} else {
					result = -2l;
				}
			}
		}

		logger.info("login result: " + result);
		
		return result;
	}
	
	@Override
	public Integer logout(Long id) {
		return update(SQL_ONLINE_TEMPLET, new Object[]{0, id});
	}
	
}
