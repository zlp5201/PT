/**
 * 
 */
package com.zhangliping.sqladmin.bussiness;

/**
 * @author zhangliping
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhangliping.sqladmin.bean.SqlAdminResult;
import com.zhangliping.sqladmin.util.ExcelUtil;
import com.zhangliping.sqladmin.util.LocalRestUtil;

/**
 * @author zhangliping
 *
 */
public class PreOrderBusiness {
	
	/**
	 * 加载查询语句
	 * @return
	 */
	public String loadDynamicSQL()
	{
		File filePath = new File( PreOrderBusiness.class.getClassLoader().getResource( "" ).getPath());  
		String fileName = filePath + File.separator + "config"+ File.separator  + "query.properties";
		
		StringBuilder sb = new StringBuilder("");
		File myFile = new File(fileName);
		if (!myFile.exists()) {
			System.err.println("Can't Find " + fileName);
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(myFile));
			 String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 加载from部分的语句
	 * @return
	 */
	public String loadDynamicFromSQL()
	{
		File filePath = new File(PreOrderBusiness.class.getClassLoader().getResource( "" ).getPath());  
		String fileName = filePath + File.separator + "config"+ File.separator  + "from.properties";
		
		StringBuilder sb = new StringBuilder("");
		File myFile = new File(fileName);
		if (!myFile.exists()) {
			System.err.println("Can't Find " + fileName);
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(myFile));
			 String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 循环遍历查询每1000条数据后进行汇总
	 * @param sqlAdminUrl
	 * @param sqlHead
	 * @param limitStr
	 * @param sqlParam
	 * @return
	 * @throws Exception
	 */
	public List<SqlAdminResult> getSearchResut(String sqlAdminUrl, Map<String,String> sqlHead, String limitStr, String sqlParam) throws Exception
	{
		Gson gson = new Gson();
		String sqlResult = null;
		try {
			sqlResult = LocalRestUtil.sendData(sqlAdminUrl, "POST", sqlParam, 25000, sqlHead);
		} catch (Exception e) {
			System.out.println("您的浏览器无法连接sqlAdmin.请确认cookie是否已经过期或者sql语句是否有错误");
			e.printStackTrace();
			throw new Exception("请求出错，本次数据部正确。" + sqlParam);
		}
		
		if (null != sqlResult && sqlResult.indexOf("[") > 0) {

			String result = sqlResult.substring(sqlResult.indexOf("["), sqlResult.indexOf("]") + 1);
			List<SqlAdminResult> resultList = gson.fromJson(result,
					new TypeToken<List<SqlAdminResult>>() {
					}.getType());
			
			if (resultList.size() ==0) {
				System.out.println("m没有取到数据的execl " + sqlParam);
			}
			return resultList;
		} else {
			System.out.println("您的浏览器无法连接sqlAdmin.请确认cookie是否已经过期");
			throw new Exception("请求出错，本次数据部正确。" + sqlParam);
		}
	}
	
	/**
	 *  统计当前sql语句有多少条，还进行拆分查询
	 * @param sqlAdminUrl
	 * @param sqlHead
	 * @param sqlParam
	 * @return
	 * @throws Exception 
	 */
	public Integer getSearchResutCount(String sqlAdminUrl, Map<String,String> sqlHead, String sqlParam) throws Exception
	{
		Gson gson = new Gson();
		Integer count = 0;
		String result = "";
		String sqlResult = "";
		try {
			sqlResult = LocalRestUtil.sendData(sqlAdminUrl, "POST", sqlParam, 25000, sqlHead);
		} catch (Exception e) {
			System.out.println("您的浏览器无法连接sqlAdmin.请确认cookie是否已经过期或者sql语句是否有错误");
			e.printStackTrace();
			throw new Exception("请求出错，本次数据部正确。" + sqlParam);
		}
		if (null != sqlResult && sqlResult.indexOf("[") > 0) {

			result = sqlResult.substring(sqlResult.indexOf("[") + 1 ,
					sqlResult.indexOf("]"));
			Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>() { }.getType());
			
			count = Integer.parseInt(resultMap.get("count(1)"));
		}

		return count;
	}
	
	
	/**
	 * 生成报表
	 * @param resultList
	 */
	public void genReport(List<SqlAdminResult> resultList, String fileName, int sheetMaxCount, int orderStatusInt) {
		// 设置execl的标题及顺序
		Map<String, String> header = new HashMap<String, String>();
		header.put("00" + "order_id", "订单号");// 属性前边的数字代表字段的先后顺序。
		header.put("01" + "add_time", "下单时间");// 最好将源码中判别顺序的格式改为"序号-字段"。
		header.put("02" + "cust_id", "会员编号");
		header.put("03" + "order_status_name", "订单状态");
		header.put("04" + "update_time", "最后更新时间");
		header.put("05" + "extend_order_id", "转化订单号");
		header.put("06" + "add_uid", "取消人员");
		header.put("07" + "staff_name", "订单客服");
		header.put("08" + "book_city", "预订城市");
		header.put("09" + "product_id", "产品编号");
		header.put("10" + "product_class_name", "一级品类");
		header.put("11" + "product_child_class_name", "二级品类");
		header.put("12" + "dest_type_name", "目的地大类");
		header.put("13" + "dest_group_name", "目的地分组");
		header.put("14" + "dest_name", "目的地");
		header.put("15" + "total_price", "下单金额");

		// 生成Execl
		Map data = new HashMap();
		data.put("listData", resultList);
		data.put("header", header);
		
		ExcelUtil.objListToExcel(fileName, data, sheetMaxCount, orderStatusInt);
	}
}



