package cn.appsys.service.datadictionary;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	/**
	 * �������ͱ�Ų�ѯ�ֵ���Ϣ
	 * @param string
	 */
	List<DataDictionary> getDataDictionaryListByTypeCode(String string);

}
