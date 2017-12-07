/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: ShellRunner.java
 * Author:   DELL
 * Date:     2017-12-7 下午7:14:37
 */
package com.zhangliping.excel.room;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.crab2died.ExcelUtils;
import com.google.common.collect.Lists;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ShellRunner {

    /**
     * 功能描述: 〈功能详细描述〉
     * 
     * @param args
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void main(String[] args) throws Exception {
        String output = "D:\\apache-maven-3.3.9\\";
        String fileName="room.xlsx";
        String outputFileName = "output.xlsx";
        List<DataInfo> userInfoList = ExcelUtils.getInstance().readExcel2Objects(output + fileName, DataInfo.class, 0, 0);

        Set keySet = new HashSet<String>();


        List<String> roomList = Lists.newArrayList();

        for (DataInfo dataInfo : userInfoList) {
            if (keySet.contains(dataInfo.getRoomNo())) {
                continue;
            } else {
                keySet.add(dataInfo.getRoomNo());
            }
        }
        
        
        List<DataInfo> resultList = Lists.newArrayList();
        DataInfo dataInfo = null;
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String str = it.next();
            dataInfo = new DataInfo();

            dataInfo.setRoomNo(str);
            StringBuilder sb = new StringBuilder();
            for (DataInfo temp : userInfoList) {
                if (temp.getRoomNo().equals(str)) {
                    sb.append(temp.getPhone()).append(",");
                }
            }
            if (sb.toString().contains(",")) {
                
                dataInfo.setPhone(sb.substring(0, sb.length()-1));
            } else {
                dataInfo.setPhone(sb.toString());
            }
            resultList.add(dataInfo);
        }
        
        // 线下放款生成
        ExcelUtils.getInstance().exportObjects2Excel(resultList, DataInfo.class, true, "room_phone", true, output + outputFileName);
    }

}
