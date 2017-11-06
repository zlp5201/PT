package com.zhangliping.puhui.bean;

import java.util.Date;

import lombok.Data;

import com.github.crab2died.annotation.ExcelField;
import com.zhangliping.puhui.bean.converter.Received2DateConverter;



@Data
public class Received {
	//合同编号
	@ExcelField(title = "合同编号", order = 1)
    private String contractNo;
	// 借款姓名
    @ExcelField(title = "借款姓名", order = 1)
    private String borrower;
    @ExcelField(title = "放款日期", order = 1, writeConverter = Received2DateConverter.class)
    private Date returnStartDate;
    @ExcelField(title = "服务费", order = 1)
    private String serviceFee;
    @ExcelField(title = "放款金额", order = 1)
    private String tureTotal;
    @ExcelField(title = "应收利息", order = 1)
    private String totallixi;
    @ExcelField(title = "还款总额", order = 1)
    private String returnTotal;
    @ExcelField(title = "应还款日", order = 1, writeConverter = Received2DateConverter.class)
    private Date returnDate;
}
