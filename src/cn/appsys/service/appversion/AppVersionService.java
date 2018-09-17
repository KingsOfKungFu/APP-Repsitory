package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	/**
	 * 根据infoid删除所有版本
	 * @param id
	 * @return
	 */
	boolean delVersionByInfoId(Integer id);
	
	/**
	 * 根据appinfoid查询版本列表
	 * @param id
	 * @return
	 */
	List<AppVersion> getVersionByAppInfoId(Integer id);
	
	/**
	 * 增加版本
	 * @param appVersion
	 */
	boolean addVersion(AppVersion appVersion);
	
	/**
	 * 根据appId查询版本列表
	 * @param appinfoid
	 * @return
	 */
	List<AppVersion> getAppVersionByInfoid(Integer appinfoid);

	

}
