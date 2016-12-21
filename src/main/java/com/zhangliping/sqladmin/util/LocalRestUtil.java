/**
 * 
 */
package com.zhangliping.sqladmin.util;

/**
 * @author zhangliping
 *
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LocalRestUtil {
	/**
	 * sqladmin的请求路径
	 */
	public static final String SQL_ADMIN_URL = "http://sqladmin.tuniu.org/show/execute.php?action=get_result";
	/**
	 * toc查询参数拼装模板
	 */
	public static String TOC_TEMPLET = "server_id=2437&instance_id=2438&DbId=2439&sql_str=";
	/**
	 * 发送请求
	 * @param questUrl 请求地址
	 * @param method  请求方式 GET/POST
	 * @param param  请求参数
	 * @param timeOut  超时时间
	 * @param requestHeadInfo  头信息（如果需要的话）
	 * @return
	 * @throws Exception
	 */
	public static String sendData(String questUrl, String method, String param, Integer timeOut, Map<String,String> requestHeadInfo) throws Exception{
		StringBuffer result = new StringBuffer();
		OutputStream os = null;
		InputStream is =null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try{
			String urlParam =null;
			if("GET".equals(method)) {
				urlParam = questUrl + "?" + param;
			}else if ("POST".equals(method)) {
				urlParam = questUrl;
			}else{
				return "请传入正确的请求方式";
			}
			URL url = new URL(urlParam);
			URLConnection con = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection)con;
			httpCon.setRequestMethod(method);
			httpCon.setConnectTimeout(3000);
			httpCon.setReadTimeout(timeOut);
			//设置头信息
			if(null != requestHeadInfo && !requestHeadInfo.isEmpty()) {
				Set<String> keySet = requestHeadInfo.keySet();
				for(String headInfo : keySet){
					httpCon.setRequestProperty(headInfo, requestHeadInfo.get(headInfo));
				}
			}
			if("POST".equals(method)){
				httpCon.setDoOutput(true);
				httpCon.setDoInput(true);
				os = httpCon.getOutputStream();
				byte[] bytes = param.getBytes("UTF-8");
				os.write(bytes);
			}
			is = httpCon.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = null;
			while((line = br.readLine()) != null){
				result.append(line);
			}
			
			return result.toString();
		}catch(Exception e){
			e.printStackTrace();
			return e.toString();
		}finally{
			if(null != os) {
				os.close();
			}
			if(null != br) {
				br.close();
			}
			if(null != isr) {
				isr.close();
			}
			if(null != is) {
				is.close();
			}
		}
	} 
	/**
	 * 根据cookie组装默认的sqladmin连接配置
	 * @param cookie
	 * @return
	 */
	public static Map<String, String> getSqladminHead(String cookie){
		if(null == cookie || "".equals(cookie.trim())){
			return null;
		}
		Map<String,String> headMap = new HashMap<String,String>();
		headMap.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		//headMap.put("Accept-Encoding", "gzip, deflate");
		headMap.put("Cookie",cookie);
		headMap.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		headMap.put("Connection","keep-alive");
		//headMap.put("X-Requested-With","XMLHttpRequest");
		headMap.put("Host","sqladmin.tuniu.org");
		headMap.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headMap.put("Referer","http://sqladmin.tuniu.org/show/execute.php?action=ExecuteDbSql&DbId=2439");
		return headMap;
	}
	
	/**
	 * 根据sql语句生成查询toc的参数
	 * @param sql
	 * @return
	 */
	public static String getTocSqlParam(String sql){
		return TOC_TEMPLET + URLEncoder.encode(sql);
	} 
	
}
