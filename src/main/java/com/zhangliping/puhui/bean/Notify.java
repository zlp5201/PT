package com.zhangliping.puhui.bean;

import java.util.Date;

import lombok.Data;

import com.github.crab2died.annotation.ExcelField;
import com.zhangliping.puhui.bean.converter.Notify2DateConverter;



@Data
public class Notify {

    // 学号
    @ExcelField(title = "序号", order = 1)
    private String id;
    @ExcelField(title = "大区", order = 1)
    private String daqu;
    @ExcelField(title = "营业部名称", order = 1)
    private String yingyebumingcheng;
    @ExcelField(title = "申请人", order = 1)
    private String shenqingren;
    @ExcelField(title = "团队经理", order = 1)
    private String teamLeader;
    @ExcelField(title = "客户经理", order = 1)
    private String kehujingli;
    // 借款人
    @ExcelField(title = "借款人", order = 1)
    private String borrower;
    // 用户名
    @ExcelField(title = "用户名", order = 1)
    private String userName;
    // 合同编号
    @ExcelField(title = "合同编号", order = 1)
    private String contractNo;
    @ExcelField(title = "借款金额", order = 1)
    private double jiekuanjine;
    @ExcelField(title = "借款期限", order = 1)
    private String jiekuanqixian;
    @ExcelField(title = "借款利率", order = 1)
    private String jiekuanlilv;
    @ExcelField(title = "还款开始日期", order = 1, readConverter = Notify2DateConverter.class)
    private Date returnStartDate;
    @ExcelField(title = "还款结束日期", order = 1, readConverter = Notify2DateConverter.class)
    private Date returnEndDate;
    @ExcelField(title = "服务费", order = 1)
    private String serviceFee;
    @ExcelField(title = "实际打款金额", order = 1)
    private String tureTotal;
    @ExcelField(title = "总利息", order = 1)
    private String totallixi;
    @ExcelField(title = "每期应收金额", order = 1)
    private String meiqiyingshou;
    
    @ExcelField(title = "首期还款金额", order = 1)
    private String shouqihuankuan;
    @ExcelField(title = "产品类型", order = 1)
    private String productType;
    
//    借款期限	借款利率	 还款开始日期 	 还款结束日期 	服务费	实际打款金额	总利息	每期应收金额	首期还款金额	产品类型
    
//    大区	营业部名称	申请人	团队经理	客户经理	借款人	用户名	合同编号	借款金额	借款期限	借款利率	 还款开始日期 	 还款结束日期 	服务费	实际打款金额	总利息	每期应收金额	首期还款金额	产品类型
}
