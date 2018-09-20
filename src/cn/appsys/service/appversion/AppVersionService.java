package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	
	/**
	 * 更新最新版本
	 * @param appVersion
	 * @return
	 */
	boolean saveappVersion(AppVersion appVersion);
	
	/**
	 * ����VsersionId��ѯ���°汾��Ϣ
	 * @param vid
	 * @return
	 */
	 AppVersion getAppVersionByVersionId(Integer vid);

	
	/**
	 * ����infoidɾ�����а汾
	 * @param id
	 * @return
	 */
	boolean delVersionByInfoId(Integer id);
	
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


	

}
