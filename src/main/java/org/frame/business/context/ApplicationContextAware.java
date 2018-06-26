/**
 * This class can not work normally when be sealed in a jar.
 */
package org.frame.business.context;

import org.frame.web.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextAware extends ApplicationContext implements org.springframework.context.ApplicationContextAware {
	
	private static org.springframework.context.ApplicationContext applicationContext = null;
	
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
		ApplicationContextAware.applicationContext = applicationContext;
	}
	
	public static org.springframework.context.ApplicationContext getContext() {
		return applicationContext;
	}

}
