/**
 * 
 */
package com.zhangliping.sqladmin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取工具类
 * @author zhangliping
 *
 */
public class PropertiesUtils {
	private static Properties prop = null;

	static {
//		tuniu
//		prop = new Properties();
//		InputStream config = PropertiesUtils.class
//				.getResourceAsStream("/puhui.properties");
//
//		try {
//			BufferedReader bf = new BufferedReader(new InputStreamReader(
//					config, "utf-8"));
//			prop.load(bf);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		prop = new Properties();
		InputStream config = PropertiesUtils.class
				.getResourceAsStream("/puhui.properties");

		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					config, "utf-8"));
			prop.load(bf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return prop.getProperty(key);
	}

	public static void SetValue(String key, String value) {
		prop.put(key, value);
	}
}
