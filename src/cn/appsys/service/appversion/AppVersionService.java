package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	
	/**
	 * 保存最新版本修改
	 * @param appVersion
	 * @return
	 */
	boolean saveappVersion(AppVersion appVersion);
	
	/**
	 * 根据VsersionId查询最新版本信息
	 * @param vid
	 * @return
	 */
	 AppVersion getAppVersionByVersionId(Integer vid);

	
	/**
	 * 根据infoid删除所有版本
	 * @param id
	 * @return
	 */
	boolean delVersionByInfoId(Integer id);
	
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
