package org.frame.business.service.system.dictionary.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.dictionary.IDictionaryDao;
import org.frame.business.model.system.Dictionary;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.common.util.Date;
import org.frame.common.webservice.client.Client;
import org.frame.common.xml.XMLBinding;
import org.frame.repository.sql.model.Page;
import org.frame.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Repository("dictionaryService")
@Scope("singleton")
public class DictionaryService extends BaseService implements IDictionaryService{
	
	private Log logger = LogFactory.getLog(DictionaryService.class);
	
	@Resource
	@Autowired
	IDictionaryDao dictionaryDao;
	
	@Override
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			List<Map<String, Object>> parameter = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			for (String id : ids.split(",")) {
				try {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));
					map.put("flag", flag);

					parameter.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(dictionaryDao.abandon(parameter).toArray());
		}

		return result;
	}
	
	@Override
	public Long create(Dictionary dictionary) {
		return dictionaryDao.insert(dictionary);
	}
	
	@Override
	public Long edit(Dictionary dictionary) {
		Integer result = dictionaryDao.update(dictionary);
		return result > 0 ? dictionary.getId() : result.longValue();
	}
	
	public List<Dictionary> group() {
		return dictionaryDao.group();
	}
	
	@Override
	public Long modify(Dictionary dictionary) {
		if (StringHelper.isNull(String.valueOf(dictionary.getId())))
			return this.create(dictionary);
		else
			return this.edit(dictionary);
	}
	
	@Override
	public Page pagination(String parentname, String sort, Integer flag, Page page) {
		return dictionaryDao.pagination(parentname, sort, flag, page);
	}
	
	@Override
	public List<Dictionary> query(String sort) {
		return this.query(null, null, sort, ISymbolConstant.FLAG_AVAILABLE);
	}
	
	@Override
	public List<Dictionary> query(Long parentid, String sort) {
		return this.query(parentid, null, sort, ISymbolConstant.FLAG_AVAILABLE);
	}
	
	@Override
	public List<Dictionary> query(Long parentid, String parentname, String sort, Integer flag) {
		return dictionaryDao.select(parentid, parentname, sort, flag);
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

			result = new StringHelper().join(dictionaryDao.delete(parameter).toArray());
		}

		return result;
	}
	
	@Override
	public Dictionary search(Long id) {
		return dictionaryDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_dictionary" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportDictionary", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importDictionary(String.valueOf(info[0]));
				} else {
					System.err.println("no dictionary info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
