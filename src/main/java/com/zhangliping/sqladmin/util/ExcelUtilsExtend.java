/**
 * 
 */
package com.zhangliping.sqladmin.util;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.crab2died.handler.ExcelHeader;
import com.github.crab2died.utils.Utils;
import com.google.common.base.Strings;

/**
 * @author zhangliping
 *
 */
public class ExcelUtilsExtend {
	private static ExcelUtilsExtend excelUtils = new ExcelUtilsExtend();

	public static ExcelUtilsExtend getInstance() {
		return excelUtils;
	}

	public void exportObjects2Excel(List<?> data, Class clazz,
			boolean isWriteHeader, String sheetName, boolean isXSSF,
			String targetPath) throws Exception {

		FileOutputStream fos = new FileOutputStream(targetPath);
		exportExcelNoModuleHandler(data, clazz, isWriteHeader, sheetName,
				isXSSF).write(fos);
	}

	private Workbook exportExcelNoModuleHandler(List<?> data, Class clazz,
			boolean isWriteHeader, String sheetName, boolean isXSSF)
			throws Exception {

		Workbook workbook;
		if (isXSSF) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new HSSFWorkbook();
		}
		Sheet sheet;
		if (null != sheetName && !"".equals(sheetName)) {
			sheet = workbook.createSheet(sheetName);
		} else {
			sheet = workbook.createSheet();
		}
		Row row = sheet.createRow(0);
		List<ExcelHeader> headers = Utils.getHeaderList(clazz);
		if (isWriteHeader) {
			// 写标题
			for (int i = 0; i < headers.size(); i++) {
				row.createCell(i).setCellValue(headers.get(i).getTitle());
			}
		}
		// 写数据
		Object _data;
		for (int i = 0; i < data.size(); i++) {
			row = sheet.createRow(i + 1);
			_data = data.get(i);
			for (int j = 0; j < headers.size(); j++) {
				Cell cell = row.createCell(j);
				String value = Utils.getProperty(_data, headers.get(j).getFiled(),
						headers.get(j).getFiledClazz(), headers.get(j)
						.getWriteConverter());
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框     
				if (headers.get(j).getFiledClazz() == Double.class) {
					if (Strings.isNullOrEmpty(value)) {
						cell.setCellValue(value);
					} else {
						cell.setCellValue(Double.parseDouble(value));
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					}
					
					DataFormat format = workbook.createDataFormat();
					cellStyle.setDataFormat(format.getFormat("0.00"));//设置单元类型
				}else if (headers.get(j).getFiledClazz() == Integer.class) {
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					cell.setCellValue(Integer.valueOf(value));
				} else if (headers.get(j).getFiledClazz() == String.class) {
					cell.setCellValue(value);
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				} else if (headers.get(j).getFiledClazz() == Date.class) {
					cell.setCellValue(value);
				}
				cell.setCellStyle(cellStyle);
			}
		}
		return workbook;
	}
}
