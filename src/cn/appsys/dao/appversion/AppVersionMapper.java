package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	
	/**
	 * 保存最新版本修改
	 * @param appVersion
	 * @return
	 */
	int saveappVersion(AppVersion appVersion);
	
	/**
	 * 根据VsersionId查询最新版本信息
	 * @param vid
	 * @return
	 */
	AppVersion getAppVersionByVersionId(Integer vid);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	int delVersionByInfoId(@Param("id") Integer id);
	
	
	/**
	 * 增加版本
	 * @param appVersion
	 * @return
	 */
	int addVersion(AppVersion appVersion);

	/**
	 * 根据appinfoid查询版本列表
	 * @param appinfoid
	 * @return
	 */
	List<AppVersion> getAppVersionByInfoid(@Param("appinfoid")Integer appinfoid);

	

}
