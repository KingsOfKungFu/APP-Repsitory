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
	 * 根据appinfoid查询版本列表
	 * @param id
	 * @return
	 */
	List<AppVersion> getVersionByAppInfoId(@Param("id") Integer id);
	
	/**
	 * 增加版本
	 * @param appVersion
	 * @return
	 */
	int addVersion(AppVersion appVersion);
	
	/**
	 * 根据appId查询版本列表
	 * @param appinfoid
	 * @return
	 */
	List<AppVersion> getAppVersionByInfoid(@Param("appinfoid") Integer appinfoid);

	

}
