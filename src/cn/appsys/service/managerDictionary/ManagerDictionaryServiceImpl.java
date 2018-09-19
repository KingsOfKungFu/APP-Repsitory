package cn.appsys.service.managerDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.managerDictionary.ManagerDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service
public class ManagerDictionaryServiceImpl implements ManagerDictionaryService {

	@Resource
	private ManagerDictionaryMapper managerDictionaryMapper;
	@Override
	public List<DataDictionary> getDictionaryList(String typeCode) {
		return managerDictionaryMapper.getDictionaryList(typeCode);
	}

}
