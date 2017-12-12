/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: ExcelToSql.java
 * Author:   DELL
 * Date:     2017-11-1 下午1:50:29
 */
package com.zhangliping.excel2sql;

import java.util.List;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.utils.DateUtils;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.util.DateUtil;

/**
 * excel转换成sql语句
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ExcelToSql {

    public final static String fileFullPath = "会员后台注册信息汇总表模板.xlsx";
    /**
     * 功能描述: 
     * 〈功能详细描述〉
     *
     * @param args
     * @throws Exception 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void main(String[] args) throws Exception {
//        String path = "D:\\new\\doc\\07.发布交付件\\01会员同步sql";
//        String deleteSqlTemplete = "delete from customer_data;";
//        String defaultSqlTemplete = "INSERT INTO customer_data (id,phone,id_no,name) values "
//                + "('%s','%s','%s','%s');";
//
//        List<UserInfo> userInfoList = ExcelUtils.getInstance().readExcel2Objects(fileFullPath, UserInfo.class, 0, 0);
//
//        String combomSql = genSqlSatement(deleteSqlTemplete, defaultSqlTemplete, userInfoList);
//        String fileName = "customer-reg-" + DateUtil.today() + ".sql";
//        FileUtil.writeUtf8String(combomSql, fileName);
        
        
        
    }
    /**
     * 功能描述: 
     * 〈功能详细描述〉
     *
     * @param deleteSqlTemplete
     * @param defaultSqlTemplete
     * @param userInfoList
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static String genSqlSatement(String deleteSqlTemplete, String defaultSqlTemplete, List<UserInfo> userInfoList) {
        StringBuilder combomSql = new StringBuilder();
        combomSql.append(deleteSqlTemplete).append("\r\n");

        for (UserInfo userInfo : userInfoList) {
            String sqlFormat = String.format(defaultSqlTemplete, userInfo.getId(), userInfo.getPhone(),
                    userInfo.getIdNo(), userInfo.getName());
            combomSql.append(sqlFormat).append("\r\n");
        }
        return combomSql.toString();
    }
}
