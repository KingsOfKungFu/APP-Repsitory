package cn.appsys.service.datadictionary;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	/**
	 * 根据类型编号查询字典信息
	 * @param string
	 */
	List<DataDictionary> getDataDictionaryListByTypeCode(String string);

}
