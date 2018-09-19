package cn.appsys.service.appversion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
@Transactional
@Service
public class AppVersionServiceImpl implements AppVersionService {
	
	@Resource
	private AppVersionMapper appVersionMapper;
	
	
	@Override
	public boolean saveappVersion(AppVersion appVersion) {
		if(appVersionMapper.saveappVersion(appVersion) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public AppVersion getAppVersionByVersionId(Integer vid) {
		
		return appVersionMapper.getAppVersionByVersionId(vid);
	}
	
	@Override
	public boolean delVersionByInfoId(Integer id) {
		if(appVersionMapper.delVersionByInfoId(id) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<AppVersion> getAppVersionByInfoid(Integer appinfoid) {
		
		return appVersionMapper.getAppVersionByInfoid(appinfoid);
	}

	@Override
	public boolean addVersion(AppVersion appVersion) {
		if(appVersionMapper.addVersion(appVersion) > 0) {
			return true;
		}
		return false;
	}

	

}
