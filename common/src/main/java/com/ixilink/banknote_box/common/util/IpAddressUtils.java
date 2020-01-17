package com.ixilink.banknote_box.common.util;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 获取IP工具类
 * @author 张俊
 * 2018年10月11日
 */
public class IpAddressUtils {


	/**
	 * 获取客户端的IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		System.out.println(ip);
		if("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
		if (ip.contains(",")) {
			return ip.split(",")[0];
		} else {
			return ip;
		}
	}
	public static String getIpAddress2(HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
		String forwarded = request.getHeader("X-Forwarded-For");
		String realIp = request.getHeader("X-Real-IP");
		String ip = null;
		if (realIp == null) {
			if (forwarded == null) {
				ip = remoteAddr;
			} else {
				ip = remoteAddr + "/" + forwarded.split(",")[0];
			}
		} else {
			if (realIp.equals(forwarded)) {
				ip = realIp;
			} else {
				if(forwarded != null){
					forwarded = forwarded.split(",")[0];
				}
				ip = realIp + "/" + forwarded;
			}
		}
		return ip;
	}

	 /** 解析ip地址 
	  * 设置访问地址为http://ip.taobao.com/service/getIpInfo.php
	  * 设置请求参数为ip=[已经获得的ip地址]
	  * 设置解码方式为UTF-8 
	  * @param content  请求的参数 格式为：ip=192.168.1.101
	  * @param encoding 服务器端请求编码。如GBK,UTF-8等
	  * @return
	*/
	public static String getAddresses(String content, String encoding) throws UnsupportedEncodingException {
		//设置访问地址
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		// 从http://whois.pconline.com.cn取得IP所在的省市区信息
		String returnStr = getResult(urlStr, content, encoding);
		if (returnStr != null) {
			// 处理返回的省市区信息
			// System.out.println(returnStr);
			String[] temp = returnStr.split(",");
			if (temp.length < 3) {
				return "0";// 无效IP，局域网测试
			}
			String country = ""; //国家
			String area = ""; //地区
			String region = ""; //省份
			String city = ""; //市区
			String county = ""; //地区
			String isp = ""; //ISP公司
			for (int i = 0; i < temp.length; i++) {
				switch (i) {
					case 2:
						country = (temp[i].split(":"))[1].replaceAll("\"", "");
						country = URLDecoder.decode(country, encoding);// 国家
						break;
					case 3:
						area = (temp[i].split(":"))[1].replaceAll("\"", "");
						area = URLDecoder.decode(area, encoding);// 地区
						break;
					case 4:
						region = (temp[i].split(":"))[1].replaceAll("\"", "");
						region = URLDecoder.decode(region, encoding);// 省份
						break;
					case 5:
						city = (temp[i].split(":"))[1].replaceAll("\"", "");
						city = URLDecoder.decode(city, encoding);// 市区
						if ("内网IP".equals(city)) {
							return "地址为：内网IP";
						}
						break;
					case 6:
						county = (temp[i].split(":"))[1].replaceAll("\"", "");
						county = URLDecoder.decode(county, encoding);// 地区
						break;
					case 7:
						isp = (temp[i].split(":"))[1].replaceAll("\"", "");
						isp = URLDecoder.decode(isp, encoding); // ISP公司
						break;
				}
			}
			return "地址为：" + country + "," + region + "省," + city + "市," + county + "," + "ISP公司：" + isp;
		}
		return null;
	}

	/**
	 * 访问目标地址并获取返回值
	 * @param urlStr   请求的地址
	 * @param content  请求的参数 格式为：ip=192.168.1.101
	 * @param encoding 服务器端请求编码。如GBK,UTF-8等
	 * @return
	 */
	private static String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(33000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuilder buffer = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	/**
	 * 获取当前机器的端口号
	 */
	public static String getLocalPort() {
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objectNames = null;
		try {
			objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
			Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
//		for (ObjectName o:Objects.requireNonNull(objectNames)){
//			System.out.println(o.getKeyProperty("port"));
//		}
		return objectNames != null ? objectNames.iterator().next().getKeyProperty("port") : null;
	}

	/**
	 * @return
	 * 获取当前机器的IP
	 */
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert addr != null;
		byte[] ipAddr = addr.getAddress();
		StringBuilder ipAddrStr = new StringBuilder();
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr.append(".");
			}
			ipAddrStr.append(ipAddr[i] & 0xFF);
		}
		return ipAddrStr.toString();
	}

	public static String getIp(){
		String addr = "";
		try {
			//获得本机IP
			addr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr;
	}

	/***
	 * 获取本机的外网ip地址
	 * @return
	 * */
	public static String getV4IP() {
		String ip = "";
		String chinaz = "http://ip.chinaz.com";
		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			while ((read = in.readLine()) != null) {
				inputLine.append(read).append("\r\n");
			}
		//System.out.println(inputLine.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if (m.find()) {
			ip = m.group(1);
			//System.out.println(ipstr);
		}
		return ip;
	}
}
