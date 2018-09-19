package cn.appsys.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.sun.scenario.effect.Blend.Mode;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.service.appcategory.AppCategoryService;
import cn.appsys.service.appinfo.AppinfoService;
import cn.appsys.service.appversion.AppVersionService;
import cn.appsys.service.datadictionary.DataDictionaryService;
import cn.appsys.util.PageBean;

@Controller
@RequestMapping("/dev/app")
public class DevAppInfoController {
	
	@Resource
	private AppinfoService appInfoService;
	
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	@Resource
	private AppCategoryService appCategoryService;
	
	@Resource
	private AppVersionService appVersionService;
	
	/**
	 * 版本修改页面上传文件
	 * @param id
	 * @return
	 */
	@RequestMapping("appversionmodifysave")
	public String appversionmodifysave(@ModelAttribute AppVersion appVersion,
			@RequestParam(value = "a_idPicPath",required = false) MultipartFile attach,
			HttpServletRequest request) {
		String IdPicPath = null;
		String fileName = null;
		if(!attach.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statice"+File.separator+"uploadfiles");
			String lodFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(lodFileName);
			int filesize = 900000000;
			if(attach.getSize() > filesize) {
				
				request.setAttribute("", "文件太大");
				return "developer/appversionmodify";
				
			}else if(prefix.equalsIgnoreCase("apk")){
				fileName = System.currentTimeMillis()+"_xxx.apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存
				try {
					attach.transferTo(targetFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				//拼接出数据库存储的相对路径
				IdPicPath = File.separator+"statics"+File.separator+"uploadfiles"+File.separator+fileName;
			}else {
				
				request.setAttribute("", "格式不正确");
				return "developer/appversionmodify";
				
			}
		}
		DevUser devUser = (DevUser) request.getSession().getAttribute("devLoginUser");
		//设置其他值(更新者，更新时间，文件名)
		appVersion.setCreatedBy(devUser.getId());
		appVersion.setCreationDate(new Date());
		appVersion.setApkFileName(fileName);
		
		//保存
		boolean flag = appVersionService.saveappVersion(appVersion);
		if(!flag) {
			return "developer/appversionmodify";
		}
		
		return "developer/appinfolist";
	}
	
	/**
	 * 删除版本的安装包文件
	 * @param id
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delfile.json")
	public String delfile(@RequestParam Integer id,@RequestParam String flag,HttpServletRequest request) {
		HashMap<Object, Object> hashMap = new HashMap<>();
		AppVersion appVersion = appVersionService.getAppVersionByVersionId(id);
		String path = request.getSession().getServletContext().getRealPath(appVersion.getApkLocPath());
		File file = new File(path);
		if(file.delete()) {
			hashMap.put("result", "success");
		}else {
			hashMap.put("result", "failed");
		}
		
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * 跳转到版本修改页面
	 * @return
	 */
	@RequestMapping("list/toappversionmodify")
	public String appversionmodify(@RequestParam Integer vid,@RequestParam Integer aid,Model model) {
		//对应app所有版本信息
		List<AppVersion> appVersionList = appVersionService.getAppVersionByInfoid(aid);
		//查询该app的最新版本信息
		AppVersion appVersion = appVersionService.getAppVersionByVersionId(vid);
		
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", appVersion);
		
		return "developer/appversionmodify";
	}
	
	/**
	 * 保存app信息修改
	 * @return
	 */
	@RequestMapping("appinfomodifysave")
	public String appinfomodify() {
		
		return "";
	}
	
	/**
	 * app修改页面删除图片
	 * @param id
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list/delfile")
	public String delfile(HttpServletRequest request,@RequestParam Integer id,@RequestParam String flag) {
		
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		String path = request.getSession().getServletContext().getRealPath(appInfo.getLogoLocPath());
		File file = new File(path);
		if(file.delete()) {
			hashMap.put("result", "success");
		}else {
			hashMap.put("result", "failed");
		}
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * app修改页面的三级联动
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("categorylevellist.json")
	public String categorylevellist(@RequestParam Integer pid) {
		
		List<AppCategory> data = appCategoryService.getAppCategoryListByParentId(pid);
		 
		return JSON.toJSONString(data);
	}
	
	
	/**
	 * app修改页面加载平台列表
	 * @param tcode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("datadictionarylist")
	public String datadictionarylist(@RequestParam String tcode) {
		
		List<DataDictionary> data = dataDictionaryService.getDataDictionaryListByTypeCode("APP_FLATFORM");
		
		return JSON.toJSONString(data);
	}
	
	
	/**
	 * 跳转到app修改页面
	 * @param appinfoid
	 * @return
	 */
	@RequestMapping("/list/toappinfomodify")
	public String toappinfomodify(Model model,@RequestParam Integer appinfoid) {
		
		//app基本信息
		AppInfo appInfo = appInfoService.getAppInfoById(appinfoid);
		if(appInfo != null) {
			model.addAttribute("appInfo", appInfo);
			return "developer/appinfomodify";
		}
		return "developer/appinfolist";
	}
	
	/**
	 * 删除app信息
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list/delapp")
	public String delapp(HttpServletRequest request,Model model,Integer id) {
		
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles"+File.separator);
		Map<String,Object> hashMap = new HashMap<>();
														//该方法重造过可能会有bug
		List<AppVersion> versionList = appVersionService.getAppVersionByInfoid(id);
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		boolean flag = false;
		if(versionList != null) {
			//先删文件  遍历versionList删除每个版本的文件 
			for (AppVersion appVersion : versionList) {
				if(appVersion.getApkLocPath() != null) {
					File file = new File(path+File.separator+appVersion.getApkFileName());
					flag = file.delete();
					//版本文件删除失败   
					if(flag == false) {
						hashMap.put("delResult", "false");
						break;
					}
				}
				//删除版本
				//flag = appVersionService.delVersionByInfoId(id);
			}
			if(flag == false && versionList.size() > 0) {
				hashMap.put("delResult", "false");
				//回到appinfolist页面
				return "developer/appinfolist";
			}
			//删除app图片     路径对不上删不掉
			if(appInfo.getLogoLocPath() != null) {
				//拼接出图片路径，并删除图片
				File file = new File(request.getSession().getServletContext().getRealPath(appInfo.getLogoLocPath()));
				flag = file.delete();
			}
			if(flag == false) {
				hashMap.put("delResult", "false");
				//回到appinfolist页面
				return "developer/appinfolist";
			}
			//上面的都删除成功了   删除app信息
			if(flag) {
			 	flag = appInfoService.dealpp(id);
			}
			if(flag == false) {
				hashMap.put("delResult", "false");
				//回到appinfolist页面
				return "developer/appinfolist";
			}
			hashMap.put("delResult", "true");
		}
		//跳转到appinfolst页面
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * 查看app详细信息
	 * @param model
	 * @param appinfoid
	 * @return
	 */
	@RequestMapping("/list/appview/{appinfoid}")
	public String appview(Model model,@PathVariable Integer appinfoid) {
		
		AppInfo appInfo = appInfoService.getAppInfoById(appinfoid);
		if(appInfo != null) {
			model.addAttribute("appInfo", appInfo);
		}
		return "developer/appinfoview";
	}
	
	/**
	 * 增加版本
	 * @param request
	 * @param appVersion
	 * @param attach
	 * @return
	 */
	@RequestMapping("/addversionsave")
	public String addversionsave(HttpServletRequest request,@ModelAttribute AppVersion appVersion,
			@RequestParam(value="a_downloadLink",required=false) MultipartFile attach) {
		String IdPicPath = null;
		String fileName = null;
		String path = null;
		//判断文件是否为空
		if(!attach.isEmpty()) {
			//文件的本地存储路径
			path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 900000000;
			if(attach.getSize() > filesize) {
				request.getSession().setAttribute("fileUploadError", "上传文件不能超过500kb");
				//返回增加版本页面
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")){
				//新文件名
				fileName = System.currentTimeMillis()+"_xxx.apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存
				try {
					attach.transferTo(targetFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "上传失败");
					return "developer/appversionadd";
				}
				//拼接出数据库存储的相对路径
				IdPicPath = File.separator+"statics"+File.separator+"uploadfiles"+File.separator+fileName;
			}else {
				request.setAttribute("fileUploadError", "上传文件格式不正确");
				return "developer/appversionadd";
			}
		}
		
		//完善对象值
		//下载链接  文件存储路径+文件名
		appVersion.setDownloadLink(IdPicPath);
		//创建者
		DevUser devUser = (DevUser) request.getSession().getAttribute("devLoginUser");
		appVersion.setCreatedBy(devUser.getCreatedBy());
		//创建时间
		appVersion.setCreationDate(new Date());
		//apk文件的服务器存储路径    修改成相对路径！！！！！
		appVersion.setApkLocPath(IdPicPath);
		//上传的apk文件名称
		appVersion.setApkFileName(fileName);
		
		if(!appVersionService.addVersion(appVersion)) {
			return "developer/appversionadd";
		}
		
		return "redirect:/dev/app/list";
	}
	
	/**
	 * 根据appId查询版本列表,跳转到appversionadd页面
	 * @param model
	 * @param appinfoid
	 * @return
	 */
	@RequestMapping("list/appversionadd/{appinfoid}")
	public String appversionadd(Model model,@PathVariable Integer appinfoid,HttpServletRequest request) {
		
		List<AppVersion> appVersionList = appVersionService.getAppVersionByInfoid(appinfoid);
		if(appVersionList != null) {
			model.addAttribute("appVersionList",appVersionList);
		}
		model.addAttribute("appinfoid",appinfoid);
		return "developer/appversionadd";
	}
	
	
	
	/**
	 * 根据父if查询分类列表
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getclist/{pid}")
	public String getCategoryList(@PathVariable Integer pid) {
		List<AppCategory> appCategoryList = appCategoryService.getAppCategoryListByParentId(pid);
		return JSON.toJSONString(appCategoryList);
	}
	
	/**
	 * 挑转到首页
	 * @param model
	 * @param queryAppInfoVO
	 * @return
	 */
	@RequestMapping("/list")
	public String appList(Model model,@ModelAttribute QueryAppInfoVO queryAppInfoVO) {
		
		Integer currentPageNo = 1;
		if(queryAppInfoVO.getPageIndex() != null) {
			currentPageNo = queryAppInfoVO.getPageIndex();
		}
		Integer pageSize = 5;
		PageBean<AppInfo> pageBean = new PageBean<AppInfo>();
		pageBean.setCurrentPageNo(currentPageNo);
		pageBean.setPageSize(pageSize);
		
		appInfoService.getAppInfoList(pageBean,queryAppInfoVO);
		
		//查询app状态
		List<DataDictionary> statusList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_STATUS");
		//查询app所属平台
		List<DataDictionary> flatFormList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_FLATFORM");
		//查询一级分类
		List<AppCategory> categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
		
		// 完善分类的回显
		// 如果传了一级分类  说明你选择过  所以肯定触发过三级联动  认为应该将二级分类全部查询
		if(queryAppInfoVO.getQueryCategoryLevel1() != null) {
			List<AppCategory> categoryLevel2List = appCategoryService.getAppCategoryListByParentId(queryAppInfoVO.getQueryCategoryLevel1());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(queryAppInfoVO.getQueryCategoryLevel2() != null) {
			List<AppCategory> categoryLevel3List = appCategoryService.getAppCategoryListByParentId(queryAppInfoVO.getQueryCategoryLevel2());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("queryAppInfoVO", queryAppInfoVO);
		
		
		
		return "developer/appinfolist";
	}
	
}
