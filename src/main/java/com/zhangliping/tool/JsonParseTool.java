package com.zhangliping.tool;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.zhangliping.baseUtil.JsonUtil;
import com.zhangliping.baseUtil.ResponseVo;
import com.zhangliping.sqladmin.bean.SqlAdminResult;

/***
 * 本类用来加载远程接口的不同返回结果，结果配置在META-INFO/jsons/下的文件中
 * 不同的接口配置不同的文件，这样可以根据不同的返回结果测试各种业务场景
 * @author yuguangying
 *
 */
public class JsonParseTool {

    //缓存文件中不同的json
    private static Map<String, String> JSON_CACHE_MAP = new HashMap<String, String>();

    //文件路径
    private static String SERVICE_PATH = "META-INF/jsons/";

    /***
     * 获取json中的json字符串
     * @param name 文件名称
     * @param key 不同json字符串对应的key
     * @return 返回key对应的json字符串
     */
    public static String getJson(String name) {

        //缓存中有直接从缓存中取
//      if (!JSON_CACHE_MAP.isEmpty()) {
//          return JSON_CACHE_MAP.get(key);
//      }
        String jsonKey = "key";
        //到文件中取读取
        String fileName = SERVICE_PATH + name;
        Enumeration<URL> urls = null;
        //获取ClassLoader
        ClassLoader cl = getClassLoader();
        try {
            if (cl != null) {
                urls = cl.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }
            
            if (urls != null) {
                //读取文件
                while (urls.hasMoreElements()) {
                    URL url = (URL) urls.nextElement();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(),  "utf-8"));
                    String line = null;
                    
                    String value = "";
                    try {
                        while ((line = reader.readLine()) != null) {
                            int index = line.indexOf("@");
                            if (index > 0) {
                                jsonKey = line.substring(0, index).trim();
                                value = line.substring(index + 1);
                            } else {
                                value += line;
                            }
                        }
                    } finally {
                        reader.close();
                    }
                    if (value == null || value.trim().equals("")) {
                        throw new IllegalArgumentException("value is null");
                    }
                    JSON_CACHE_MAP.put(jsonKey, value);
                }
            }
            
        } catch (IOException e) {
            throw new IllegalArgumentException("file:" + fileName + " not found");
        }
        
        return JSON_CACHE_MAP.get(jsonKey);
    }

    private static ClassLoader getClassLoader() {

        ClassLoader loader = null;
        
        //获取当前线程ClassLoader，
        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {
            //无法获取当前线程ClassLoader
        }
        
        //当前线程ClassLoader不存在
        if (loader == null) {
            loader = JsonParseTool.class.getClassLoader();
            //ClassLoader为BootStrap ClassLoader
            if (loader == null) {
                try {
                    loader = ClassLoader.getSystemClassLoader();
                } catch (Exception e) {
                    //无法访问系统ClassLoader,此时的系统ClassLoader可能就是BootStrap ClassLoader
                }
            }
        }

        return loader;
    }
    
    public static void main(String[] args) {
        String json = getJson("json.json");
        
        
        ResponseVo responseVo=JsonUtil.toBean(json, ResponseVo.class);
        SqlAdminResult result=JsonUtil.toBean(responseVo.getData(), SqlAdminResult.class);
        
        System.out.println(result);
    }
}

