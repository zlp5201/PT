/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: ExcelToSql.java
 * Author:   DELL
 * Date:     2017-11-1 下午1:50:29
 */
package com.zhangliping.excel2sql;

import java.util.List;

import com.github.crab2died.ExcelUtils;

/**
 * excel转换成sql语句
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ExcelToSql {

    public final static String fileFullPath = "D:\\home\\会员自动注册实名处理.xlsx";
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
        
        List<UserInfo> userInfoList = ExcelUtils.getInstance().readExcel2Objects(
                fileFullPath, UserInfo.class,0,0);
        for (UserInfo userInfo : userInfoList) {
            System.out.println(userInfo);
        }
    }
}
