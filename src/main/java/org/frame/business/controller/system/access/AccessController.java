package org.frame.business.controller.system.access;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.constant.IMessageConstant;
import org.frame.business.model.system.User;
import org.frame.business.model.system.access.Account;
import org.frame.business.service.system.access.IAccessService;
import org.frame.business.service.system.user.IUserService;
import org.frame.business.system.Config;
import org.frame.common.algorithm.Hash;
import org.frame.common.algorithm.Hash.HASH_TYPE;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.common.network.NetState;
import org.frame.common.path.Path;
import org.frame.web.message.Publisher;
import org.frame.web.message.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AccessController {
	
	private Log logger = LogFactory.getLog(AccessController.class);
	
	@Resource
	@Autowired
	IAccessService accessService;
	
	@Resource
	@Autowired
	IUserService userService;
	
	@Resource
	@Autowired
	Publisher publisher;
	
	@Resource
	@Autowired
	Receiver receive;

	@RequestMapping(value = "/system/access/chgpwd.jspx")
	public @ResponseBody String chgpwd(HttpServletRequest request, Model model) {
		logger.info("access controller change password start...");
		
		String result = ISymbolConstant.FLAG_FALSE;
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		try {
			User user =  userService.search(Long.parseLong(id));
			if (user != null) {
				String allowNull = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.LOGIN_PASSWORD_ALLOWNULL).toLowerCase();
				if ("true".equals(allowNull)) {
					if (StringHelper.isNull(password)) password = null;
				} else {
					if (StringHelper.isNull(password)) {
						logger.error("password can not be null.");
						throw new Exception("password can not be null.");
					} else {
						password = new Hash().hash(user.getPassword(), HASH_TYPE.MD5);
					}
				}
				
				user.setPassword(password);
				userService.edit(user);
				
				result = ISymbolConstant.FLAG_TRUE;
			}
		} catch (NumberFormatException e) {
			logger.error("id invalid: " + id);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("access controller change password done...");
		
		return result;
	}
	
	@RequestMapping(value = {"/system/access/login.jspx"})
	public String login(HttpServletRequest request, Model model) {
		logger.info("access controller login start...");
		
		String result = "/error/error.jsp";
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			logger.info("username: " + username);
			logger.info("password: " + password);
			
			String allowNull = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.LOGIN_PASSWORD_ALLOWNULL).toLowerCase();
			
			if (StringHelper.isNull(username)) {
				logger.error("username invalid: " + username);
			} else if (!"true".equals(allowNull) && StringHelper.isNull(password)) {
				logger.error("password invalid: " + password);
			} else {
				if ("".equals(password)) password = null;
				Map<String, Object> map = accessService.login(username, password);
				if (map != null) {
					Long code = (Long) map.get("code");
					
					logger.info(username + " login result: " + code);
					
					if (-1 == code) {
						model.addAttribute("username", username);
						model.addAttribute("password", password);
						model.addAttribute(ISymbolConstant.INFO_CODE, code);
						model.addAttribute(ISymbolConstant.INFO_RESULT, IMessageConstant.LOGIN_FAILURE_NO_USER);
						
						result = "login.jsp";
					} else if (-2 == code) {
						model.addAttribute("username", username);
						model.addAttribute("password", password);
						model.addAttribute(ISymbolConstant.INFO_CODE, code);
						model.addAttribute(ISymbolConstant.INFO_RESULT, IMessageConstant.LOGIN_FAILURE_WRONG_PASSWORD);
						
						result = "login.jsp";
					} else if (-3 == code) {
						model.addAttribute("username", username);
						model.addAttribute("password", password);
						model.addAttribute(ISymbolConstant.INFO_CODE, code);
						model.addAttribute(ISymbolConstant.INFO_RESULT, IMessageConstant.LOGIN_FAILURE_TOO_MANY);
						
						result = "login.jsp";
					} else {
						Account account = (Account) map.get("account");
						
						if (account != null) {
							account.setIp(new NetState().ipFromRequest(request));
								
							if (account.getUser() != null)
								account.getUser().setPwd(password);
							
							if (account.getMenus() != null && account.getMenus().size() > 0)
								result = account.getMenus().get(0).getUri();
						}
						
						request.getSession().setAttribute(IBusinessConstant.SYSTEM_ACCOUNT, account);
						
						model.addAttribute(ISymbolConstant.INFO_CODE, code);
						model.addAttribute(ISymbolConstant.INFO_RESULT, IMessageConstant.LOGIN_SUCCESS);
						
						result = "/system/welcome.jsp";
						
						/*Map<String, Object> message = new HashMap<String, Object>();
						message.put("session", request.getSession().getId());
						message.put(IBusinessConstant.SYSTEM_ACCOUNT, new JSON().toJsonString(account));
						publisher.send(message);
						
						Message info = receive.reveive();
						try {
							System.out.println(((MapMessage) info).getString("session"));
							System.out.println(((MapMessage) info).getString(IBusinessConstant.SYSTEM_ACCOUNT));
						} catch (JMSException e) {
							e.printStackTrace();
						}*/
					}
				} else {
					model.addAttribute(ISymbolConstant.INFO_CODE, -4);
					model.addAttribute(ISymbolConstant.INFO_RESULT, IMessageConstant.LOGIN_FAILURE_UNKNOWN);
					
					result = "login.jsp";
				}
			}
			
			request.getSession().setAttribute(IBusinessConstant.SYSTEM_PATH, new Path().web_path(request));
			request.getSession().setAttribute(IBusinessConstant.SYSTEM_URL, new Path().web_url(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("access controller login done...");
		
		//return "redirect:" + result;
		return result;
	}
	
	@RequestMapping(value = "/system/access/logout.jspx")
	public String logout(HttpServletRequest request) {
		logger.info("access controller logout start...");
		this.nullify(request);
		logger.info("access controller logout done...");
		
		//return "redirect:login.jsp";
		return "login.jsp";
	}
	
	@RequestMapping(value = "/system/access/nullify.jspx")
	public @ResponseBody String nullify(HttpServletRequest request) {
		logger.info("access controller nullify start...");
		
		String result = "false";
		
		try {
			Account account = (Account) request.getSession().getAttribute(IBusinessConstant.SYSTEM_ACCOUNT);
			if (account != null && account.getUser() != null && account.getUser().getId() != null) accessService.logout(account);
			request.getSession().removeAttribute(IBusinessConstant.SYSTEM_ACCOUNT);
			
			result = "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("access controller nullify done...");
		
		return result;
	}
	
	@RequestMapping(value = "/system/access/password.jspx")
	public String password(HttpServletRequest request, Model model) {
		logger.info("access controller password start...");
		
		try {
			Account account = (Account) request.getSession().getAttribute(IBusinessConstant.SYSTEM_ACCOUNT);
			if (account != null && account.getUser() != null && account.getUser().getId() != null) {
				model.addAttribute("user", userService.search(account.getUser().getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("access controller password done...");
		
		return "password.jsp";
	}
	
	@RequestMapping(value = "/system/access/sso.jspx")
	public String sso(HttpServletRequest request, Model model) {
		return this.login(request, model);
	}
	
	@RequestMapping(value = "/system/access/validate.jspx")
	public @ResponseBody String validate(HttpServletRequest request, Model model) {
		logger.info("access controller validate access...");
		
		String result;
		
		String allowNull = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.LOGIN_PASSWORD_ALLOWNULL).toLowerCase();
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		try {
			if (!StringHelper.isNull(id)) {
				if (!"true".equals(allowNull)) {
					if ("".equals(password)) {
						logger.error("password can not be null.");
						return "1";
					}
				} else {
					if ("".equals(password)) password = null;
				}
				
				User user = userService.search(Long.parseLong(id));
				if (user != null) {
					if (password == null && user.getPassword() == null) {
						result = "";
					} else if (password == null && user.getPassword() != null) {
						result = "2";
					} else if (password != null && user.getPassword() == null) {
						result = "2";
					} else if (password != null && user.getPassword() != null) {
						if (user.getPassword().equals(new Hash().hash(user.getPassword(), HASH_TYPE.MD5))) {
							result = "";
						} else {
							result = "2";
						}
					} else result = "3";
				} else {
					result = "4";
				}
			} else {
				result = "";
			}
		} catch (NumberFormatException e) {
			logger.error("user id invalid: " + id);
			result = "3";
		} catch (Exception e) {
			result = "3";
			e.printStackTrace();
		}
		
		logger.info("access controller validate done...");
		
		return result;
	}

}
