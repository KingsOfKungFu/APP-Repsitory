package cn.appsys.service.datadictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService {
	
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	
	
	@Override
	public List<DataDictionary> getDataDictionaryListByTypeCode(String typeCode) {
		
		return dataDictionaryMapper.getDataDictionaryListByTypeCode(typeCode);
	}
	
}
