package cn.appsys.service.appinfo;

import java.util.List;


import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.util.PageBean;

public interface AppinfoService {
	
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

	

}
