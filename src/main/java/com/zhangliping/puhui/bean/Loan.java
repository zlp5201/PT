/**
 * 
 */
package com.zhangliping.puhui.bean;


import java.util.Date;

import lombok.Data;

import com.github.crab2died.annotation.ExcelField;
import com.zhangliping.puhui.bean.converter.Loan2DateConverter;

/**
 * 线下放款
 * @author zhangliping
 *
 */
@Data
public class Loan {
	//用户名
    @ExcelField(title = "用户名", order = 1)
    private String userName;
	//合同编号
    @ExcelField(title = "合同编号", order = 1)
    private String contractNo;
	//借款姓名	
    @ExcelField(title = "借款姓名", order = 1)
    private String borrower;
	//放款日期
    @ExcelField(title = "放款日期", order = 1, writeConverter=Loan2DateConverter.class)
    private Date loadDate;
    
    @ExcelField(title = "签约金额", order = 1)
    private String jiekuanjine;
    
    @ExcelField(title = "服务费", order = 1)
    private String serviceFee;
	//实际放款金额
    @ExcelField(title = "实际打款金额", order = 1)
    private String tureTotal;
	//应收利息
    @ExcelField(title = "应收利息", order = 1)
    private String totallixi;
	//还款总额
    @ExcelField(title = "还款总额", order = 1)
    private String returnTotal;
	//提前还款减免服务费
    @ExcelField(title = "提前还款减免本金", order = 1)
    private String tiqianjianmianfuwufei;
	//提前还款减免本金
    @ExcelField(title = "提前还款减免本金", order = 1)
    private String tiqianhuankuanbenjin;
	//提前还款减免利息
    @ExcelField(title = "提前还款减免利息", order = 1)
    private String tiqianhuankuanlixi;
	//总还款期数
    @ExcelField(title = "总还款期数", order = 1)
    private String jiekuanqixian;
	//产品名称
    @ExcelField(title = "产品名称", order = 1)
    private String productType;
    @ExcelField(title = "营业部名称", order = 1)
    private String yingyebumingcheng;
	//申请人
    @ExcelField(title = "申请人", order = 1)
    private String shenqingren;
	//团队经理
    @ExcelField(title = "团队经理", order = 1)
    private String teamLeader;
	//客户经理
    @ExcelField(title = "客户经理", order = 1)
    private String kehujingli;
	//所属部门
    @ExcelField(title = "所属部门", order = 1)
    private String daqu;
}
