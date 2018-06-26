package org.frame.business.controller.system.department;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.Department;
import org.frame.business.service.system.department.IDepartmentService;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.common.constant.ISymbolConstant;
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
public class DepartmentController extends BaseController {
	
	private Log logger = LogFactory.getLog(DepartmentController.class);
	
	@Resource
	@Autowired
	private IDepartmentService departmentService;
	
	@Resource
	@Autowired
	private IDictionaryService dictionaryService;

	@RequestMapping(value = "/system/department/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("department controller abandon access...");
		
		String ids = request.getParameter("ids");
		String sign = request.getParameter("flag");
		try {
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, departmentService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/department/list.jspx";
	}
	
	@RequestMapping(value = "/system/department/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("department controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			Department department = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				department = departmentService.search(id);
			} else {
				department = new Department();
				department.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("department", department);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/department/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("department controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String parent = request.getParameter("query_parentid");
		String sign = request.getParameter("query_flag");
		
		String parentname = request.getParameter("query_parentname");
		String title = request.getParameter("query_title");
		
		try {
			Integer flag;
			Long parentid;
			
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
			
			if (!StringHelper.isNull(parent)) {
				try {
					parentid = Long.parseLong(parent);
				} catch (NumberFormatException e) {
					parentid = null;
					logger.error("parent id invalid. parent id: " + parent);
				}
			} else {
				parentid = null;
			}
			
			if (StringHelper.isNull(parentname)) {
				parentname = null;
			}
			
			if (StringHelper.isNull(title)) {
				title = null;
			}
			
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_parentid", parentid);
			model.addAttribute("query_parentname", parentname);
			model.addAttribute("query_title", title);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, departmentService.pagination(parentid, parentname, title, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/department/list.jsp";
	}
	
	@RequestMapping(value = "/system/department/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("department controller load access...");
		
		String result = "[]";
		
		String parent = request.getParameter("id");
		
		try {
			Long parentid;
			
			if (!StringHelper.isNull(parent)) {
				parentid = Long.parseLong(parent);
				if (parentid == -1) parentid = null;
			} else {
				parentid = null;
			}
			
			List<Department> data = departmentService.query(parentid, null, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new TreeDataFormat().format(data);
		} catch (NumberFormatException e) {
			logger.error("parent id invalid: " + parent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@RequestMapping(value = "/system/department/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("department controller modify access...");
		try {
			Department department = (Department) super.request2bean(request, Department.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, departmentService.modify(department));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/department/list.jspx";
	}
	
	@RequestMapping(value = "/system/department/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("department controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, departmentService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/department/list.jspx";
	}
	
	@RequestMapping(value = "/system/department/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("department controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = departmentService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}

}
