package cn.appsys.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.QueryManagerAppInfoVO;
import cn.appsys.service.ManagerAppInfo.ManagerAppInfoService;
import cn.appsys.service.managerAppVersion.ManagerAppVersionService;
import cn.appsys.service.managerCategory.ManagerCategoryService;
import cn.appsys.service.managerDictionary.ManagerDictionaryService;
import cn.appsys.util.PageBean;

@Controller
@RequestMapping("/manager/app")
public class ManagerAppInfoController {
	
	@Resource
	private ManagerAppInfoService managerAppInfoService;
	
	@Resource
	private ManagerDictionaryService managerDictionaryService; 
	
	@Resource
	private ManagerCategoryService managerCategoryService;
	
	@Resource
	private ManagerAppVersionService managerAppVersionService;
	
	
	@RequestMapping("/checksave")
	public String doCheckSave(@ModelAttribute AppInfo appInfo) {
		boolean flag = managerAppInfoService.updataVersion(appInfo.getStatus(),appInfo.getId());
		if (flag) {
			return "redirect:/manager/app/list";
		}
		return "backend/appcheck";
	}
	
	/**
	 * 跳转到app审核的页面
	 * @param model
	 * @param appId
	 * @param versionId
	 * @return
	 */
	@RequestMapping("/check")
	public String toAppCheck(Model model,
			                 @RequestParam(value="aid",required=false)String appId,
			                 @RequestParam(value="vid",required=false)String versionId) {
		
		AppInfo appInfo = managerAppInfoService.getAppInfoById(appId);
		AppVersion appVersion = managerAppVersionService.getAppVersionById(versionId);
				
	    model.addAttribute("appInfo", appInfo);
	    model.addAttribute("appVersion", appVersion);
	    
		return "backend/appcheck";
	}
	
	/**
	 * 	url:"getclist/"+queryCategoryLevel1,//请求的url
	 * localhost:8080/APP-Repsitory/manager/app/categorylevellist.json?pid=1
	 * localhost:8080/APP-Repsitory/manager/app/categorylevellist.json?pid=2
	 * 三级联动查询
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/categorylevellist.json/{pid}")
	public String getCategoryList(@PathVariable Integer pid) {
		List<AppCategory> appCategoryList = managerCategoryService.getAppCategoryLevelByParentId(pid);
		return JSON.toJSONString(appCategoryList);
	}

	/**
	 * 加载信息列表
	 * @param model
	 * @param queryManagerAppInfoVO
	 * @return
	 */
	@RequestMapping("/list")
	public String toAppList(Model model,@ModelAttribute QueryManagerAppInfoVO queryManagerAppInfoVO) {
		
		//分页查询
		Integer currentPageNo = 1;
		if (queryManagerAppInfoVO.getPageIndex() != null) {
			currentPageNo = queryManagerAppInfoVO.getPageIndex();
		}
		Integer pageSize = 5;
		PageBean<AppInfo> pageBean = new PageBean<>();
		pageBean.setCurrentPageNo(currentPageNo);
		pageBean.setPageSize(pageSize);
		managerAppInfoService.getAppInfoList(pageBean,queryManagerAppInfoVO);
		//所属平台信息
		List<DataDictionary> flatFormList = managerDictionaryService.getDictionaryList("APP_FLATFORM");
		//一级分类
		List<AppCategory> categoryLevel1List = managerCategoryService.getAppCategoryLevelByParentId(null);
		//完善分类的回显
		
		if (queryManagerAppInfoVO.getQueryCategoryLevel1() != null) {
			List<AppCategory>  categoryLevel2List = managerCategoryService.getAppCategoryLevelByParentId(queryManagerAppInfoVO.getQueryCategoryLevel1());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if (queryManagerAppInfoVO.getQueryCategoryLevel2() != null) {
			List<AppCategory>  categoryLevel3List = managerCategoryService.getAppCategoryLevelByParentId(queryManagerAppInfoVO.getQueryCategoryLevel2());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("queryManagerAppInfoVO", queryManagerAppInfoVO);
		
		return "backend/applist";
		
	}
}
