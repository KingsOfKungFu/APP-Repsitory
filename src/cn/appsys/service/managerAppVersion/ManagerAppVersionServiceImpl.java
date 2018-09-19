package cn.appsys.service.managerAppVersion;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.managerAppVersion.ManagerAppVersionMapper;
import cn.appsys.pojo.AppVersion;

@Service
@Transactional
public class ManagerAppVersionServiceImpl implements ManagerAppVersionService {

	@Resource
	private ManagerAppVersionMapper managerAppVersionMapper;

	@Override
	public AppVersion getAppVersionById(String versionId) {
		return managerAppVersionMapper.getAppVersionById(versionId);
	}
}
