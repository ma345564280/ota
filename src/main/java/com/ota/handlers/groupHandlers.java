package com.ota.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BoxMapper;
import com.ota.dao.BusinessMapper;
import com.ota.dao.CustomerGroupMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.domain.CustomerGroup;
import com.ota.domain.CustomerGroupDetail;
import com.ota.domain.OTAGroup;

@Controller
public class groupHandlers {

	@Autowired
	CustomerGroupMapper customerGroupMapper;
	@Autowired
	BusinessMapper businessMapper;
	@Autowired
	OTAGroupMapper otaGroupMapper;
	@Autowired
	BoxMapper BoxMapper;

	@RequestMapping("customer-info-indevicegroup")
	@ResponseBody
	public List<CustomerGroupDetail> customerInfo() {
		List<CustomerGroupDetail> customerGroups = customerGroupMapper.selectAllCustomerNameAndGroupName();
		return customerGroups;
	}

	@RequestMapping("query-sort")
	@ResponseBody
	public List<Integer> querySort(@RequestBody JSONObject jsonRec) {
		System.out.println("querySort.");
		List<Integer> ret = new ArrayList<>();
		String customer = jsonRec.getString("customer");
		int customerid = businessMapper.selectBusinessidByName(customer);
		List<Integer> groupids = customerGroupMapper.selectGroupIDByCustomerID(customerid);
		System.out.println("groupids: " + groupids.size());
		if(groupids.size() == 0) {
			return null;
		}
		List<Integer> sortList = new ArrayList<Integer>();
		for (int i : groupids) {
			sortList.add(otaGroupMapper.selectSortByGroupid(i));
		}
		
		Collections.sort(sortList);
		
		int maxGroupSort = sortList.get(sortList.size() - 1);
		System.out.println("maxGroupSort: " + maxGroupSort);
		for (int i = 1; i <= maxGroupSort + 1; i++) {
			if (sortList.contains(i))
				continue;
			else
				ret.add(i);
		}
		return ret;

	}

	@RequestMapping(value = "check-newgroup")
	@ResponseBody
	public JSONObject checkNewGroup(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		String customer = jsonRec.getString("name");
		String groupname = jsonRec.getString("groupname");

		Integer customerid = businessMapper.selectBusinessidByName(customer);
		Integer groupid = otaGroupMapper.selectGroupIDByGroupName(groupname);
		if ((groupid + "").equals("")) {
			json.put("code", "1");
			return json;
		}
		System.out.println("customer: " + customer);
		System.out.println("groupname: " + groupname);

		Map<String, Integer> map = new HashMap<>();
		map.put("customerid", customerid);
		map.put("groupid", groupid);
		int count = customerGroupMapper.checkNewGroup(map);
		System.out.println(count);
		if (count == 0) {
			json.put("code", "1");
		} else {
			json.put("code", "-1");
		}
		return json;
	}

	@RequestMapping(value = "add-group")
	@ResponseBody
	public JSONObject addGroup(@RequestBody CustomerGroupDetail customerGroupDetail) {
		JSONObject json = new JSONObject();
		String groupname = customerGroupDetail.getGroupname();
		String customer = customerGroupDetail.getName();
		CustomerGroup customerGroup = new CustomerGroup();
		int customerid = businessMapper.selectBusinessidByName(customer);

		customerGroup.setCustomerid(customerid);
		try {
			OTAGroup newOTAGroup = new OTAGroup();
			newOTAGroup.setGroupname(groupname);
			newOTAGroup.setDescription(customerGroupDetail.getDescription());
			newOTAGroup.setSort(customerGroupDetail.getSort());
			otaGroupMapper.insertSelective(newOTAGroup);
			customerGroup.setGroupid(newOTAGroup.getId());
			System.out.println("xinid: " + newOTAGroup.getId());
			customerGroupMapper.insert(customerGroup);
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "update-group")
	@ResponseBody
	public JSONObject editGroup(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();

		String id = jsonRec.getString("id");
		CustomerGroup customerGroup = customerGroupMapper.selectByPrimaryKey(Integer.parseInt(id));
		Integer groupid = customerGroup.getGroupid();
		String customer = jsonRec.getString("customer");
		String groupname = jsonRec.getString("groupname");
		String sort = jsonRec.getString("sort");
		String description = jsonRec.getString("description");

		OTAGroup editOTAGroup = new OTAGroup();
		editOTAGroup.setGroupname(groupname);
		editOTAGroup.setDescription(description);
		editOTAGroup.setId(groupid);
		editOTAGroup.setSort(Integer.parseInt(sort));
		try {
			otaGroupMapper.updateByPrimaryKeySelective(editOTAGroup);
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "delete-group")
	@ResponseBody
	public JSONObject deleteGroup(@RequestBody CustomerGroup customerGroup) {
		JSONObject json = new JSONObject();
		try {
			customerGroup = customerGroupMapper.selectByPrimaryKey(customerGroup.getId());
			customerGroupMapper.deleteByPrimaryKey(customerGroup.getId());
			HashMap<String, Integer> map = new HashMap<>();
			map.put("businessid", customerGroup.getCustomerid());
			map.put("groupid", customerGroup.getGroupid());
			BoxMapper.deleteByCustomerIdAndGroupId(map);
			otaGroupMapper.deleteByPrimaryKey(customerGroup.getGroupid());
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}
}
