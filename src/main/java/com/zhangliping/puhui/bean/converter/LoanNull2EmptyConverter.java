/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: Notify2DateConverter.java
 * Author:   DELL
 * Date:     2017-11-3 下午5:34:06
 */
package com.zhangliping.puhui.bean.converter;


import com.github.crab2died.converter.WriteConvertible;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class LoanNull2EmptyConverter implements WriteConvertible {

    public Object execWrite(Object object) {
        // TODO Auto-generated method stub
        if (object == null) {
            return "";
        }
        return object;
    }


}
