/*
 * Copyright (C), 2010-2018, 未来域
 * FileName: ContractAnnotation.java
 * Author:   DELL
 * Date:     2018-4-24 下午5:40:17
 */
package com.zhangliping.contract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.TYPE })
public @interface ContractAnnotation {
    
    String value() default "";
    
    int maxLength() default 5;
}
