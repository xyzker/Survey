package survey.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class POITest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		//工作表
		HSSFSheet sheet = wb.createSheet("first sheet");
		//行
		HSSFRow row = sheet.createRow(0);
		//单元格
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(false);
		row.createCell(1).setCellValue(Calendar.getInstance());		//日历
		row.createCell(2).setCellValue(new Date());					//Date
		row.createCell(3).setCellValue(123456789.87654321);					//double
		row.createCell(4).setCellValue(new HSSFRichTextString("哈哈哈哈哈哈哈哈哈哈哈ddddddd"));
		row.createCell(5).setCellValue("呵呵");
		
		//创建数据格式对象
		HSSFDataFormat format = wb.createDataFormat();
		//格式化数据
		HSSFCellStyle style = wb.createCellStyle();
		//设置样式的数据格式,对日期格式化
		style.setDataFormat(format.getFormat("yyyy-MM-dd hh:mm:ss"));
		row.getCell(1).setCellStyle(style);
		row.getCell(2).setCellStyle(style);
		
		//double值格式化
		style = wb.createCellStyle();
		style.setDataFormat(format.getFormat("#,##0.000"));
		row.getCell(3).setCellStyle(style);
		
		//设置列宽(单位：1/20)
		sheet.setColumnWidth(1, 6000);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.setColumnWidth(4, 7000);
		
		//自动回绕文本
		style = wb.createCellStyle();
		style.setWrapText(true);
		row.getCell(4).setCellStyle(style);
		
		//设置文本对齐方式
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("左上");
		row.createCell(1).setCellValue("中中");
		row.createCell(2).setCellValue("右下");
		
		//设置行高
		row.setHeightInPoints(50);
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		
		//设置对齐方式
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);		//左对齐
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);		//上对齐
		row.getCell(0).setCellStyle(style);
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);		
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		
		row.getCell(1).setCellStyle(style);
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);		
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);		
		row.getCell(2).setCellStyle(style);
		
		//设置字体和大小
		style = row.getCell(0).getCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontName("方正姚体");
		font.setFontHeightInPoints((short) 30);
		font.setItalic(true);
		font.setColor(HSSFColor.RED.index);
		style.setFont(font);
		
		//字体旋转
		style = row.getCell(1).getCellStyle();
		style.setRotation((short)-30);
		
		//设置边框
		row = sheet.createRow(2);
		cell = row.createCell(0);
		style = wb.createCellStyle();
		style.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);		//双实线
		style.setTopBorderColor(HSSFColor.BLUE.index);
		cell.setCellStyle(style);
		
		//计算列
		row = sheet.createRow(3);
		row.createCell(0).setCellValue(13);
		row.createCell(1).setCellValue(16);
		row.createCell(2).setCellValue(19);
		row.createCell(3).setCellFormula("sum(A4:C4)");
		
		//移动行
		sheet.shiftRows(1, 3, 2);
		
		//拆分窗格
		//1000:左侧窗格的宽度
		//2000：上侧窗格的高度
		//3：右侧窗格开始显示列的索引
		//4: 下侧窗格开始显示行的索引
		//0：激活的面板区
		//sheet.createSplitPane(1000, 2000, 3, 4, 0);
		
		//冻结窗格
		//1：左侧冻结的列数
		//2：右侧冻结的行数
		sheet.createFreezePane(1,2);
		
		//保存
		wb.write(new FileOutputStream(new File("f:/poi.xls")));
	}

}
