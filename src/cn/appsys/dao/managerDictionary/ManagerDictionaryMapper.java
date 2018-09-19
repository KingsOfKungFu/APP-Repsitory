package cn.appsys.dao.managerDictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface ManagerDictionaryMapper {

	List<DataDictionary> getDictionaryList(@Param("typeCode")String typeCode);

}
