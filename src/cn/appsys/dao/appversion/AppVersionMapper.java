package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	
	/**
	 * 更新最新版本
	 * @param appVersion
	 * @return
	 */
	int saveappVersion(AppVersion appVersion);
	
	/**
	 * ����VsersionId��ѯ���°汾��Ϣ
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
	 * ���Ӱ汾
	 * @param appVersion
	 * @return
	 */
	int addVersion(AppVersion appVersion);

	/**
	 * ����appinfoid��ѯ�汾�б�
	 * @param appinfoid
	 * @return
	 */
	List<AppVersion> getAppVersionByInfoid(@Param("appinfoid")Integer appinfoid);

	

}
