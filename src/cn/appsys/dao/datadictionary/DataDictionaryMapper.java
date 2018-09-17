package cn.appsys.dao.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
	/**
	 * �������ͱ�Ų�ѯ�ֵ���Ϣ
	 * @param typeCode
	 * @return
	 */
	List<DataDictionary> getDataDictionaryListByTypeCode(@Param("typeCode")String typeCode);

}
