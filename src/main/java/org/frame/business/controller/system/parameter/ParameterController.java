package org.frame.business.controller.system.parameter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.Parameter;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.parameter.IParameterService;
import org.frame.common.constant.ISymbolConstant;
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
public class ParameterController extends BaseController {
	
	private Log logger = LogFactory.getLog(ParameterController.class);
	
	@Resource
	@Autowired
	IParameterService parameterService;

	@Resource
	@Autowired
	private IDictionaryService dictionaryService;
	
	@RequestMapping(value = "/system/parameter/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("parameter controller abandon access...");
		
		String sign = request.getParameter("flag");
		String ids = request.getParameter("ids");
		
		try {
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, parameterService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/parameter/list.jspx";
	}
	
	@RequestMapping(value = "/system/parameter/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("parameter controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id;
			Parameter parameter = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				parameter = parameterService.search(id);
			} else {
				parameter = new Parameter();
				parameter.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("parameter", parameter);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/parameter/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("parameter controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String sign = request.getParameter("query_flag");
		String title = request.getParameter("query_title");
		
		try {
			Integer flag;
			
			if (!StringHelper.isNull(sign)) {
				try {
					flag = Integer.parseInt(sign);
				} catch (NumberFormatException e) {
					flag = null;
					logger.error("flag invalid: " + sign);
				}

				if (flag != null && flag == -1) flag = null;
			} else {
				flag = null;
			}
			
			if (StringHelper.isNull(title)) {
				title = null;
			}
			
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_title", title);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, parameterService.pagination(title, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/parameter/list.jsp";
	}

	@RequestMapping(value = "/system/parameter/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("parameter modify list access...");
		
		try {
			Parameter parameter = (Parameter) super.request2bean(request, Parameter.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, parameterService.modify(parameter));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/parameter/list.jspx";
	}
	
	@RequestMapping(value = "/system/parameter/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("parameter remove list access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, parameterService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/parameter/list.jspx";
	}

	@RequestMapping(value = "/system/parameter/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("parameter controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = parameterService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
