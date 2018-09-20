package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	/**
	 * ����infoidɾ�����а汾
	 * @param id
	 * @return
	 */
	boolean delVersionByInfoId(Integer id);
	
	/**
	 * ����appinfoid��ѯ�汾�б�
	 * @param id
	 * @return
	 */
	//List<AppVersion> getVersionByAppInfoId(Integer id);
	
	/**
	 * ���Ӱ汾
	 * @param appVersion
	 */
	boolean addVersion(AppVersion appVersion);
	
	/**
	 * ����appId��ѯ�汾�б�
	 * @param appinfoid
	 * @return
	 */
	List<AppVersion> getAppVersionByInfoid(Integer appinfoid);

	List<AppVersion> getVersionList();

	

}
