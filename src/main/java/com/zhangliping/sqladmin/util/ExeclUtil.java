/**
 * 
 */
package com.zhangliping.sqladmin.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zhangliping.sqladmin.bean.SqlAdminResult;

/**
 * @author zhangliping
 *
 */
public class ExeclUtil {
	/**
	 * 
	 * @param name Excel保存的主题名
	 * @param data 里边有Map和List Map存放字段对应关系(ziDuan,字段的第一个字符是序号)
	 *            List存放对象数据(listData)
	 * @return [0]是fileName [1]是filePath
	 */
	public static String[] objListToExcel(String fileName, Map data, int sheetMaxCount, int orderStatusInt) {
	    Map<String, String> header = (Map<String, String>) data.get("header");
	    List<SqlAdminResult> listData = (List) data.get("listData");
	    Object[] keys = header.keySet().toArray();
	    String[] ziDuanKeys = new String[keys.length];
	    for (int k = 0; k < keys.length; k++) {
	        String temp = keys[k].toString();
	        int xuHao = Integer.valueOf(temp.substring(0, 2));
	        ziDuanKeys[xuHao] = temp.substring(2);
	    }
	    try {
	        String path = reName(fileName, orderStatusInt);
	        File newFile = new File(path);
	        newFile.createNewFile();
	        System.out.println("创建文件成功：" + path);
	        HSSFWorkbook wb = new HSSFWorkbook();
	        
	        // 由于每天请求的数据可能超过execl的sheet的最大限制65535，一页存放不下，所以拆分成多个execl
	        List[] list = splitList(listData, sheetMaxCount);
	        
	        for (int k = 0; k < list.length; k++) {
	        	 HSSFSheet sheet = wb.createSheet("resut" + k);
	        	 List<SqlAdminResult> listtemp = list[k];
	        	 HSSFRow row = sheet.createRow(0);
	        	 HSSFCell cell = null;
	        	 
	        	 // 处理head部的内容
	        	  for (int j = 0; j < ziDuanKeys.length; j++) {
	        		  cell = row.createCell(j);
	                sheet.setColumnWidth(j, 6000);
	                cell.setCellValue(new HSSFRichTextString(header.get(fillDouble(j)
	                + ziDuanKeys[j])));
	        	  }
	        	  
	        	 // 开始处理数据
	 	        for (int i = 0; i < listtemp.size(); i++) {
	 	            row = sheet.createRow(i + 1);
	 	            Object obj = listtemp.get(i);
	 	            
	 	            for (int j = 0; j < ziDuanKeys.length; j++) {
	 	                cell = row.createCell(j);
	 	                    String ziDuanName = (String) ziDuanKeys[j];
	 	                    ziDuanName = ziDuanName.replaceFirst(ziDuanName
	 	                    .substring(0, 1), ziDuanName.substring(0, 1)
	 	                    .toUpperCase());
	 	                    ziDuanName = "get" + ziDuanName;
	 	                    Class clazz = Class.forName(obj.getClass().getName());
	 	                    Method[] methods = clazz.getMethods();
	 	                    Pattern pattern = Pattern.compile(ziDuanName);
	 	                    Matcher mat = null;
	 	                    for (Method m : methods) {
	 	                        mat = pattern.matcher(m.getName());
	 	                        if (mat.find()) {
	 	                        Object shuXing = m.invoke(obj, null);
	 	                        if (shuXing != null) {
	 	                            cell.setCellValue(shuXing.toString());//这里可以做数据格式处理
	 	                        } else {
	 	                            cell.setCellValue("");
	 	                        }
	 	                        break;
	 	                    }
	 	                }
	 	            }
	 	    }
		}
	        
	       
	    OutputStream out = new FileOutputStream(path);
	    wb.write(out);// 写入File
	    out.flush();
	    out.close();
	    return null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public static String reName(String fileName, Integer orderStatusInt)
	{
		String suffix = "";
		switch (orderStatusInt) {
		case 1:
			suffix = "(处理中)";
			break;
		case 2:
			suffix = "(已处理)";
			break;
		case 3:
			suffix = "(已取消)";
			break;
		default:
			break;
		}
		
		String newName = fileName.substring(0, fileName.indexOf("."));
		
		newName = newName + suffix + fileName.substring(fileName.indexOf("."), fileName.length());
		return newName;
	}
	
	public static String fillDouble(int j)
	{
		if (j < 10)
		{
			return "0" +  j;
		}
		return "" + j;
	}
	
	//list: 要拆分的列表  
	//pageSize: 每个子列表条数  
	public static List[] splitList(List<SqlAdminResult> list, int pageSize) {  
	    int total = list.size();  
	    //总页数  
	    int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;  
	    List[] result = new List[pageCount];  
	    for(int i = 0; i < pageCount; i++) {  
	        int start = i * pageSize;  
	        //最后一条可能超出总数  
	        int end = start + pageSize > total ? total : start + pageSize;  
	        List subList = list.subList(start, end);  
	        result[i] = subList;  
	    }  
	    return result;  
	}  
}
