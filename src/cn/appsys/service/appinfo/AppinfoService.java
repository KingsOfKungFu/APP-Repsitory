package cn.appsys.service.appinfo;

import java.util.List;


import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.util.PageBean;

public interface AppinfoService {
	
	/**
	 * 根据infoid删除app
	 * @param id
	 * @return
	 */
	boolean dealpp(Integer id);
	
	/**
	 * 根据id查询app信息
	 * @param appinfoid
	 * @return
	 */
	AppInfo getAppInfoById(Integer appinfoid);
	
	/**
	 * 根据条件查询app列表
	 * @param pageBean
	 * @param queryAppInfoV
	 */
	void getAppInfoList(PageBean<AppInfo> pageBean, QueryAppInfoVO queryAppInfoV);

	

}
