package com.zhangliping.puhui.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.utils.DateUtils;

public class ShellRunner {

    public static String path= "D:\\home\\";
    public static String templeName= "temp.xlsx";
    public static String fileName= "个人放款通知单10.30.xlsx";
    public static Date loadDate= DateUtils.str2Date("2017-10-30", DateUtils.DATE_FORMAT_DAY);
    
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String fileFullPath = path + fileName;

		List<Notify> notifyList = ExcelUtils.getInstance().readExcel2Objects(
                fileFullPath, Notify.class,3,72,0);
		
		
		
		List<Loan> loanList = new ArrayList();
		Loan loan = null;
		for (Notify stu : notifyList) {
			loan = new Loan();
			BeanUtils.copyProperties(loan, stu);
			loan.setLoadDate(loadDate);
			loan.setReturnTotal("");
			loan.setTiqianjianmianfuwufei("");
			loan.setTiqianhuankuanbenjin("");
			loan.setTiqianhuankuanlixi("");
			loanList.add(loan);
		}
		
		// 线下放款生成
		ExcelUtils.getInstance().exportObjects2Excel(loanList, Loan.class, true, "线下放款", true, path + "线下放款.xlsx");
	
	
		List<Received> receivedList = new ArrayList<Received>();
		Received received = null;
		for (Notify stu : notifyList) {
			received = new Received();
			received.setContractNo(stu.getContractNo());
			received.setBorrower(stu.getBorrower());
		    // 放款日期", order = 1)
		    received.setReturnStartDate(loadDate);
		    // "服务费", order = 1)
		    received.setServiceFee(stu.getServiceFee());
//		    @ExcelField(title = "放款金额", order = 1)
		    received.setTureTotal(stu.getTureTotal());
//		    @ExcelField(title = "应收利息", order = 1)
		    received.setTotallixi(stu.getTotallixi());
		    
//		    @ExcelField(title = "还款总额", order = 1)
		    double totallixi = Double.valueOf(received.getTotallixi());
		    double tureTotal = Double.valueOf(received.getTureTotal());
		    double serviceFee = Double.valueOf(stu.getServiceFee());
		    received.setReturnTotal(String.valueOf(totallixi + tureTotal + serviceFee));
//		    @ExcelField(title = "应还款日", order = 1)
		    received.setReturnDate(null);
		    receivedList.add(received);
		    Date calcDate = stu.getReturnStartDate();
		    for (int i = 0;i<Integer.valueOf(stu.getJiekuanqixian());i++) {
		    	received = new Received();
				received.setContractNo(stu.getContractNo());
				received.setBorrower(stu.getBorrower());
				// 放款日期", order = 1)
			    received.setReturnStartDate(null);
			    // "服务费", order = 1)
			    received.setServiceFee("");
//			    @ExcelField(title = "放款金额", order = 1)
			    received.setTureTotal("");
//			    @ExcelField(title = "应收利息", order = 1)
			    received.setTotallixi("");
//			    @ExcelField(title = "还款总额", order = 1)
			    received.setReturnTotal("");
//			    @ExcelField(title = "应还款日", order = 1)
			    
			    if (i==0) {
			        received.setReturnDate(stu.getReturnStartDate());
			    } else {
			        Calendar   calendar   =   new   GregorianCalendar(); 
			        calendar.setTime(calcDate); 
			        calendar.add(calendar.MONTH, 1);
			        received.setReturnDate(calendar.getTime());
			    }
			    calcDate = received.getReturnDate();
				
		    	receivedList.add(received);
		    }
		}
		
		
		// 生成线下回款
		ExcelUtils.getInstance().exportObjects2Excel(receivedList, Received.class, true, "线下回款", true, path + "线下回款.xlsx");
		
		
		String tempPath = path + templeName;

        List<ExcelLocal> excelLocalList = ExcelUtils.getInstance().readExcel2Objects(
                tempPath, ExcelLocal.class,0,0);
        
        String name = "周开东";
        
        List<ExcelLocal> genLocalList = new ArrayList();
        ExcelLocal genLocal = null;
        for (ExcelLocal excelLocal : excelLocalList) {
            if (name.equals(excelLocal.getBorrower())) {
                genLocal = new ExcelLocal();
                genLocal.setBorrower(name);
                genLocal.setLocation(excelLocal.getLocation());
                genLocalList.add(genLocal);
            }
        }
        
        // 线下放款生成
        ExcelUtils.getInstance().exportObjects2Excel(genLocalList, ExcelLocal.class, true, "位置", true, "D:\\home\\位置.xlsx");
    }

}
