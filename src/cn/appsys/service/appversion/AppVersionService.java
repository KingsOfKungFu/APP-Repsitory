package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	
	/**
<<<<<<< HEAD
=======
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
>>>>>>> branch 'develop' of https://github.com/KingsOfKungFu/APP-Repsitory.git
	 * ����infoidɾ�����а汾
	 * @param id
	 * @return
	 */
	boolean delVersionByInfoId(Integer id);
	
	/**
<<<<<<< HEAD
	 * ����appinfoid��ѯ�汾�б�
	 * @param id
	 * @return
	 */
	//List<AppVersion> getVersionByAppInfoId(Integer id);
	
	/**
=======
>>>>>>> branch 'develop' of https://github.com/KingsOfKungFu/APP-Repsitory.git
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
