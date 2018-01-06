package com.zhangliping.puhui.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.utils.DateUtils;
import com.google.common.collect.Lists;
import com.xiaoleilu.hutool.util.DateUtil;
import com.zhangliping.sqladmin.util.ExcelUtilsExtend;
import com.zhangliping.sqladmin.util.PropertiesUtils;

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

    public static String path = PropertiesUtils.getValue("path");
    public static String excelFileNameTemplate = "个人放款通知单";
    public static String templeName = "temp.xlsx";
    public static String pwName = "PW.xlsx";

    /**
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
		
		List<String> list = getFileList(path);
		for (String fileFullPath : list) {
			logger.info("开始处理文件=>" + fileFullPath);
			
			String dealDate = getDealDate(fileFullPath);
			
		    String destPath = path + dealDate + "\\";
		    Date loadDate = DateUtils.str2Date(genDateStr(dealDate), "yyyy-M-d");
			
	        File file = new File(destPath);
	        if (!file.exists()) {
	            file.mkdirs();
	        }
			
			logger.info("开始读取放贷记录....");
			// 开始程序获取rowNum
			Integer rowNum = getRowNum(fileFullPath);
			logger.info("生成条数为=> " + rowNum);
			List<Notify> notifyList = ExcelUtils.getInstance().readExcel2Objects(
					fileFullPath, Notify.class, 3, rowNum, 0);

			// 线下放款
			logger.info("开始生成线下放款列表....");
			loadGen(notifyList, loadDate, destPath);

			// 线下回款
			logger.info("开始生成回款列表....");
			receiveGen(notifyList, loadDate, destPath);

			// 文件记录查找
			// localGen();
		}
		logger.info("完事....收工.....");
	}

	public static String getDealDate(String fileFullPath) {
		String dealDate = fileFullPath.substring(fileFullPath.indexOf(excelFileNameTemplate) + 7, fileFullPath.length() - 5);
		return dealDate;
	}

	private static List<String> getFileList(String path) {
		List<String> excelList = Lists.newArrayList();
		File file = new File(path);
		File[] listFile = file.listFiles();
		for (File file2 : listFile) {
			if (file2.getAbsolutePath().endsWith(".xlsx")) {
				excelList.add(file2.getAbsolutePath());
			}
		}
		return excelList;
	}
	
    /**
     * 获取excel文件的行数
     * @param fullName
     * @return
     * @throws IOException 
     */
    private static Integer getRowNum(String fileFullPath) throws IOException {
    	InputStream is = new FileInputStream(fileFullPath);
    	Workbook wb = new XSSFWorkbook(is);
    	Sheet sheet = wb.getSheetAt(0);
    	int lastRowNum = sheet.getPhysicalNumberOfRows();
    	return lastRowNum - 10;
    }
    
    private static void loadGen(List<Notify> notifyList, Date loadDate, String destPath) throws IllegalAccessException, InvocationTargetException,
            Exception {
        List<Loan> loanList = Lists.newArrayList();
        Loan loan = null;
        for (Notify stu : notifyList) {
            loan = new Loan();
            BeanUtils.copyProperties(loan, stu);
            loan.setLoadDate(loadDate);
            loan.setReturnTotal(null);
            loan.setTiqianjianmianfuwufei(null);
            loan.setTiqianhuankuanbenjin(null);
            loan.setTiqianhuankuanlixi(null);
            loanList.add(loan);
        }
        // 线下放款生成
        ExcelUtilsExtend.getInstance().exportObjects2Excel(loanList, Loan.class, true, "快捷通放款", true, destPath + "快捷通放款" + DateUtil.formatDate(loadDate) + ".xlsx");
    }

    private static void receiveGen(List<Notify> notifyList, Date loadDate, String destPath) throws Exception {
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

            // 先计算第二条记录

            double fangkuan = Math.floor(Double.valueOf(stu.getTureTotal()) / 24.0);
            double lixi = Math.floor(Double.valueOf(stu.getTotallixi()) / 24.0);
            double service = Math.floor((tureTotal + serviceFee) / 24.0 - fangkuan);

            double total = 0.0;
            for (int i = 0; i < Integer.valueOf(stu.getJiekuanqixian()); i++) {
                received = new Received();
                received.setContractNo(stu.getContractNo());
                received.setBorrower(stu.getBorrower());
                // 放款日期", order = 1)
                received.setReturnStartDate(null);

                if (i == 0) {
                    // "服务费", order = 1)
                    received.setServiceFee(serviceFee - service * 23);
                    // @ExcelField(title = "放款金额", order = 1)
                    received.setTureTotal(tureTotal - fangkuan * 23);
                    // @ExcelField(title = "应收利息", order = 1)
                    received.setTotallixi(totallixi - lixi * 23);
                    // @ExcelField(title = "还款总额", order = 1)
                    total = serviceFee - service * 23 + tureTotal - fangkuan * 23 + totallixi - lixi * 23;
                    received.setReturnTotal(String.valueOf(total));
                    // @ExcelField(title = "应还款日", order = 1)
                    received.setReturnDate(stu.getReturnStartDate());
                } else {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(calcDate);
                    calendar.add(calendar.MONTH, 1);
                    received.setReturnDate(calendar.getTime());

                    // "服务费", order = 1)
                    received.setServiceFee(service);
                    // @ExcelField(title = "放款金额", order = 1)
                    received.setTureTotal(fangkuan);
                    // @ExcelField(title = "应收利息", order = 1)
                    received.setTotallixi(lixi);
                    // @ExcelField(title = "还款总额", order = 1)
                    total = total + service + fangkuan + lixi;
                    received.setReturnTotal(String.valueOf(total));
                    // @ExcelField(title = "应还款日", order = 1)
                }
                calcDate = received.getReturnDate();

                receivedList.add(received);
            }
        }
        // 生成线下回款
        ExcelUtils.getInstance().exportObjects2Excel(receivedList, Received.class, true, "快捷通回款", true, destPath + "快捷通回款" + DateUtil.formatDate(loadDate) + ".xlsx");
    }

    private static void localGen(String destPath) throws Exception {
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
            ExcelUtils.getInstance().exportObjects2Excel(genLocalList, ExcelLocal.class, true, "位置", true,
                    destPath + "位置.xlsx");
        }
    }

    public static String genDateStr(String date) {
        String[] dateArray = null;
        String year = "";
        if (date.contains(".")) {
            dateArray = date.split("\\.");
            if (dateArray.length != 2) {
                System.out.println("date填写错误，请确认");
            }
            year = String.valueOf(DateUtil.thisYear());

        }

        return year + "-" + dateArray[0] + "-" + dateArray[1];
    }


}
