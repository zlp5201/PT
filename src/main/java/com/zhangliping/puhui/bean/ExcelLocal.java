/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: ExcelLocal.java
 * Author:   DELL
 * Date:     2017-11-7 上午9:44:17
 */
package com.zhangliping.puhui.bean;

import java.util.Date;

import lombok.Data;

import com.github.crab2died.annotation.ExcelField;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class ExcelLocal {
 // 借款姓名
    @ExcelField(title = "借款姓名", order = 1)
    private String borrower;
//    @ExcelField(title = "服务费", order = 1)
//    private String serviceFee;
//    @ExcelField(title = "放款金额", order = 1)
//    private String tureTotal;
    @ExcelField(title = "Excel", order = 1)
    private String location;
}
