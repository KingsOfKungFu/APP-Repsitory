package cn.appsys.service.managerDictionary;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface ManagerDictionaryService {

	List<DataDictionary> getDictionaryList(String typeCode);


}
