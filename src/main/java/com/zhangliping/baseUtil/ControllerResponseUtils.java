package com.zhangliping.baseUtil;

/*
 * ControllerResponseUtils.java
 * Copyright (c) 2014 tuniu.com
 */

import org.omg.CORBA.SystemException;

import com.xiaoleilu.hutool.util.StrUtil;
import com.zhangliping.baseUtil.error.OrderErrorCode;

/**
 * Controller的ResponseVo工具
 * @author zhangliping
 */
public class ControllerResponseUtils {

    /**
     * 根据结果获取responseVo实例
     * @param data 处理数据
     * @return {@link ResponseVo}实例
     */
    public static ResponseVo getResponseVo(Object data) {
    	ResponseVoExtend responseVoExtend = new ResponseVoExtend();
        int errorCode = OrderErrorCode.ERROR_CODE_SUCCESS;
        // 如果data为null或不是Exception实例
        if (null == data || !(data instanceof Exception)) {
        	responseVoExtend.setSuccess(true);
        	responseVoExtend.setMsg("success");
        	responseVoExtend.setData(data);
        	responseVoExtend.setErrorCode(errorCode);
            return responseVoExtend;
        }

        // 以下为处理异常
        // 异常信息
        String errorMsg = null;
        String ext_errorMsg= null;
        if (data instanceof Exception) {
        	ext_errorMsg=JosnLogUtil.formatEx((Exception)data); //获取异常具体信息
            // 已定义的系统异常
            if (data instanceof SystemException) {
                SystemException systemException = (SystemException) data;
                errorCode = systemException.getErrorCode().getErrCode();
                errorMsg = systemException.getErrorCode().getErrMessage();
            } else if (data instanceof BusinessException) { // 已定义的业务异常
                BusinessException businessException = (BusinessException) data;
                errorCode = businessException.getErrorCode().getErrCode();
                try{
                	String [] tempErrorMsgArray= businessException.getErrorCode().getErrMessage().split("ExternalErrorMsg:");
                    errorMsg = tempErrorMsgArray[1];
                }catch(Exception e){
                	errorMsg=businessException.getErrorCode().getErrMessage();
                }

            } else if (data instanceof StmException) {
                StmException stmException = (StmException) data;
                errorCode = stmException.getErrorCode().getErrCode();
                errorMsg = stmException.getErrorCode().getErrMessage();
            } else if (data instanceof IllegalArgumentException) {
                IllegalArgumentException argumentException = (IllegalArgumentException) data;
                errorCode = ErrorCodeDefinition.OTHER_SYSTEM_ERROR.getErrorCode().getErrCode();
                errorMsg = argumentException.getMessage();
            }
            else { // 未定义的系统异常
                errorCode = ErrorCodeDefinition.OTHER_SYSTEM_ERROR.getErrorCode().getErrCode();
                errorMsg = ErrorCodeDefinition.OTHER_SYSTEM_ERROR.getErrorCode().getErrMessage();
            }
            if (null == errorMsg) {
                Object cause = ((Throwable) data).getCause();
                if (cause != null) {
                    if (cause instanceof StmException) {
                        errorMsg = ((StmException) cause).getErrorCode().getErrMessage();
                    } else {
                        errorMsg = ((Throwable) cause).getMessage();
                    }
                }
            }
            if(StrUtil.isBlank(errorMsg)){
                errorMsg = ((Exception)data).getLocalizedMessage();
            }
        }
        // 设置错误信息
        responseVoExtend.setSuccess(false);
        responseVoExtend.setErrorCode(errorCode);
        responseVoExtend.setMsg(errorMsg);
        responseVoExtend.setExtendMsg(ext_errorMsg); 
        return responseVoExtend;
    }
}

