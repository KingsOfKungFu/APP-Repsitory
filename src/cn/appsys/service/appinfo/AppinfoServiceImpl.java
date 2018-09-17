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
}
