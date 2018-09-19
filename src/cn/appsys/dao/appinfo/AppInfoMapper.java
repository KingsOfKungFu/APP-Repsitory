package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.QueryAppInfoVO;

public interface AppInfoMapper {
	
	/**
	 * ����infoidɾ��app
	 * @param id
	 * @return
	 */
	int dealpp(@Param("id") Integer id);
	
	/**
	 * ����id��ѯapp��Ϣ
	 * @param appinfoid
	 * @return
	 */
	AppInfo getAppInfoById(@Param("appinfoid")Integer appinfoid);
	/**
	 * ����������ѯ�ܼ�¼��
	 * @param queryAppInfoV
	 * @return
	 */
	/*int getAppInfoCount(@Param("queryAppInfoVO") QueryAppInfoVO queryAppInfoVO);*/
	int getAppInfoCount(QueryAppInfoVO queryAppInfoVO);
	
	/**
	 * ����������ѯÿҳ��¼����
	 * @param queryAppInfoV
	 * @return
	 */
	/*List<AppInfo> getAppInfoList(@Param("queryAppInfoVO") QueryAppInfoVO queryAppInfoVO);*/
	List<AppInfo> getAppInfoList(QueryAppInfoVO queryAppInfoVO);

	int appInfoModify(AppInfo appInfo);

	AppInfo getAppInfoByAPKName(String aPKName);

	int getAppInfoAdd(AppInfo appInfo);

	int updateLogoPicPath(Integer id);
	
	
}
