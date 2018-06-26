package org.frame.business.controller.system.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.User;
import org.frame.business.model.system.UserDepartment;
import org.frame.business.model.system.UserPost;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.user.IUserService;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.json.JSON;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.constant.IWebConstant;
import org.frame.web.controller.BaseController;
import org.frame.web.tree.TreeDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController extends BaseController {
	
	private Log logger = LogFactory.getLog(UserController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IUserService userService;
	
	@RequestMapping(value = "/system/user/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("user controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/user/list.jspx";
	}
	
	@RequestMapping(value = "/system/user/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("user controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			User user = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				user = userService.search(id);
			} else {
				user = new User();
				user.setFlag(ISymbolConstant.FLAG_AVAILABLE);
				user.setOnline(ISymbolConstant.FLAG_INAVAILABLE);
			}
			
			model.addAttribute("sex", dictionaryService.query("SEX"));
			model.addAttribute("user", user);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}
	
	@RequestMapping(value = "/system/user/department.jspx")
	public @ResponseBody String department(HttpServletRequest request, Model model) {
		logger.info("user controller department access...");
		
		String result = "[]", d = null;
		
		String departmentid = request.getParameter("departmentid");
		String id = request.getParameter("userid");
		
		try {
			Long userid;
			if (!StringHelper.isNull(id)) {
				userid = Long.parseLong(id);
			} else {
				userid = null;
			}
			
			String[] departments;
			List<UserDepartment> data = new ArrayList<UserDepartment>();
			List<User> users = userService.query(userid, departmentid, null, ISymbolConstant.FLAG_AVAILABLE);
			for (User user : users) {
				d = user.getDepartment();
				if (user.getDepartment() != null) {
					departments = user.getDepartment().split(",");
				} else {
					departments = new String[]{};
				}
				
				for (String department : departments) {
					UserDepartment userDepartment = new UserDepartment();
					userDepartment.setUser(user.getId());
					userDepartment.setDepartment(Long.parseLong(department));
					
					data.add(userDepartment);
				}
			}
			
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("user departments invalid: " + d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@RequestMapping(value = "/system/user/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("user controller list access...");
		
		String sign = request.getParameter("query_flag");
		
		String department = request.getParameter("query_department");
		String post = request.getParameter("query_post");
		String sex = request.getParameter("query_sex");
		String title = request.getParameter("query_title");
		String username = request.getParameter("query_username");
		
		try {
			Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

			Integer flag;
			
			if (!StringHelper.isNull(sign)) {
				try {
					flag = Integer.parseInt(sign);
				} catch (NumberFormatException e) {
					flag = null;
					logger.error("flag invalid. flag: " + sign);
				}
				
				if (flag != null && flag == -1) flag = null;
			} else {
				flag = null;
			}
			
			if (StringHelper.isNull(department)) {
				department = null;
			}
			
			if (StringHelper.isNull(post)) {
				post = null;
			}
			
			if (!StringHelper.isNull(sex)) {
				if (sex.equals("-1")) sex = null;
			} else {
				sex = null;
			}
			
			if (StringHelper.isNull(title)) {
				title = null;
			}
			
			if (StringHelper.isNull(username)) {
				username = null;
			}
			
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_department", department);
			model.addAttribute("query_post", post);
			model.addAttribute("query_sex", sex);
			model.addAttribute("query_title", title);
			model.addAttribute("query_username", username);
			model.addAttribute("sex", dictionaryService.query("SEX"));
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, userService.pagination(title, username, sex, department, post, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/user/list.jsp";
	}

	@RequestMapping(value = "/system/user/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("user controller load access...");
		
		String result = "[]";
		
		String parentid = request.getParameter("id");
		
		try {
			if (StringHelper.isNull(parentid)) {
				parentid = null;
			}
			
			List<User> data = userService.query(null, null, null, parentid, null, ISymbolConstant.FLAG_AVAILABLE);
			if (data != null) {
				result = new TreeDataFormat().format(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/user/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("user controller modify access...");
		try {
			User user = (User) super.request2bean(request, User.class);
			if ("".equals(user.getPassword())) {
				user.setPassword(null);
			}
			
			model.addAttribute(ISymbolConstant.INFO_RESULT, userService.modify(user));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/user/list.jspx";
	}
	
	@RequestMapping(value = "/system/user/post.jspx")
	public @ResponseBody String post(HttpServletRequest request, Model model) {
		logger.info("user controller post access...");
		
		String result = "[]", p = null;
		
		String postid = request.getParameter("departmentid");
		String id = request.getParameter("userid");
		
		try {
			Long userid;
			if (!StringHelper.isNull(id)) {
				userid = Long.parseLong(id);
			} else {
				userid = null;
			}
			
			String[] posts;
			List<UserPost> data = new ArrayList<UserPost>();
			List<User> users = userService.query(userid, null, postid, ISymbolConstant.FLAG_AVAILABLE);
			for (User user : users) {
				p = user.getPost();
				if (user.getPost() != null) {
					posts = user.getPost().split(",");
				} else {
					posts = new String[]{};
				}
				
				for (String post : posts) {
					UserPost userPost = new UserPost();
					userPost.setUser(user.getId());
					userPost.setPost(Long.parseLong(post));
					
					data.add(userPost);
				}
			}
			
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("user posts invalid: " + p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/user/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("user controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_RESULT, userService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/user/list.jspx";
	}
	
	@RequestMapping(value = "/system/user/validate.jspx")
	public @ResponseBody String validate(HttpServletRequest request, Model model) {
		logger.info("user controller validate access...");
		
		String result;
		
		try {
			String username = request.getParameter("username");
			
			if (!StringHelper.isNull(username)) {
				List<User> data = userService.query(null, username, null, null, null, null);
				if (data != null && data.size() > 0) {
					result = "1";
				} else {
					result = "";
				}
			} else {
				result = "";
			}
		} catch (Exception e) {
			result = "2";
			e.printStackTrace();
		}
		
		return result;
	}

	@RequestMapping(value = "/system/user/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("user controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = userService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
