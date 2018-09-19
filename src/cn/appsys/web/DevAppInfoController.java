package cn.appsys.web;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
	
	@RequestMapping("/appinfoaddsave")
	public String appInfoAddSave(@ModelAttribute AppInfo appInfo,HttpServletRequest request,
								@RequestParam(value="a_logoPicPath",required = false)MultipartFile multipartFile) {
		
		String path = null;
		String logoPicPath =  null;
		String logoLocPath =  null; 
		if (!multipartFile.isEmpty()) {
			//获取绝对路径
			path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			//获取文件名
			String fileName = multipartFile.getOriginalFilename();
			//获取文件名的扩展名
			String extension = FilenameUtils.getExtension(fileName);
			//设置文件上传大小
			int fileSize = 50000000;
			//设置文件上传格式
			List<String> asList = Arrays.asList("jpg","png","jpeg","pneg");
			if (fileSize < multipartFile.getSize()) {
				request.setAttribute("fileUploadError", "上传文件超过大小限制");
				return "developer/appinfoadd";
			}else if (!asList.contains(extension)) {
				request.setAttribute("fileUploadError", "不支持此种文件格式！");
				return "developer/appinfoadd";
			}else {
				//重命名
				String newFileName = System.currentTimeMillis()+"logo."+extension;
				//文件上传
				File file = new File(path+File.separator+newFileName);
				try {
					multipartFile.transferTo(file);
					//获取绝对路径
					logoLocPath = path+File.separator+newFileName;
					//获取相对路径
					logoPicPath = File.separator+"statics"+File.separator+"uploadfiles"+newFileName;
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		//获取当前用户
		DevUser devUser = (DevUser)request.getSession().getAttribute("loginUser");
		appInfo.setCreatedBy(devUser.getId());
		appInfo.setCreationDate(new Date());
		appInfo.setStatus(1);
		boolean flag = appInfoService.getAppInfoAdd(appInfo);
		if (!flag) {
			return "developer/appinfoadd";
		}
		return "redirect:/dev/app/list";
	}
	
	/**
	 * 检查APKName名称
	 * @param APKName
	 * @return
	 */
	@RequestMapping("apkexist/{APKName}")
	@ResponseBody
	public String checkAPKName(@PathVariable String APKName) {
		HashMap<String, Object> hashMap = new HashMap<>();
		AppInfo appInfo = appInfoService.getAppInfoByAPKName(APKName);
		String result = "exist";
		if (appInfo == null) {
			result = "noexist";
		}
		hashMap.put("result", result);
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * 跳转到app增加页面
	 * @return
	 */
	@RequestMapping("appinfoadd")
	public String toAppInfoAdd() {
		return "developer/appinfoadd";
	}
	
	/**
	 * 修改app信息
	 * @param session
	 * @param appInfo
	 * @return
	 */
	@RequestMapping("/appinfomodifysave")
	public String appInfoModifySave(HttpServletRequest request,@ModelAttribute AppInfo appInfo,
									@RequestParam(value="attach",required=false)MultipartFile multipartFile) {
		String path = null;
		String logoPicPath = null;
		String logoLocPath = null;
		if (!multipartFile.isEmpty()) {
			//获取绝对路径
			path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			//获取文件名
			String fileName = multipartFile.getOriginalFilename();
			//获取文件名的扩展名
			String extension = FilenameUtils.getExtension(fileName);
			//设置文件上传大小
			int fileSize = 50000000;
			//设置文件上传格式
			List<String> asList = Arrays.asList("jpg","png","jpeg","pneg");
			if (fileSize < multipartFile.getSize()) {
				request.setAttribute("fileUploadError", "上传文件超过大小限制");
				return "developer/appinfoadd";
			}else if (!asList.contains(extension)) {
				request.setAttribute("fileUploadError", "不支持此种文件格式！");
				return "developer/appinfoadd";
			}else {
				//重命名
				String newFileName = System.currentTimeMillis()+"logo."+extension;
				//文件上传
				File file = new File(path+File.separator+newFileName);
				try {
					multipartFile.transferTo(file);
					//获取绝对路径
					logoLocPath = path+File.separator+newFileName;
					//获取相对路径
					logoPicPath = File.separator+"statics"+File.separator+"uploadfiles"+newFileName;
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		//获取当前登录用户
		DevUser devUser = (DevUser) request.getSession().getAttribute("loginUser");
		appInfo.setModifyBy(devUser.getId());
		appInfo.setModifyDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		boolean flag = appInfoService.appInfoModify(appInfo);
		if (!flag) {
			return "developer/appinfomodify";
		}
		return "redirect:/dev/app/list";
	}
	
	/**
	 * 修改页面删除图片 未完成
	 * @param id
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appinfomodify/delfile")
	public String deFile(Integer id,String flag) {
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		HashMap<String, Object> hashMap = new HashMap<>();
		String result = "";
		 if ("logo".equals(flag)) { //删除logo图片
			 if (appInfo.getLogoLocPath()!= null) {
					File file = new File(appInfo.getLogoLocPath());
					if (file.exists()) {
						if (file.delete()) {
							//删除数据库的logo图片，实际上更新数据，把logoPicPath清空
							boolean boo = appInfoService.deleteLogoPicPath(id);
							if (boo) {
								result = "success";
							}
						}	
					}
				}else {
					result = "failed";
				}
		}
		
		hashMap.put("result", result);
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * ajax加载分类列表
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value= {"appinfomodify/categorylevellist","categorylevellist"})
	public String categoryLevelList(Integer pid) {
		List<AppCategory> categoryLevel1List = appCategoryService.getAppCategoryListByParentId(pid);
		return JSON.toJSONString(categoryLevel1List);
	}
	
	/**
	 * ajax加载所属平台列表
	 * @param typeCode
	 * @return
	 */
	@RequestMapping(value={"appinfomodify/datadictionarylist/{typeCode}","datadictionarylist/{typeCode}"})
	//@RequestMapping("datadictionarylist")
	@ResponseBody
	public String dataDictionaryList(@PathVariable String typeCode) {
		List<DataDictionary> flatFormList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_FLATFORM");
		return JSON.toJSONString(flatFormList);
	}
	
	/**
	 * 跳转到修改app基础信息页面
	 * @param id
	 * @return
	 */
	@RequestMapping("appinfomodify/{id}")
	public String appInfoModify(Model model ,@PathVariable Integer id) {
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}
	
	@ResponseBody
	@RequestMapping("list/delapp")
	public String delapp(Model model,Integer id) {
		
		Map<String,Object> hashMap = new HashMap<>();
		List<AppVersion> versionList = appVersionService.getVersionByAppInfoId(id);
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		boolean flag = false;
		if(versionList != null) {
			//先删文件  遍历versionList删除每个版本的文件 
			for (AppVersion appVersion : versionList) {
				if(appVersion.getApkLocPath() != null) {
					File file = new File(appVersion.getApkLocPath());
					//flag = file.delete();
					//版本文件删除失败    
					/*if(flag == false) {
						hashMap.put("delResult", "false");
						break;
					}*/
				}
				//删除版本
				//flag = appVersionService.delVersionByInfoId(id);
			}
			/*if(flag == false) {
				hashMap.put("delResult", "false");
				//回到appinfolist页面
				return "developer/appinfolist";
			}*/
			//删除app图片     路径对不上删不掉
			if(appInfo.getLogoLocPath() != null) {
				File file = new File(appInfo.getLogoLocPath());
				//flag = file.delete();
			}
			/*if(flag == false) {
				hashMap.put("delResult", "false");
				//回到appinfolist页面
				return "developer/appinfolist";
			}*/
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
	@RequestMapping("addversionsave")
	public String addversionsave(HttpServletRequest request,@ModelAttribute AppVersion appVersion,
			@RequestParam(value="a_downloadLink",required=false) MultipartFile attach) {
		String IdPicPath = null;
		String fileName = null;
		String path = null;
		//判断文件是否为空
		if(!attach.isEmpty()) {
			//文件的服务器存储路径
			path = request.getServletContext().getRealPath("statics"+File.separator+"uploadfils");
			path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfils");
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
				IdPicPath = path+File.separator+fileName;
				
			}else {
				request.setAttribute("fileUploadError", "上传文件格式不正确");
				return "developer/appversionadd";
			}
		}
		
		//完善对象值
		//下载链接  文件存储路径+文件名
		appVersion.setDownloadLink(path+fileName);
		//创建者
		DevUser devUser = (DevUser) request.getSession().getAttribute("devLoginUser");
		appVersion.setCreatedBy(devUser.getCreatedBy());
		//创建时间
		appVersion.setCreationDate(new Date());
		//apk文件的服务器存储路径
		appVersion.setApkLocPath(path);
		//上传的apk文件名称
		appVersion.setApkFileName(fileName);
		
		if(!appVersionService.addVersion(appVersion)) {
			return "developer/appversionadd";
		}
		
		return "redirect:/dev/app/list";
	}
	
	/**
	 * 根据appId查询版本列表
	 * @param model
	 * @param appinfoid
	 * @return
	 */
	@RequestMapping("list/appversionadd/{appinfoid}")
	public String appversionadd(Model model,@PathVariable Integer appinfoid) {
		
		List<AppVersion> appVersionList = appVersionService.getAppVersionByInfoid(appinfoid);
		if(appVersionList != null) {
			model.addAttribute("appVersionList",appVersionList);
		}
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
