package com.zhangliping.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangliping.baseUtil.JsonUtil;
import com.zhangliping.sqladmin.bean.SqlAdminResult;
import com.zhangliping.sqladmin.bussiness.PreOrderBusiness;
import com.zhangliping.sqladmin.util.LocalRestUtil;
import com.zhangliping.sqladmin.util.PropertiesUtils;


/**
 * 
 * 生成预订单源数据入口
 * @author zhangliping
 *
 */
public class SqlAdminGenTool {
	
    private static final Logger LOG = LoggerFactory.getLogger(SqlAdminGenTool.class);
	
	/**
	 * 方法入口
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		
		PreOrderBusiness preOrderBusiness = new PreOrderBusiness();
		
		// 1、读取配置
		String COOKIE = "PHPSESSID=" + PropertiesUtils.getValue("PHPSESSID");
		String query_start_time = PropertiesUtils.getValue("query_start_time");
		String query_end_time = PropertiesUtils.getValue("query_end_time");
		String order_status = PropertiesUtils.getValue("order_status");
		String fileName =  PropertiesUtils.getValue("fileName");
		String sheetMaxCount =  PropertiesUtils.getValue("sheetMaxCount");
		
		
		
		// 2、生成sql语句进行select count
		String sqlAdminUrl = LocalRestUtil.SQL_ADMIN_URL;
		String sqlstart = preOrderBusiness.loadDynamicSQL();
		String sqlCount = String.format(sqlstart, "count(1)", query_start_time, query_end_time , "");
		
		
		// 3、查询count
		List<SqlAdminResult> resultList = new ArrayList<SqlAdminResult>();
		String sqlParamCount = LocalRestUtil.getTocSqlParam(sqlCount);
		Map<String,String> sqlHead = LocalRestUtil.getSqladminHead(COOKIE);
		Integer count = preOrderBusiness.getSearchResutCount(sqlAdminUrl, sqlHead, sqlParamCount);
		LOG.info("~~~~~~~~~~~~~~~~~~~~~~~~");
		LOG.info("当前查询总记录："+ count + "条。");
		// 4、根据count 每千条循环查询 然后进行汇总
		if (count > 0) {
			LOG.info("" + count/1000);
			for (int i = 0; i <= (count/1000); i++) {
				String limitStr = "limit ";
				if (i == 0) {
					limitStr += "0," + (i+1) * 1000;
				} else {
					limitStr += i * 1000 + "," + (i+1)*1000;
				}
				// 5、生成每千条的查询语句
				String sql = String.format(preOrderBusiness.loadDynamicSQL(), preOrderBusiness.loadDynamicFromSQL(), query_start_time, query_end_time, order_status, limitStr);
				String sqlParam = LocalRestUtil.getTocSqlParam(sql);
				List<SqlAdminResult> resultListtemp = preOrderBusiness.getSearchResut(sqlAdminUrl, sqlHead, limitStr, sqlParam);
				LOG.info(limitStr + "   有 " + resultListtemp.size() + " 条。");
				for (SqlAdminResult result : resultListtemp) {
					resultList.add(result);
				}
				resultListtemp.clear();
			}
		}
		
		// 6、汇总生成报表
		preOrderBusiness.genReport(resultList, fileName, Integer.valueOf(sheetMaxCount), Integer.valueOf(order_status));
	}
}

