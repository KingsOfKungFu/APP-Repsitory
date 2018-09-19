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
	

	@RequestMapping("/appinfoaddsave")
	public String appInfoAddSave(@ModelAttribute AppInfo appInfo,HttpServletRequest request,
								@RequestParam(value="a_logoPicPath",required = false)MultipartFile multipartFile) {
		
		String path = null;
		String logoPicPath =  null;
		String logoLocPath =  null; 
		if (!multipartFile.isEmpty()) {
			//鑾峰彇缁濆璺緞
			path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			//鑾峰彇鏂囦欢鍚�
			String fileName = multipartFile.getOriginalFilename();
			//鑾峰彇鏂囦欢鍚嶇殑鎵╁睍鍚�
			String extension = FilenameUtils.getExtension(fileName);
			//璁剧疆鏂囦欢涓婁紶澶у皬
			int fileSize = 50000000;
			//璁剧疆鏂囦欢涓婁紶鏍煎紡
			List<String> asList = Arrays.asList("jpg","png","jpeg","pneg");
			if (fileSize < multipartFile.getSize()) {
				request.setAttribute("fileUploadError", "涓婁紶鏂囦欢瓒呰繃澶у皬闄愬埗");
				return "developer/appinfoadd";
			}else if (!asList.contains(extension)) {
				request.setAttribute("fileUploadError", "涓嶆敮鎸佹绉嶆枃浠舵牸寮忥紒");
				return "developer/appinfoadd";
			}else {
				//閲嶅懡鍚�
				String newFileName = System.currentTimeMillis()+"logo."+extension;
				//鏂囦欢涓婁紶
				File file = new File(path+File.separator+newFileName);
				try {
					multipartFile.transferTo(file);
					//鑾峰彇缁濆璺緞
					logoLocPath = path+File.separator+newFileName;
					//鑾峰彇鐩稿璺緞
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
		//鑾峰彇褰撳墠鐢ㄦ埛
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
=======
	 * 妫�鏌PKName鍚嶇О
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
	 * 璺宠浆鍒癮pp澧炲姞椤甸潰
	 * @return
	 */
	@RequestMapping("appinfoadd")
	public String toAppInfoAdd() {
		return "developer/appinfoadd";
	}
	
	/**
	 * 淇敼app淇℃伅
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
			//鑾峰彇缁濆璺緞
			path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			//鑾峰彇鏂囦欢鍚�
			String fileName = multipartFile.getOriginalFilename();
			//鑾峰彇鏂囦欢鍚嶇殑鎵╁睍鍚�
			String extension = FilenameUtils.getExtension(fileName);
			//璁剧疆鏂囦欢涓婁紶澶у皬
			int fileSize = 50000000;
			//璁剧疆鏂囦欢涓婁紶鏍煎紡
			List<String> asList = Arrays.asList("jpg","png","jpeg","pneg");
			if (fileSize < multipartFile.getSize()) {
				request.setAttribute("fileUploadError", "涓婁紶鏂囦欢瓒呰繃澶у皬闄愬埗");
				return "developer/appinfoadd";
			}else if (!asList.contains(extension)) {
				request.setAttribute("fileUploadError", "涓嶆敮鎸佹绉嶆枃浠舵牸寮忥紒");
				return "developer/appinfoadd";
			}else {
				//閲嶅懡鍚�
				String newFileName = System.currentTimeMillis()+"logo."+extension;
				//鏂囦欢涓婁紶
				File file = new File(path+File.separator+newFileName);
				try {
					multipartFile.transferTo(file);
					//鑾峰彇缁濆璺緞
					logoLocPath = path+File.separator+newFileName;
					//鑾峰彇鐩稿璺緞
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
		//鑾峰彇褰撳墠鐧诲綍鐢ㄦ埛
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
	 * 淇敼椤甸潰鍒犻櫎鍥剧墖 鏈畬鎴�
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
		 if ("logo".equals(flag)) { //鍒犻櫎logo鍥剧墖
			 if (appInfo.getLogoLocPath()!= null) {
					File file = new File(appInfo.getLogoLocPath());
					if (file.exists()) {
						if (file.delete()) {
							//鍒犻櫎鏁版嵁搴撶殑logo鍥剧墖锛屽疄闄呬笂鏇存柊鏁版嵁锛屾妸logoPicPath娓呯┖
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
	 * ajax鍔犺浇鍒嗙被鍒楄〃
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
	 * ajax鍔犺浇鎵�灞炲钩鍙板垪琛�
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
	 * 璺宠浆鍒颁慨鏀筧pp鍩虹淇℃伅椤甸潰
	 * @param id
	 * @return
	 */
	@RequestMapping("appinfomodify/{id}")
	public String appInfoModify(Model model ,@PathVariable Integer id) {
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}
	

	/**
	 * 锟斤拷锟斤拷app锟斤拷息锟睫革拷
>>>>>>> branch 'develop' of https://github.com/KingsOfKungFu/APP-Repsitory.git
	 * @return
	 */
	@RequestMapping("appinfomodifysave")
	public String appinfomodify() {
		
		return "";
	}
	
	/**
<<<<<<< HEAD
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
=======
	 * 锟斤拷转锟斤拷app锟睫革拷页锟斤拷
>>>>>>> branch 'develop' of https://github.com/KingsOfKungFu/APP-Repsitory.git
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
	 * 删锟斤拷app锟斤拷息
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
			//锟斤拷删锟侥硷拷  锟斤拷锟斤拷versionList删锟斤拷每锟斤拷锟芥本锟斤拷锟侥硷拷 
			for (AppVersion appVersion : versionList) {
				if(appVersion.getApkLocPath() != null) {
					File file = new File(path+File.separator+appVersion.getApkFileName());
					flag = file.delete();
					//锟芥本锟侥硷拷删锟斤拷失锟斤拷   
					if(flag == false) {
						hashMap.put("delResult", "false");
						break;
					}
				}
				//删锟斤拷锟芥本
				//flag = appVersionService.delVersionByInfoId(id);
			}
			if(flag == false && versionList.size() > 0) {
				hashMap.put("delResult", "false");
				//锟截碉拷appinfolist页锟斤拷
				return "developer/appinfolist";
			}
			//删锟斤拷app图片     路锟斤拷锟皆诧拷锟斤拷删锟斤拷锟斤拷
			if(appInfo.getLogoLocPath() != null) {
				//拼锟接筹拷图片路锟斤拷锟斤拷锟斤拷删锟斤拷图片
				File file = new File(request.getSession().getServletContext().getRealPath(appInfo.getLogoLocPath()));
				flag = file.delete();
			}
			if(flag == false) {
				hashMap.put("delResult", "false");
				//锟截碉拷appinfolist页锟斤拷
				return "developer/appinfolist";
			}
			//锟斤拷锟斤拷亩锟缴撅拷锟斤拷晒锟斤拷锟�   删锟斤拷app锟斤拷息
			if(flag) {
			 	flag = appInfoService.dealpp(id);
			}
			if(flag == false) {
				hashMap.put("delResult", "false");
				//锟截碉拷appinfolist页锟斤拷
				return "developer/appinfolist";
			}
			hashMap.put("delResult", "true");
		}
		//锟斤拷转锟斤拷appinfolst页锟斤拷
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * 锟介看app锟斤拷细锟斤拷息
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
	 * 锟斤拷锟接版本
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
		//锟叫讹拷锟侥硷拷锟角凤拷为锟斤拷
		if(!attach.isEmpty()) {
			//锟侥硷拷锟侥憋拷锟截存储路锟斤拷
			path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 900000000;
			if(attach.getSize() > filesize) {
				request.getSession().setAttribute("fileUploadError", "锟较达拷锟侥硷拷锟斤拷锟杰筹拷锟斤拷500kb");
				//锟斤拷锟斤拷锟斤拷锟接版本页锟斤拷
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")){
				//锟斤拷锟侥硷拷锟斤拷
				fileName = System.currentTimeMillis()+"_xxx.apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//锟斤拷锟斤拷
				try {
					attach.transferTo(targetFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "锟较达拷失锟斤拷");
					return "developer/appversionadd";
				}
				//拼锟接筹拷锟斤拷锟捷匡拷娲拷锟斤拷锟斤拷路锟斤拷
				IdPicPath = File.separator+"statics"+File.separator+"uploadfiles"+File.separator+fileName;
			}else {
				request.setAttribute("fileUploadError", "锟较达拷锟侥硷拷锟斤拷式锟斤拷锟斤拷确");
				return "developer/appversionadd";
			}
		}
		
		//锟斤拷锟狡讹拷锟斤拷值
		//锟斤拷锟斤拷锟斤拷锟斤拷  锟侥硷拷锟芥储路锟斤拷+锟侥硷拷锟斤拷
		appVersion.setDownloadLink(IdPicPath);
		//锟斤拷锟斤拷锟斤拷
		DevUser devUser = (DevUser) request.getSession().getAttribute("devLoginUser");
		appVersion.setCreatedBy(devUser.getCreatedBy());
		//锟斤拷锟斤拷时锟斤拷
		appVersion.setCreationDate(new Date());
		//apk锟侥硷拷锟侥凤拷锟斤拷锟斤拷锟芥储路锟斤拷    锟睫改筹拷锟斤拷锟铰凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
		appVersion.setApkLocPath(IdPicPath);
		//锟较达拷锟斤拷apk锟侥硷拷锟斤拷锟斤拷
		appVersion.setApkFileName(fileName);
		
		if(!appVersionService.addVersion(appVersion)) {
			return "developer/appversionadd";
		}
		
		return "redirect:/dev/app/list";
	}
	
	/**
	 * 锟斤拷锟斤拷appId锟斤拷询锟芥本锟叫憋拷,锟斤拷转锟斤拷appversionadd页锟斤拷
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
	 * 锟斤拷锟捷革拷if锟斤拷询锟斤拷锟斤拷锟叫憋拷
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
	 * 锟斤拷转锟斤拷锟斤拷页
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
		
		//锟斤拷询app状态
		List<DataDictionary> statusList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_STATUS");
		//锟斤拷询app锟斤拷锟斤拷平台
		List<DataDictionary> flatFormList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_FLATFORM");
		//锟斤拷询一锟斤拷锟斤拷锟斤拷
		List<AppCategory> categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
		
		// 锟斤拷锟狡凤拷锟斤拷幕锟斤拷锟�
		// 锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟�  说锟斤拷锟斤拷选锟斤拷锟�  锟斤拷锟皆肯讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷  锟斤拷为应锟矫斤拷锟斤拷锟斤拷锟斤拷锟斤拷全锟斤拷锟斤拷询
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
