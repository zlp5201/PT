package com.zhangliping.puhui.bean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.utils.DateUtils;
import com.google.common.collect.Lists;

/**
 * 
 * 普惠数据生成入口
 *
 * @author DELL
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ShellRunner {

    private static final Logger logger = LoggerFactory.getLogger(ShellRunner.class);

    public static String path = "D:\\report\\puhui\\";
    public static String destPath = path + "11.13\\";
    public static String templeName = "temp.xlsx";
    public static String fileName = "个人放款通知单11.13.xlsx";
    public static int position = 48;
    public static String pwName = "PW.xlsx";
    public static Date loadDate = DateUtils.str2Date("2017-11-13", "yyyy-M-d");

    static {
    	File file = new File(destPath);
    	if (!file.exists()) {
			file.mkdirs();
    	}
    	
    }
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String fileFullPath = path + fileName;
        logger.info("开始读取放贷记录....");
        List<Notify> notifyList = ExcelUtils.getInstance().readExcel2Objects(fileFullPath, Notify.class, 3, position, 0);

        // 线下放款
        loadGen(notifyList);

        // 线下回款
        receiveGen(notifyList);

        // 文件记录查找
        localGen();
        logger.info("完事....收工.....");
    }

    private static void loadGen(List<Notify> notifyList) throws IllegalAccessException, InvocationTargetException,
            Exception {
        logger.info("开始生成线下放款列表....");
        List<Loan> loanList = Lists.newArrayList();
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
        ExcelUtils.getInstance().exportObjects2Excel(loanList, Loan.class, true, "线下放款", true, destPath + "线下放款.xlsx");
    }

    private static void receiveGen(List<Notify> notifyList) throws Exception {
        logger.info("开始生成回款列表....");
        List<Received> receivedList = Lists.newArrayList();
        Received received = null;
        for (Notify stu : notifyList) {
            received = new Received();
            received.setContractNo(stu.getContractNo());
            received.setBorrower(stu.getBorrower());
            // 放款日期", order = 1)
            received.setReturnStartDate(loadDate);
            // "服务费", order = 1)
            received.setServiceFee(stu.getServiceFee());
            // @ExcelField(title = "放款金额", order = 1)
            received.setTureTotal(stu.getTureTotal());
            // @ExcelField(title = "应收利息", order = 1)
            received.setTotallixi(stu.getTotallixi());

            // @ExcelField(title = "还款总额", order = 1)
            double totallixi = Double.valueOf(received.getTotallixi());
            double tureTotal = Double.valueOf(received.getTureTotal());
            double serviceFee = Double.valueOf(stu.getServiceFee());
            received.setReturnTotal(String.valueOf(totallixi + tureTotal + serviceFee));
            // @ExcelField(title = "应还款日", order = 1)
            received.setReturnDate(null);
            receivedList.add(received);
            Date calcDate = stu.getReturnStartDate();
            for (int i = 0; i < Integer.valueOf(stu.getJiekuanqixian()); i++) {
                received = new Received();
                received.setContractNo(stu.getContractNo());
                received.setBorrower(stu.getBorrower());
                // 放款日期", order = 1)
                received.setReturnStartDate(null);
                // "服务费", order = 1)
                received.setServiceFee("");
                // @ExcelField(title = "放款金额", order = 1)
                received.setTureTotal("");
                // @ExcelField(title = "应收利息", order = 1)
                received.setTotallixi("");
                // @ExcelField(title = "还款总额", order = 1)
                received.setReturnTotal("");
                // @ExcelField(title = "应还款日", order = 1)

                if (i == 0) {
                    received.setReturnDate(stu.getReturnStartDate());
                } else {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(calcDate);
                    calendar.add(calendar.MONTH, 1);
                    received.setReturnDate(calendar.getTime());
                }
                calcDate = received.getReturnDate();

                receivedList.add(received);
            }
        }
        // 生成线下回款
        ExcelUtils.getInstance().exportObjects2Excel(receivedList, Received.class, true, "线下回款", true,
        		destPath + "线下回款.xlsx");
    }

    private static void localGen() throws Exception {
        logger.info("开始生成位置查找列表....");
        String pwPath = path + pwName;
        List<ExcelLocal> pwList = ExcelUtils.getInstance().readExcel2Objects(pwPath, ExcelLocal.class, 0, 0);

        String tempPath = path + templeName;
        List<ExcelLocal> tempLocalList = ExcelUtils.getInstance().readExcel2Objects(tempPath, ExcelLocal.class, 0, 0);
        List<ExcelLocal> genLocalList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(pwList)) {
            for (ExcelLocal pw : pwList) {

                ExcelLocal genLocal = null;
                for (ExcelLocal tempLocal : tempLocalList) {
                    if (pw.getBorrower().equals(tempLocal.getBorrower())) {
                        genLocal = new ExcelLocal();
                        genLocal.setBorrower(tempLocal.getBorrower());
                        genLocal.setLocation(tempLocal.getLocation());
                        genLocalList.add(genLocal);
                    }
                }
            }
            // 位置生成
            ExcelUtils.getInstance()
            .exportObjects2Excel(genLocalList, ExcelLocal.class, true, "位置", true, destPath + "位置.xlsx");
        }
    }

}
