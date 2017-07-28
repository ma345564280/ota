package com.ota.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ota.dao.BoxMapper;
import com.ota.dao.BoxTypeMapper;
import com.ota.dao.BusinessMapper;
import com.ota.dao.CustomerGroupMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.domain.Box;
import com.ota.domain.BoxType;
import com.ota.domain.BoxWithStatus;
import com.ota.domain.Business;
import com.ota.tools.OTAToolKit;

@Controller
public class DeviceHandlers {
	@Autowired
	BoxMapper boxMapper;
	@Autowired
	BoxTypeMapper boxTypeMapper;
	@Autowired
	HttpSession session;
	@Autowired
	BusinessMapper businessMapper;
	@Autowired
	CustomerGroupMapper customerGroupMapper;
	@Autowired
	OTAGroupMapper otaGroupMapper;

	@RequestMapping("deviceinfo")
	@ResponseBody
	public Map<String, Object> statistic(HttpServletRequest request) {
		// String businessid = (String) session.getAttribute("businessid");
		int offset = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String customer = request.getParameter("customer");
		String group = request.getParameter("group");
		String model = request.getParameter("model");
		System.out.println("customer:" + customer);
		System.out.println("group:" + group);
		System.out.println("model:" + model);
		Integer businessid;
		Integer groupid = null;
		Integer modelid;

		if (customer.equals("All Customer"))
			businessid = null;
		else {
			businessid = businessMapper.selectBusinessidByName(customer);
		}
		if (group.equals("All Group")) {
			groupid = null;
		} else {
			groupid = otaGroupMapper.selectGroupIDByGroupName(group);
		}
		if (model.equals("All Model"))
			modelid = null;
		else {
			modelid = boxTypeMapper.selectModelIDByType(model);
		}

		String status = request.getParameter("status");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("businessid", businessid);
			map.put("groupid", groupid);
			map.put("modelid", modelid);
			PageBounds pageBounds = OTAToolKit.getPagerBoundsByParameter(pageSize, offset);
			List<Box> boxlist;
			List<BoxWithStatus> boxWithStatusList = new ArrayList<>();
			int total;
			if (status.equals("Online")) {
				boxlist = boxMapper.selectOnlineBoxWithFilter(map, pageBounds);
				getBoxWithStatusList(boxlist, status, boxWithStatusList);
				total = boxMapper.countOnlineWithFilter(map);
			} else if (status.equals("Offline")) {
				boxlist = boxMapper.selectOfflineBoxWithFilter(map, pageBounds);
				getBoxWithStatusList(boxlist, status, boxWithStatusList);
				total = boxMapper.countOfflineWithFilter(map);
			} else {
				boxlist = boxMapper.selectOnlineBoxWithFilter(map, pageBounds);
				getBoxWithStatusList(boxlist, status, boxWithStatusList);
				List<Box> boxlistOffline = boxMapper.selectOfflineBoxWithFilter(map, pageBounds);
				getBoxWithStatusList(boxlistOffline, status, boxWithStatusList);
				total = boxMapper.countOfflineWithFilter(map) + boxMapper.countOnlineWithFilter(map);
			}
			Map<String, Object> mapSumAndList = new HashMap<>();
			if (boxlist != null) {
				mapSumAndList.put("total", total);
				mapSumAndList.put("rows", boxWithStatusList);
			}
			return mapSumAndList;
			// return boxlist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private void getBoxWithStatusList(List<Box> boxlist, String status, List<BoxWithStatus> boxWithStatusList) {
		for (Box singleBox : boxlist) {
			Integer businessid = singleBox.getBusinessid();
			Integer groupid = singleBox.getGroupid();
			Integer modelid = singleBox.getModelid();
			String customertmp = null;
			String groupnametmp = null;
			String modeltmp = null;
			if (businessid != null)
				customertmp = businessMapper.selectByPrimaryKey(businessid).getName();
			if (groupid != null)
				groupnametmp = otaGroupMapper.selectGroupnameByPrimaryKey(groupid);
			if (modelid != null)
				modeltmp = boxTypeMapper.selectByPrimaryKey(modelid).getType();
			boxWithStatusList.add(OTAToolKit.box2BoxWithStaus(singleBox, status, customertmp, groupnametmp, modeltmp));
		}
	}

	@RequestMapping("customer-info")
	@ResponseBody
	public List<Business> queryCustomerInfo() {
		List<Business> customerList = businessMapper.selectAllCustomer();
		return customerList;
	}

	@RequestMapping("model-info")
	@ResponseBody
	public List<BoxType> queryModelInfo() {
		List<BoxType> boxTypeList = boxTypeMapper.selectAllType();
		return boxTypeList;
	}

	@RequestMapping("group-info")
	@ResponseBody
	public List<String> queryGroupInfo(HttpServletRequest request) {
		String customer = request.getParameter("customer");
		if (customer.equals("All Customer") || (customer + "").equals("")) {
			return otaGroupMapper.selectAllGroupname();
		}
		System.out.println("edit:" + customer);
		int businessid = businessMapper.selectBusinessidByName(customer);
		List<Integer> groupidList = customerGroupMapper.selectGroupIDByCustomerID(businessid);
		List<String> groupnameList = new ArrayList<>();
		for (int id : groupidList) {
			groupnameList.add(otaGroupMapper.selectGroupnameByPrimaryKey(id));
		}
		return groupnameList;
	}

	@RequestMapping("delete-Box")
	@ResponseBody
	public JSONObject deleteBox(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		String mac = jsonRec.getString("mac");
		try {
			boxMapper.deleteByPrimaryKey(mac);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping("edit-Box")
	@ResponseBody
	public JSONObject editBox(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		String mac = jsonRec.getString("mac");
		String userid = jsonRec.getString("userid");
		String romversion = jsonRec.getString("romversion");

		String customer = jsonRec.getString("customer");
		String groupname = jsonRec.getString("group");
		String type = jsonRec.getString("model");
		Integer businessid = businessMapper.selectBusinessidByName(customer);
		Integer groupid = otaGroupMapper.selectGroupIDByGroupName(groupname);
		Integer modelid = boxTypeMapper.selectModelIDByType(type);

		Box box = new Box();
		box.setMac(mac);
		box.setUserid(userid);
		box.setRomversion(romversion);
		box.setBusinessid(businessid);
		box.setGroupid(groupid);
		box.setModelid(modelid);

		System.out.println(box.toString());
		try {
			boxMapper.updateByPrimaryKeySelectiveWithoutTime(box);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping("boxrequest")
	@ResponseBody
	public JSONObject onlineRecord(HttpServletRequest jsonRec) {
		JSONObject json = new JSONObject();
		String mac = jsonRec.getParameter("Mac");
		String type = jsonRec.getParameter("Type");
		String romversion = jsonRec.getParameter("RomVersion");
		String appversion = jsonRec.getParameter("ApkVersion");
		String hardwareversion = jsonRec.getParameter("HardwareVersion");
		String manufacturer = jsonRec.getParameter("Manufacturer");
		String userid = jsonRec.getParameter("UserID");
		Boolean dvbsupport = Boolean.parseBoolean(jsonRec.getParameter("DVBSupport"));
		String homeui = jsonRec.getParameter("HomeUI");
		
		Integer modelid = boxTypeMapper.selectModelIDByType(type);
		Box box = new Box();

		box.setMac(mac);
		box.setModelid(modelid);
		box.setRomversion(romversion);
		box.setUserid(userid);
		box.setHardwareversion(hardwareversion);
		box.setDvbsupport(dvbsupport);
		box.setManufacturer(manufacturer);
		box.setHomeui(homeui);
		box.setAppversion(appversion);
//http://localhost:8080/ota/boxrequest?Type=Etimo@T2&RomVersion=1.0.4&ApkVersion=20170712_androidM_DVB_sample&HardwareVersion=V1.0&Manufacturer=Amlogic&DVBSupport=true&HomeUI=&Mac=22:22:22:11:11:55&UserID=201100001111
		try {
			boxMapper.updateByPrimaryKeySelective(box);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

}
