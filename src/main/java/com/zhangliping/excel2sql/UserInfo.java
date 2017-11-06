/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: UserInfo.java
 * Author:   DELL
 * Date:     2017-11-3 下午1:45:20
 */
package com.zhangliping.excel2sql;

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
public class UserInfo {
    //用户名
    @ExcelField(title = "序号", order = 1)
    private String id;
    @ExcelField(title = "手机号", order = 1)
    private String phone;
    
    @ExcelField(title = "身份证号", order = 1)
    private String id_no;
    
    @ExcelField(title = "姓名", order = 1)
    private String name;

}
