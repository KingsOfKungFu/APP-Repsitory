package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	int delVersionByInfoId(@Param("id") Integer id);
	
	/**
	 * ����appinfoid��ѯ�汾�б�
	 * @param id
	 * @return
	 */
	//List<AppVersion> getVersionByAppInfoId(@Param("id") Integer id);
	
	/**
	 * ���Ӱ汾
	 * @param appVersion
	 * @return
	 */
	int addVersion(AppVersion appVersion);
	
	/**
	 * ����appId��ѯ�汾�б�
	 * @param appinfoid
	 * @return
	 */
	List<AppVersion> getAppVersionByInfoid(@Param("appinfoid") Integer appinfoid);

	List<AppVersion> getVersionList();

	

}
