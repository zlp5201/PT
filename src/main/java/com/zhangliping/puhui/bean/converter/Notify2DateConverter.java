/*
 * Copyright (C), 2010-2017, 未来域
 * FileName: Notify2DateConverter.java
 * Author:   DELL
 * Date:     2017-11-3 下午5:34:06
 */
package com.zhangliping.puhui.bean.converter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.github.crab2died.converter.ReadConvertible;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Notify2DateConverter implements ReadConvertible {

    public Date execRead(String paramString) {
        String dateStr = (String) paramString;
        Calendar c = new GregorianCalendar(1900, 0, -1);
        Date d = c.getTime();
        Date value = addDays(d, Integer.valueOf(dateStr)); // 42605是距离1900年1月1日的天数
        return value;
    }
    
    
    
    

    public static Date addDays(Date date, int amount) {
      return add(date, 5, amount);
    }

    public static Date add(Date date, int calendarField, int amount) {
      if (date == null) {
        throw new IllegalArgumentException("The date must not be null");
      }
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.add(calendarField, amount);
      return c.getTime();
    }
}
