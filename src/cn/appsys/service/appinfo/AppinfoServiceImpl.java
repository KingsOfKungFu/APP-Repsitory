package cn.appsys.service.appinfo;

import java.util.List;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.internal.txw2.Document;
import com.sun.xml.internal.txw2.TypedXmlWriter;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.util.PageBean;
@Service
@Transactional
public class AppinfoServiceImpl implements AppinfoService{
	
	@Resource
	private AppInfoMapper appInfoMapper;
	
	
	@Override
	public boolean updataAppInfo(AppInfo appInfo) {
		if(appInfoMapper.updataAppInfo(appInfo) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean dealpp(Integer id) {
		if(appInfoMapper.dealpp(id) > 0) {
			return true;
		}
		return false;
	}

	
	@Override
	public AppInfo getAppInfoById(Integer appinfoid) {
		return appInfoMapper.getAppInfoById(appinfoid);
	}
	
	
	@Override
	public void getAppInfoList(PageBean<AppInfo> pageBean,QueryAppInfoVO queryAppInfoVO) {

		int totalCount = appInfoMapper.getAppInfoCount(queryAppInfoVO);
		pageBean.setTotalCount(totalCount);
		
		queryAppInfoVO.setStartIndex(pageBean.getStartIndex());
		queryAppInfoVO.setPageSize(pageBean.getPageSize());
		List<AppInfo> result = appInfoMapper.getAppInfoList(queryAppInfoVO);
		pageBean.setResult(result);
	}


	@Override
	public boolean appInfoModify(AppInfo appInfo) {
		boolean flag = false;
		if (appInfoMapper.appInfoModify(appInfo) > 0) {
			flag = true;
		}
		return flag;
	}


	@Override
	public AppInfo getAppInfoByAPKName(String APKName) {
		return appInfoMapper.getAppInfoByAPKName(APKName);
	}


	@Override
	public boolean getAppInfoAdd(AppInfo appInfo) {
		boolean result = false;
		if (appInfoMapper.getAppInfoAdd(appInfo) > 0) {
			result = true;
		}
		return result;
	}


	@Override
	public boolean deleteLogoPicPath(Integer id) {
		boolean result = false;
		if(appInfoMapper.updateLogoPicPath(id) > 0) {
			result = true;
		}
		return result;
	}

}
