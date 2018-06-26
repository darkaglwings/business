package org.frame.business.controller.system.userdepartment;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.UserDepartment;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.user.IUserService;
import org.frame.business.service.system.userdepartment.IUserDepartmentService;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.json.JSON;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.constant.IWebConstant;
import org.frame.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserDepartmentController extends BaseController {
	
	private Log logger = LogFactory.getLog(UserDepartmentController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IUserDepartmentService userDepartmentService;
	
	@Resource
	@Autowired
	IUserService userService;

	@RequestMapping(value = "/system/userdepartment/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("userDepartment controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userDepartmentService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/userdepartment/list.jspx";
	}
	
	@RequestMapping(value = "/system/userdepartment/assign.jspx")
	public @ResponseBody String assign(HttpServletRequest request, Model model) {
		logger.info("userDepartment controller edit access...");
		
		String result = "";
		
		String user = request.getParameter("userid");
		String departments = request.getParameter("departments");
		
		String department = request.getParameter("departmentid");
		String users = request.getParameter("users");
		
		Long departmentid, userid;
		try {
			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				result = userDepartmentService.assign(userid, departments, null, null);
			} else {
				logger.info("user id invalid: " + user);
			}
			
			if (!StringHelper.isNull(department)) {
				departmentid = Long.parseLong(department);
				result = userDepartmentService.assign(null, null, departmentid, users);
			} else {
				logger.info("department id invalid: " + department);
			}
		} catch (NumberFormatException e) {
			logger.info("department id or user id invalid, department id: " + department + ", user id: " + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/userdepartment/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("userDepartment controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			UserDepartment userDepartment = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				userDepartment = userDepartmentService.search(id);
			} else {
				userDepartment = new UserDepartment();
				userDepartment.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("users", userService.query(null, null, null, null, null, ISymbolConstant.FLAG_AVAILABLE));
			model.addAttribute("userDepartment", userDepartment);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/userdepartment/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("userDepartment controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String department = request.getParameter("query_departmentid");
		String user = request.getParameter("query_userid");
		String sign = request.getParameter("query_flag");
		
		String departmentname = request.getParameter("query_department");
		String username = request.getParameter("query_user");
		
		try {
			Integer flag;
			Long departmentid, userid;
			
			if (!StringHelper.isNull(department)) {
				try {
					departmentid = Long.parseLong(department);
				} catch (NumberFormatException e) {
					departmentid = null;
					logger.error("department id invalid. department id: " + department);
				}
			} else {
				departmentid = null;
			}
			
			if (StringHelper.isNull(departmentname)) {
				departmentname = null;
			}
			
			if (!StringHelper.isNull(sign)) {
				try {
					flag = Integer.parseInt(sign);
				} catch (NumberFormatException e) {
					flag = null;
					logger.error("flag invalid. flag: " + flag);
				}
				if (flag != null && flag == -1) flag = null;
			} else {
				flag = null;
			}
			
			if (!StringHelper.isNull(user)) {
				try {
					userid = Long.parseLong(user);
				} catch (NumberFormatException e) {
					userid = null;
					logger.error("user id invalid. user id: " + user);
				}
			} else {
				userid = null;
			}
			
			if (!StringHelper.isNull(username)) {
				username = null;
			}
			
			model.addAttribute("query_department",department);
			model.addAttribute("query_departmentid",departmentid);
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_user", user);
			model.addAttribute("query_userid", userid);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, userDepartmentService.pagination(userid, username, departmentid, departmentname, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/userdepartment/list.jsp";
	}

	@RequestMapping(value = "/system/userdepartment/load.jspx")
	public @ResponseBody String load(HttpServletRequest request) {
		logger.info("userDepartment controller load access...");
		
		String result = "[]";
		
		String department = request.getParameter("departmentid");
		String user = request.getParameter("userid");
		
		try {
			Long departmentid, userid;
			
			if (!StringHelper.isNull(department)) {
				departmentid = Long.parseLong(department);
				if (departmentid == -1) departmentid = null;
			} else {
				departmentid = null;
			}
			
			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				if (userid == -1) userid = null;
			} else {
				userid = null;
			}
			
			List<UserDepartment> data = userDepartmentService.query(userid, null, departmentid, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("user id or department id invalid, user id: " + user + ", department id: " + department);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/userdepartment/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("userDepartment controller modify access...");
		
		try {
			UserDepartment userDepartment = (UserDepartment) super.request2bean(request, UserDepartment.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, userDepartmentService.modify(userDepartment));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/userdepartment/list.jspx";
	}

	@RequestMapping(value = "/system/userdepartment/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("userDepartment controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userDepartmentService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/userdepartment/list.jspx";
	}

	@RequestMapping(value = "/system/userdepartment/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("userdepartment controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = userDepartmentService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
