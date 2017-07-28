package com.ota.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ota.dao.AdvertisementMapper;
import com.ota.dao.AdvertisementMappingMapper;
import com.ota.dao.BusinessMapper;
import com.ota.domain.AdverInfo;
import com.ota.domain.Advertisement;
import com.ota.domain.AdvertisementInfo;
import com.ota.domain.AdvertisementMapping;
import com.ota.domain.FilterForMultipleFiles;
import com.ota.tools.OTAToolKit;

@Controller
public class AdvertismentHandler {

	@Autowired
	private AdvertisementMapper admp;

	@Autowired
	private AdvertisementMappingMapper admpmp;

	@Autowired
	private HttpSession session;
	@Autowired
	private BusinessMapper businessMapper;
	
	private String addownloadUrl = "";

	@RequestMapping("advertisement-info")
	@ResponseBody
	public List<AdverInfo> advertisementInfo(HttpServletRequest request) {
		System.out.println("advertisement");
		int offset = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String customer = request.getParameter("customer");
		Integer customerid;
		if (customer.equals("All Customer"))
			customerid = null;
		else {
			customerid = businessMapper.selectBusinessidByName(customer);
		}
		
		List<AdverInfo> advertisements = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("customerid", customerid);
			PageBounds pageBounds = OTAToolKit.getPagerBoundsByParameter(pageSize, offset);
			advertisements = admp.selectAllAdvertisementWithFilter(map, pageBounds);
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		for(AdverInfo a: advertisements){
			System.out.println(a.toString());
		}
		return advertisements;
	}

	@RequestMapping("advertisement-detail")
	@ResponseBody
	public JSONObject patchDetail(@RequestBody JSONObject addetailId) {
		JSONObject json = new JSONObject();
		int id = Integer.parseInt(addetailId.getString("advertisementId"));
		Advertisement ad = admp.selectByPrimaryKey(id);
		json.put("advertisement", ad);
		if (ad.getMappingid() != null && ad.getMappingid().length() > 0) {
			String mappingid = ad.getMappingid();
			String[] multMapping = mappingid.split(",");
			List<AdvertisementMapping> admapping = new ArrayList<>();
			for (int i = 0; i < multMapping.length; i++) {
				admapping.add(admpmp.selectByPrimaryKey(Integer.parseInt(multMapping[i])));
			}

			json.put("advertisementmapping", admapping);
		}
		return json;
	}

	@RequestMapping("deleteAdFile")
	@ResponseBody
	public JSONObject deleteFile(@RequestBody JSONObject id) {
		JSONObject json = new JSONObject();
		int deleteid = Integer.parseInt(id.getString("id"));

		try {
			/*
			Advertisement ad = admp.selectByPrimaryKey(deleteid);
			String mappingid = ad.getMappingid();
			
			//瀵瑰瀵圭殑鍏崇郴 涓嶈兘鍒犻櫎filter
			
			if (mappingid != null && mappingid.length() > 0) {
				String[] deletefilteridArrary = mappingid.split(",");
				// 閫愭潯鍒犻櫎updatefile 瀵瑰簲鐨刦ilter璁板綍
				for (String deletefilterid : deletefilteridArrary) {
					admpmp.deleteByPrimaryKey(Integer.parseInt(deletefilterid));
				}
			}
			
			*/
			admp.deleteByPrimaryKey(deleteid);
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

	@RequestMapping("deleteAdFilter")
	@ResponseBody
	public JSONObject deleteFilter(@RequestBody JSONObject id) {
		JSONObject json = new JSONObject();
		int deleteid = Integer.parseInt(id.getString("id"));
		int adid = Integer.parseInt(id.getString("adid"));

		try {
			//admpmp.deleteByPrimaryKey(deleteid);
			
			Advertisement adfile = admp.selectByPrimaryKey(adid);
			String mappingid = adfile.getMappingid();
			System.out.println("mappingid: " + mappingid);
			// mappingid 涓�瀹氬瓨鍦� 鍚﹀垯 涓氬姟閫昏緫寮傚父鎴栨暟鎹簱涓殑鏁版嵁閿欒
			mappingid = OTAToolKit.deleteFilterIdInFile(mappingid, id.getString("id"));
			adfile.setMappingid(mappingid);
			admp.updateByPrimaryKeySelective(adfile);
			 
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("advertisementAddFilter")
	@ResponseBody
	public JSONObject patchAddFilter(@RequestBody AdvertisementInfo adinfo) {
		System.out.println("addfilter:session.getAttribute(\"businessid\"): " + session.getAttribute("businessid"));
		JSONObject json = new JSONObject();

		Advertisement ad = adinfo.getAdvertisement();
		AdvertisementMapping admapping = adinfo.getAdvertisementMapping();

		try {
			admapping.setBusinessid((String) session.getAttribute("businessid"));
			// autoID 鏄彃鍏ユ柊澧瀎ilemapping鍚� 鐢辨暟鎹簱杩斿洖鐨勮嚜澧瀒d 浣嗘槸杩斿洖鐨勪竴鐩存槸1 锛屼簬鏄病鏈変娇鐢�
			// 锛屽張鍋氫簡涓�娆″彇id鐨勬搷浣�
			// 鏈夊緟浼樺寲鐨剆ql闂
			admpmp.insertSelective(admapping);
			int id = admpmp.selectlastid();
			String mappingid = null;
			if (admp.selectByPrimaryKey(ad.getId()).getMappingid() == null
					|| admp.selectByPrimaryKey(ad.getId()).getMappingid().length() <= 0) {
				mappingid = "" + id;

			} else {
				mappingid = admp.selectByPrimaryKey(ad.getId()).getMappingid();
				mappingid = mappingid + "," + id;
			}

			ad.setMappingid(mappingid);
			ad.setBusinessid((String) session.getAttribute("businessid"));
			admp.updateMappingIDByPrimaryKey(ad);

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
	
	@RequestMapping("advertisementUpdateFilter")
	@ResponseBody
	public JSONObject patchUpdateFilter(@RequestBody AdvertisementInfo advertisementInfo) {
		JSONObject json = new JSONObject();

		Advertisement ad = advertisementInfo.getAdvertisement();
		AdvertisementMapping adMapping = advertisementInfo.getAdvertisementMapping();

		try {
			// 鏇存柊
			adMapping.setId(Integer.parseInt(ad.getMappingid()));
			admpmp.updateByPrimaryKeySelective(adMapping);
			json.put("code", "1");
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", "-1");
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}
	
	@RequestMapping("addadupdateFile")
	@ResponseBody
	public JSONObject addupdateFile(@RequestBody Advertisement advertisement) {
		JSONObject json = new JSONObject();
		System.out.println("session.getAttribute(\"businessid\"): " + session.getAttribute("businessid"));
		try {
			advertisement.setDownload(addownloadUrl);

			advertisement.setBusinessid((String) session.getAttribute("businessid"));
			advertisement.setUpdateTime(new Date());
			int autoid = admp.insertSelective(advertisement);
			int id = admp.selectlastid();
			json.put("code", 1);
			json.put("msg", "success");
			json.put("id", id);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", -1);
		}
		return json;
	}
	
	
	@RequestMapping("uploadadfile")
	@ResponseBody
	public JSONObject uploadupdatefile(HttpServletRequest req,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		JSONObject json = new JSONObject();
		System.out.println("寮�濮�");
		String path = req.getSession().getServletContext().getRealPath("upload");
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

		// 淇濆瓨
		try {
			file.transferTo(targetFile);
			addownloadUrl = "upload";
			addownloadUrl = OTAToolKit.getOSUrl(addownloadUrl, sysdate, fileName);
			System.out.println(addownloadUrl);
			json.put("msgcode", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msgcode", "-1");
		}

		return json;
	}
	
	
	@RequestMapping(value="advertisementAddMultFilter") 
	@ResponseBody
	public JSONObject addmultadfile(@RequestBody FilterForMultipleFiles filterForMultipleFiles) {
		JSONObject json = new JSONObject();
		String ids = filterForMultipleFiles.getIds();
		String[] idArray = ids.split(",");
		String businessid = (String) session.getAttribute("businessid");
		
		Advertisement advertisement = null;
		
		AdvertisementMapping advertisementMapping = filterForMultipleFiles.getAdvertisementMapping();
		advertisementMapping.setBusinessid(businessid);
		try {
			
			admpmp.insertSelective(advertisementMapping);
			int autoid = admpmp.selectlastid();
			for(String id: idArray) {
				advertisement = admp.selectByPrimaryKey(Integer.parseInt(id));
				System.out.println(advertisement.toString());
				advertisement.setBusinessid(businessid);
				advertisement.setId(Integer.parseInt(id));
				if(advertisement.getMappingid() == null || advertisement.getMappingid().length() <= 0) {
					advertisement.setMappingid("" + autoid);
				} else {
					advertisement.setMappingid(advertisement.getMappingid() + "," + autoid);
				}
				admp.updateForAddFilterToMultFile(advertisement);
			}
			json.put("code", "1");
			
		} catch(Exception e) {
			e.printStackTrace();
			json.put("code", "-1");
		}
		
		
		return json;
	}

}
