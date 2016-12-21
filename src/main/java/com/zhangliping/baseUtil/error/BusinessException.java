/**
 * 
 */
package com.zhangliping.baseUtil.error;

import org.apache.log4j.spi.ErrorCode;

/**
 * @author zhangliping
 *
 */

/**
 * 业务异常
 * @author wangchong
 * 
 */
public class BusinessException extends RuntimeException {

    /**
     * 序列化使用
     */
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.toString(), cause);
        this.errorCode = errorCode;
    }


    /**
     * @return the errorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}