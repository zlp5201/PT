package com.zhangliping.contract;

/*
 * Copyright (C), 2010-2018, 未来域
 * FileName: ContractDto.java
 * Author:   DELL
 * Date:     2018-4-24 下午5:52:14
 */


import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Setter
@Getter
public class ContractDto {
    
    /**
     * 门店
     */
    @ContractAnnotation(value = "门店", maxLength=50)
    private String storeName;
    
    /**
     * 房间
     */
    @ContractAnnotation(value = "房间号", maxLength=20)
    private String roomNo;
    
    /**
     * 用户姓名
     */
    private String userName;
}
