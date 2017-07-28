package com.ota.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.ota.dao.BoxTypeMapper;
import com.ota.dao.BusinessMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.domain.Box;
import com.ota.domain.BoxWithStatus;

public class OTAToolKit {
	@Autowired
	OTAGroupMapper otaGroupMapper;
	@Autowired
	BoxTypeMapper boxTypeMapper;
	@Autowired
	BusinessMapper businessMapper;
	public static String string2StandardFormOfTimeString(String string) {
		String year = string.substring(0, 4);
		String month = string.substring(4, 6);
		String day = string.substring(6, 8);
		String hour = string.substring(8, 10);
		String minute = string.substring(10, 12);
		String second = string.substring(12, 14);
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
	}

	public static String getSystemTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());
	}
	
	public static String getSystemDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		return df.format(new Date());
	}

	public static BoxWithStatus box2BoxWithStaus(Box box, String status,String customer,String groupname,String model) {
		BoxWithStatus boxWithStatus = new BoxWithStatus();

		boxWithStatus.setMac(box.getMac());
		boxWithStatus.setBusinessid(""+box.getBusinessid());
		boxWithStatus.setAppversion(box.getAppversion());
		boxWithStatus.setCustomer(customer);
		boxWithStatus.setDvbsupport(box.getDvbsupport());
		boxWithStatus.setGroupname(groupname);
		boxWithStatus.setHardwareversion(box.getHardwareversion());
		boxWithStatus.setHomeui(box.getHomeui());
		boxWithStatus.setManufacturer(box.getManufacturer());
		boxWithStatus.setRomversion(box.getRomversion());
		boxWithStatus.setStatus(status);
		boxWithStatus.setType(model);
		boxWithStatus.setUserid(box.getUserid());
		return boxWithStatus;
	}

	public static Object pagelistToJSONMapNew(PageList list) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (list != null) {
			Paginator paginator = list.getPaginator();
			map.put("total", paginator.getTotalCount());
			map.put("rows", new ArrayList(list));
		}
		return map;
	}

	public static PageBounds getPagerBoundsByParameter(int pageSize, int offset) {
		if (pageSize == 0) {
			return null;
		}

		PageBounds pageBounds = new PageBounds(offset / pageSize + 1, pageSize);
		return pageBounds;
	}

	public static String getWebsiteDatetime(String webUrl) {
		try {
			URL url = new URL(webUrl);// 取得资源对象
			URLConnection uc = url.openConnection();// 生成连接对象
			uc.connect();// 发出连接
			long ld = uc.getDate();// 读取网站日期时间
			Date date = new Date(ld);// 转换为标准时间对象
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf.format(date);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static String tranferToMac(String macString) {
		StringBuilder sb = new StringBuilder();
		if (macString.length() < 12) {
			for (int j = 1; j <= 12 - macString.length(); j++) {
				sb.append("0");
			}
		}

		sb.append(macString);

		for (int i = 2; i < 16; i += 3) {
			sb.insert(i, ':');
		}
		String re = new String(sb);
		re = re.toUpperCase();
		return re;
	}

	public static String addTimeStamp(String filename) {
		if (filename.length() <= 0)
			return null;

		StringBuilder temp = new StringBuilder(filename);
		int lastdot = temp.indexOf(".");
		temp.insert(lastdot, System.currentTimeMillis());
		return new String(temp);
	}

	public static String getAddr(String serverAddress, int serverPort, String downloadUrlInDataBase) {
		StringBuffer downloadUrl = new StringBuffer("http://");
		downloadUrlInDataBase = downloadUrlInDataBase.replace("\\", "/");
		downloadUrl.append(serverAddress);
		downloadUrl.append(":");
		downloadUrl.append(serverPort);
		downloadUrl.append("/ota/");
		downloadUrl.append(downloadUrlInDataBase);

		return new String(downloadUrl);
	}

	public static boolean matchFromLeft(String matcher, String matcherPattern) {
		boolean ismatched = true;

		if ((matcherPattern != null) && (matcherPattern.length() > 0)) {
			if (matcher.length() <= 0)
				return false;
			char[] matchPatternArray = matcherPattern.toCharArray();
			char[] matcherArray = matcher.toCharArray();
			for (int i = 0; i < matchPatternArray.length; i++) {
				if (matcherArray[i] != matchPatternArray[i]) {
					ismatched = false;
					break;
				}
			}
		}
		return ismatched;
	}

	public static String deleteFilterIdInFile(String mappingIDInFile, String filterID) {

		String[] mappingStringArray = mappingIDInFile.split(",");
		StringBuilder temp = new StringBuilder();
		for (String singleMappingID : mappingStringArray) {
			if (singleMappingID.equals(filterID))
				continue;
			temp.append(singleMappingID);
			temp.append(",");
		}
		int indexOfLastComma = temp.lastIndexOf(",");
		if (indexOfLastComma != -1)
			temp.deleteCharAt(indexOfLastComma);

		return new String(temp);
	}

	public static String insertFilterIDIntoFileMappingID(String mappingIDInFile, String filterID) {
		StringBuilder temp = new StringBuilder();

		return new String(temp);

	}

	public static String getOSUrl(String path, String businessName) {
		Properties prop = System.getProperties();
		String osname = prop.getProperty("os.name");
		if (osname.startsWith("win") || osname.startsWith("Win")) {
			path = path + "\\" + businessName;
		} else {
			path = path + "/" + businessName;
		}

		return path;
	}

	public static String getOSUrl(String path, String businessName, String filename) {
		Properties prop = System.getProperties();
		String osname = prop.getProperty("os.name");
		if (osname.startsWith("win") || osname.startsWith("Win")) {
			path = path + "\\" + businessName + "\\" + filename;
		} else {
			path = path + "/" + businessName + "/" + filename;
		}

		return path;
	}

	public static String getAddr(int serverPort, String downloadUrlInDataBase) throws UnknownHostException {
		StringBuffer downloadUrl = new StringBuffer("http://");
		InetAddress address = InetAddress.getLocalHost();
		String serverAddress = address.getHostAddress();
		downloadUrlInDataBase = downloadUrlInDataBase.replace("\\", "/");
		downloadUrl.append(serverAddress);
		downloadUrl.append(":");
		downloadUrl.append(serverPort);
		downloadUrl.append("/ota/");
		downloadUrl.append(downloadUrlInDataBase);

		return new String(downloadUrl);
	}
}
