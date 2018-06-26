package org.frame.business.service.system.parameter.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.parameter.IParameterDao;
import org.frame.business.model.system.Parameter;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.parameter.IParameterService;
import org.frame.business.system.Config;
import org.frame.common.lang.StringHelper;
import org.frame.common.util.Date;
import org.frame.common.webservice.client.Client;
import org.frame.common.xml.XMLBinding;
import org.frame.repository.sql.model.Page;
import org.frame.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Repository("parameterService")
@Scope("singleton")
public class ParameterService extends BaseService implements IParameterService {
	
	private Log logger = LogFactory.getLog(ParameterService.class);
	
	@Resource
	@Autowired
	IParameterDao parameterDao;
	
	@Override
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			for (String id : ids.split(",")) {
				try {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(parameterDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public Long create(Parameter parameter) {
		return parameterDao.insert(parameter);
	}
	
	@Override
	public Long edit(Parameter parameter) {
		Integer result = parameterDao.update(parameter);
		return result > 0 ? parameter.getId() : result.longValue();
	}
	
	@Override
	public Long modify(Parameter parameter) {
		if (StringHelper.isNull(String.valueOf(parameter.getId())))
			return this.create(parameter);
		else
			return this.edit(parameter);
	}
	
	@Override
	public Page pagination(String title, Integer flag, Page page) {
		return parameterDao.pagination(title, flag, page);
	}

	@Override
	public List<Parameter> query(String title, Integer flag) {
		return parameterDao.select(title, flag);
	}

	@Override
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			List<Map<String, Object>> parameter = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			for (String id : ids.split(",")) {
				try {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));

					parameter.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(parameterDao.delete(parameter).toArray());
		}

		return result;
	}

	@Override
	public Parameter search(Long id) {
		return parameterDao.find(id);
	}

	@Override
	public boolean synchronization() {
		boolean result = false;
		
		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
			File out;
			String paths = config.get(IBusinessConstant.SYNCHRONIZATION_OUT);
			if (paths != null && !"".equals(paths)) {
				XMLBinding xmlBinding = new XMLBinding();
				String suffix = "_" + new Date().date2String("yyyyMMddHHmmss");
				for(String path : paths.split(",")) {
					if (path != null && !"".equals(path)) {
						out = new File(path);
						if (!out.exists()) out.mkdirs();
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_parameter" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportParameter", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importParameter(String.valueOf(info[0]));
				} else {
					System.err.println("no parameter info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
