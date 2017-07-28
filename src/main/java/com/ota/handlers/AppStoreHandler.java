package com.ota.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ota.dao.AppstoreMapper;
import com.ota.dao.AppstoreMappingMapper;
import com.ota.dao.BoxTypeMapper;
import com.ota.dao.BusinessMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.domain.Appstore;
import com.ota.domain.AppstoreInfo;
import com.ota.domain.AppstoreMapping;
import com.ota.domain.AppstoreWithAllInfo;
import com.ota.domain.FilterForMultipleFiles;
import com.ota.domain.UpdateFileInfo;
import com.ota.tools.OTAToolKit;

@Controller
public class AppStoreHandler {

	@Autowired
	private AppstoreMapper appstoreMapper;

	@Autowired
	private AppstoreMappingMapper appstoreMappingMapper;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private BusinessMapper businessMapper;
	
	@Autowired
	private BoxTypeMapper boxTypeMapper;

	private String appstoredownloadUrl = "";
	
	private String appstoreIcondownloadUrl = "";

	@RequestMapping("store-info")
	@ResponseBody
	public List<AppstoreWithAllInfo> pathcStoreInfo(HttpServletRequest request) {
		
		int offset = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String customer = request.getParameter("customer");
		String model = request.getParameter("model");
		Integer customerid;
		Integer modelid;
		if (customer.equals("All Customer"))
			customerid = null;
		else {
			customerid = businessMapper.selectBusinessidByName(customer);
		}
		if (model.equals("All Model"))
			modelid = null;
		else {
			modelid = boxTypeMapper.selectModelIDByType(model);
		}
		
		List<AppstoreWithAllInfo> appstoresinfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("customerid", customerid);
			map.put("modelid", modelid);
			PageBounds pageBounds = OTAToolKit.getPagerBoundsByParameter(pageSize, offset);
			appstoresinfo = appstoreMapper.selectAllAppstoreInfoWithFilter(map, pageBounds);
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		return appstoresinfo;
		
	}

	@RequestMapping("store-detail")
	@ResponseBody
	public JSONObject patchStoreDetail(@RequestBody JSONObject addetailId) {
		JSONObject json = new JSONObject();
		int id = Integer.parseInt(addetailId.getString("storeId"));
		Appstore ad = appstoreMapper.selectByPrimaryKey(id);
		session.setAttribute("businessid", ad.getBusinessid());
		json.put("appstore", ad);
		if (ad.getMappingid() != null && ad.getMappingid().length() > 0) {
			String mappingid = ad.getMappingid();
			String[] multMapping = mappingid.split(",");
			List<AppstoreMapping> appstoremapping = new ArrayList<>();
			for (int i = 0; i < multMapping.length; i++) {
				appstoremapping.add(appstoreMappingMapper.selectByPrimaryKey(Integer.parseInt(multMapping[i])));
			}

			json.put("appstoremapping", appstoremapping);
		}
		return json;
	}

	@RequestMapping("deleteAppFile")
	@ResponseBody
	public JSONObject deleteAppFile(@RequestBody JSONObject id) {
		JSONObject json = new JSONObject();
		int deleteid = Integer.parseInt(id.getString("id"));

		try {
			/*
			Appstore ad = appstoreMapper.selectByPrimaryKey(deleteid);
			String mappingid = ad.getMappingid();
			if (mappingid != null && mappingid.length() > 0) {
				String[] deletefilteridArrary = mappingid.split(",");
				// 逐条删除updatefile 对应的filter记录
				for (String deletefilterid : deletefilteridArrary) {
					appstoreMappingMapper.deleteByPrimaryKey(Integer.parseInt(deletefilterid));
				}
			}
			*/
			appstoreMapper.deleteByPrimaryKey(deleteid);
			json.put("id", deleteid);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("deleteAppFilter")
	@ResponseBody
	public JSONObject deleteAppFilter(@RequestBody JSONObject id) {
		JSONObject json = new JSONObject();
		int deleteid = Integer.parseInt(id.getString("id"));
		int appfileid = Integer.parseInt(id.getString("apkid"));

		try {
			//admpmp.deleteByPrimaryKey(deleteid);
			
			Appstore appfile = appstoreMapper.selectByPrimaryKey(appfileid);
			String mappingid = appfile.getMappingid();
			System.out.println("mappingid: " + mappingid);
			// mappingid 一定存在 否则 业务逻辑异常或数据库中的数据错误
			mappingid = OTAToolKit.deleteFilterIdInFile(mappingid, id.getString("id"));
			appfile.setMappingid(mappingid);
			appstoreMapper.updateByPrimaryKeySelective(appfile);
			 
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("storeAddFilter")
	@ResponseBody
	public JSONObject storeAddFilter(@RequestBody AppstoreInfo appstoreinfo) {
		System.out.println("addfilter:session.getAttribute(\"businessid\"): " + session.getAttribute("businessid"));
		JSONObject json = new JSONObject();

		Appstore appstore = appstoreinfo.getAppstore();
		AppstoreMapping appstoremapping = appstoreinfo.getAppstoreMapping();

		try {
			appstoremapping.setBusinessid(session.getAttribute("businessid")+"");
			// autoID 是插入新增filemapping后 由数据库返回的自增id 但是返回的一直是1 ，于是没有使用
			// ，又做了一次取id的操作
			// 有待优化的sql问题
			appstoreMappingMapper.insertSelective(appstoremapping);
			int id = appstoreMappingMapper.selectlastid();
			String mappingid = null;
			if (appstoreMapper.selectByPrimaryKey(appstore.getId()).getMappingid() == null
					|| appstoreMapper.selectByPrimaryKey(appstore.getId()).getMappingid().length() <= 0) {
				mappingid = "" + id;

			} else {
				mappingid = appstoreMapper.selectByPrimaryKey(appstore.getId()).getMappingid();
				mappingid = mappingid + "," + id;
			}

			appstore.setMappingid(mappingid);
			appstore.setBusinessid((Integer) session.getAttribute("businessid"));
			appstoreMapper.updateMappingIDByPrimaryKey(appstore);

			json.put("code", "1");
			json.put("msg", "success");
			json.put("id", id);
		} catch (Exception e) {
			json.put("code", "-1");
			json.put("msg", "failed");
			e.printStackTrace();
			
		}

		return json;
	}
	
	@RequestMapping("storeUpdateFilter")
	@ResponseBody
	public JSONObject storeUpdateFilter(@RequestBody AppstoreInfo appstoreInfo) {
		JSONObject json = new JSONObject();

		Appstore appstore = appstoreInfo.getAppstore();
		AppstoreMapping appstoreMapping = appstoreInfo.getAppstoreMapping();

		try {
			// 更新
			appstoreMapping.setId(Integer.parseInt(appstore.getMappingid()));
			appstoreMappingMapper.updateByPrimaryKeySelective(appstoreMapping);
			json.put("code", "1");
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", "-1");
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}
	
	@RequestMapping("addapkupdateFile")
	@ResponseBody
	public JSONObject addapkupdateFile(@RequestBody Appstore appstore) {
		JSONObject json = new JSONObject();
		System.out.println("session.getAttribute(\"businessid\"): " + session.getAttribute("businessid"));
		try {
			appstore.setApkdownload(appstoredownloadUrl);
			appstore.setApkicon(appstoreIcondownloadUrl);
			appstore.setBusinessid( (Integer) session.getAttribute("businessid"));
			appstore.setUpdateTime(new Date());
			int autoid = appstoreMapper.insertSelective(appstore);
			int id = appstoreMapper.selectlastid();
			json.put("code", 1);
			json.put("msg", "success");
			json.put("id", id);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", -1);
		}
		return json;
	}
	
	
	@RequestMapping("uploadappstorefile")
	@ResponseBody
	public JSONObject uploadupdatefile(HttpServletRequest req,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		JSONObject json = new JSONObject();
		System.out.println("开始");
		String path = req.getSession().getServletContext().getRealPath("upload");
		String businessName = (String) session.getAttribute("business_name");
		
		path = OTAToolKit.getOSUrl(path, businessName);
		
		String fileName = OTAToolKit.addTimeStamp(file.getOriginalFilename());
		// /ota/src/main/webapp/upload
		System.out.println("path:" + path);
		System.out.println("fileName:" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
			appstoredownloadUrl = "upload";
			appstoredownloadUrl = OTAToolKit.getOSUrl(appstoredownloadUrl, businessName, fileName);
			System.out.println(appstoredownloadUrl);
			json.put("msgcode", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msgcode", "-1");
		}

		return json;
	}
	
	
	@RequestMapping("uploadIconfile")
	@ResponseBody
	public JSONObject uploadIconfile(HttpServletRequest req,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		JSONObject json = new JSONObject();
		System.out.println("开始");
		String path = req.getSession().getServletContext().getRealPath("upload");
		String businessName = (String) session.getAttribute("business_name");
		
		path = OTAToolKit.getOSUrl(path, businessName);
		
		String fileName = OTAToolKit.addTimeStamp(file.getOriginalFilename());
		// /ota/src/main/webapp/upload
		System.out.println("path:" + path);
		System.out.println("fileName:" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
			appstoreIcondownloadUrl = "upload";
			appstoreIcondownloadUrl = OTAToolKit.getOSUrl(appstoredownloadUrl, businessName, fileName);
			System.out.println(appstoreIcondownloadUrl);
			json.put("msgcode", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msgcode", "-1");
		}

		return json;
	}
	
	@RequestMapping(value="/store_uploadfile", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String uploadFileHandler(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		System.out.println("开始");
		String path = request.getSession().getServletContext().getRealPath("upload");
		String sysdate = OTAToolKit.getSystemDate();
		
		path = OTAToolKit.getOSUrl(path, sysdate);
		
		String fileName = OTAToolKit.addTimeStamp(file.getOriginalFilename());
		// /ota/src/main/webapp/upload
		System.out.println("path:" + path);
		System.out.println("fileName:" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
			appstoredownloadUrl = "upload";
			appstoredownloadUrl = OTAToolKit.getOSUrl(appstoredownloadUrl, sysdate, fileName);
			System.out.println(appstoredownloadUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	@RequestMapping(value="/store_uploadicon", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String uploadIconHandler(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		System.out.println("开始");
		String path = request.getSession().getServletContext().getRealPath("upload");
		String sysdate = OTAToolKit.getSystemDate();
		
		path = OTAToolKit.getOSUrl(path, sysdate);
		
		String fileName = OTAToolKit.addTimeStamp(file.getOriginalFilename());
		// /ota/src/main/webapp/upload
		System.out.println("path:" + path);
		System.out.println("fileName:" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
			appstoredownloadUrl = "upload";
			appstoredownloadUrl = OTAToolKit.getOSUrl(appstoredownloadUrl, sysdate, fileName);
			System.out.println(appstoredownloadUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	
	@RequestMapping(value="apkstoreAddMultFilter") 
	@ResponseBody
	public JSONObject apkstoreAddMultFilter(@RequestBody FilterForMultipleFiles filterForMultipleFiles) {
		JSONObject json = new JSONObject();
		String ids = filterForMultipleFiles.getIds();
		String[] idArray = ids.split(",");
		Integer businessid = (Integer) session.getAttribute("businessid");
		
		Appstore appstore = null;
		
		AppstoreMapping appstoreMapping = filterForMultipleFiles.getAppstoreMapping();
		appstoreMapping.setBusinessid(businessid+"");
		try {
			
			appstoreMappingMapper.insertSelective(appstoreMapping);
			int autoid = appstoreMappingMapper.selectlastid();
			for(String id: idArray) {
				appstore = appstoreMapper.selectByPrimaryKey(Integer.parseInt(id));
				appstore.setBusinessid(businessid);
				appstore.setId(Integer.parseInt(id));
				if(appstore.getMappingid() == null || appstore.getMappingid().length() <= 0) {
					appstore.setMappingid("" + autoid);
				} else {
					appstore.setMappingid(appstore.getMappingid() + "," + autoid);
				}
				appstoreMapper.updateForAddFilterToMultFile(appstore);
			}
			json.put("code", "1");
			
		} catch(Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
		}
		
		
		return json;
	}
}
