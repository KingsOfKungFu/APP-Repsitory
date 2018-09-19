package cn.appsys.service.managerCategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.ManagerCategory.ManagerCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Service
@Transactional
public class ManagerCategoryServiceImpl implements ManagerCategoryService {

	@Resource
	private ManagerCategoryMapper managerCategoryMapper;

	@Override
	public List<AppCategory> getAppCategoryLevelByParentId(Integer parentId) {
		return managerCategoryMapper.getAppCategoryLevelByParentId(parentId);
	}
}
