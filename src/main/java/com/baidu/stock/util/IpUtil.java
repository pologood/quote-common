package com.baidu.stock.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpUtil {

	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	private static String host;

	/**
	 * 取当前系统站点本地地址 linux下 和 window下可用 add by RWW
	 * 
	 * @return
	 */
	public static String getLocalIP() {
		if (!StringUtils.isEmpty(host)) {
			return host;
		}
		String sIP = "null";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取本机地址异常!", e);
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
			host = sIP;
		}
		return sIP;
	}

	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
}
