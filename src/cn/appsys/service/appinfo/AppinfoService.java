package cn.appsys.service.appinfo;

import java.util.List;


import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.util.PageBean;

public interface AppinfoService {
	
	/**
	 * 更新appinfo信息
	 * @param appInfo
	 * @return
	 */
	boolean updataAppInfo(AppInfo appInfo);
	
	/**
	 * ����infoidɾ��app
	 * @param id
	 * @return
	 */
	boolean dealpp(Integer id);
	
	/**
	 * ����id��ѯapp��Ϣ
	 * @param appinfoid
	 * @return
	 */
	AppInfo getAppInfoById(Integer appinfoid);
	
	/**
	 * ����������ѯapp�б�
	 * @param pageBean
	 * @param queryAppInfoV
	 */
	void getAppInfoList(PageBean<AppInfo> pageBean, QueryAppInfoVO queryAppInfoV);

	
	boolean appInfoModify(AppInfo appInfo);

	AppInfo getAppInfoByAPKName(String aPKName);

	boolean getAppInfoAdd(AppInfo appInfo);

	boolean deleteLogoPicPath(Integer id);


}
