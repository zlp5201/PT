package com.zhangliping.baseUtil.error;

/*
 * Date: 2014-11-3
 * Description:ErrorCode.java 
 */

/**
 * 错误码定义接口</br><strong>BOSS3.0 错误码值范围 1700000~1799999</strong>
 * 基础公共模块错误码范围1700001~1709999
 * 订单模块错误码范围1710000~1720000
 * @author fengxuekui
 * 
 */
public interface OrderErrorCode {

    /**
     * 成功error code
     */
    public static final int ERROR_CODE_SUCCESS = 1700000;

    int getErrorCode();

    /**
     * 获取错误码范围-最小值
     * @return 错误码最小值
     */
    int getErrorCodeMinRange();

    /**
     * 获取错误码范围-最大值
     * @return 错误码最大值
     */
    int getErrorCodeMaxRange();

}

