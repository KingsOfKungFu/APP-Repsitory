package cn.appsys.service.appcategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
@Service
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {
	
	@Resource
	private AppCategoryMapper appCategoryMapper;
	
	@Override
	public List<AppCategory> getAppCategoryListByParentId(Integer parentId) {
		
		return appCategoryMapper.getAppCategoryListByParentId(parentId);
	}

}
