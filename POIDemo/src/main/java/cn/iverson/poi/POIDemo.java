package cn.iverson.poi;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIDemo {

	public static void main(String[] args) throws Exception {
		
		//创建一个excel表格
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		//创建一张工作表
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("工作表");
		//设置列宽
		hssfSheet.setColumnWidth(0, 5000);
		//创建第一行
		HSSFRow hssfRow = hssfSheet.createRow(0);
		//创建第一行的第一个单元格
		HSSFCell hssfCell = hssfRow.createCell(0);
		//向该单元格填值
		hssfCell.setCellValue("Iverson");
		//保存execl表格
		hssfWorkbook.write(new FileOutputStream(new File("f:\\test.xls")));
		//关闭工作簿
		hssfWorkbook.close();
	}
}
