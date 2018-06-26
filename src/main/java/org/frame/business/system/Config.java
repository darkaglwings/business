/**
 * Config contains method to get system configuration
 */
package org.frame.business.system;

import java.util.List;

import org.frame.business.constant.IBusinessConstant;
import org.frame.business.context.ApplicationContextAware;
import org.frame.business.model.system.Parameter;
import org.frame.business.service.system.parameter.impl.ParameterService;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;


public class Config {
	
	private String path;
	
	public Config(String path) {
		this.path = path;
	}
	
	/**
	 * to get system configuration
	 * 
	 * @param key key for configuration
	 * 
	 * @return value of configuration
	 */
	public String get(String key) {
		if (StringHelper.isNull(path)) path = IBusinessConstant.DEFAULT_CONFIG_PROPERTIES;
		String result = new org.frame.web.profile.Config(path).get(key);
		try {
			if (StringHelper.isNull(result)) {
				ParameterService parameterService = (ParameterService) ApplicationContextAware.getContext().getBean("parameterService");
				List<Parameter> data = parameterService.query(key, ISymbolConstant.FLAG_AVAILABLE);
				if (data != null && data.size() > 0) {
					result = data.get(0).getCode();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//if (!StringUtil.isNull(result)) result = result.toLowerCase();
		
		return result;
	}
	
	/*public static void main(String[] args) {
		System.out.println(new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get("system.database"));
	}*/

}