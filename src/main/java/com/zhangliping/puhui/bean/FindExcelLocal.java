/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: FindExcelLocal.java
 * Author:   DELL
 * Date:     2017-11-7 上午9:42:43
 */
package com.zhangliping.puhui.bean;

import java.util.ArrayList;
import java.util.List;

import com.github.crab2died.ExcelUtils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FindExcelLocal {

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
        // TODO Auto-generated method stub
        String fileFullPath = "D:\\home\\temp.xlsx";

        List<ExcelLocal> excelLocalList = ExcelUtils.getInstance().readExcel2Objects(
                fileFullPath, ExcelLocal.class,0,0);
        
        String name = "周开东";
        
        List<ExcelLocal> genLocalList = new ArrayList();
        ExcelLocal genLocal = null;
        for (ExcelLocal excelLocal : excelLocalList) {
            if (name.equals(excelLocal.getBorrower())) {
                genLocal = new ExcelLocal();
                genLocal.setBorrower(name);
                genLocal.setLocation(excelLocal.getLocation());
                genLocalList.add(genLocal);
            }
        }
        
        // 线下放款生成
        ExcelUtils.getInstance().exportObjects2Excel(genLocalList, ExcelLocal.class, true, "位置", true, "D:\\home\\位置.xlsx");
    }

}
