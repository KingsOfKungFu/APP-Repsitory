 package cn.appsys.service.ManagerAppInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.mapperAppInfo.ManagerAppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.QueryManagerAppInfoVO;
import cn.appsys.util.PageBean;

@Service
public class ManagerAppInfoServiceImpl implements ManagerAppInfoService {

	@Resource
	private ManagerAppInfoMapper managerAppInfoMapper;
	
	
	@Override
	public boolean updataVersion(Integer status, Integer id) {
		return managerAppInfoMapper.updataVersion(status,id);
	}
	

	@Override
	public AppInfo getAppInfoById(String appId) {
		return managerAppInfoMapper.getAppInfoById(appId);
	}
	
	@Override
	public void getAppInfoList(PageBean<AppInfo> pageBean,QueryManagerAppInfoVO queryManagerAppInfoVO) {
		int totalCount = managerAppInfoMapper.getAppInfoCount(queryManagerAppInfoVO);
		pageBean.setTotalCount(totalCount);
		queryManagerAppInfoVO.setStartIndex(pageBean.getStartIndex());
		queryManagerAppInfoVO.setPageSize(pageBean.getPageSize());
		List<AppInfo> result = managerAppInfoMapper.getAppInfoList(queryManagerAppInfoVO);
		pageBean.setResult(result);
	}

	


}
