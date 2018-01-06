/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: DataInfo.java
 * Author:   DELL
 * Date:     2017-12-7 下午7:12:37
 */
package com.zhangliping.excel.room;

import com.github.crab2died.annotation.ExcelField;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class DataInfo {
    @ExcelField(title = "房间号", order = 1)
    private String roomNo;
    
    @ExcelField(title = "手机号", order = 1)
    private String phone;

	public DataInfo(String roomNo, String phone) {
		super();
		this.roomNo = roomNo;
		this.phone = phone;
	}

	public DataInfo() {
		super();
	}
}
