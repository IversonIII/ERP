package cn.iverson.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class CreateOrderXls {

	/**
	 * 创建订单表格模板
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		//工作簿
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		//表
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("采购订单");
		//内容样式
		HSSFCellStyle hssfCellStyle_content = hssfWorkbook.createCellStyle();
		//内容边框样式
		hssfCellStyle_content.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
		hssfCellStyle_content.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		hssfCellStyle_content.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		hssfCellStyle_content.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		//内容位置样式
		hssfCellStyle_content.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		hssfCellStyle_content.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//内容字体样式设置
		HSSFFont hssfFont_content = hssfWorkbook.createFont();//创建字体
		hssfFont_content.setFontName("宋体");//设置字体
		hssfFont_content.setFontHeightInPoints((short)12);//设置字体大小
		hssfCellStyle_content.setFont(hssfFont_content);//放回框内
		
		//标题样式
		HSSFCellStyle hssfCellStyle_title = hssfWorkbook.createCellStyle();
		hssfCellStyle_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		hssfCellStyle_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//标题字体样式
		HSSFFont hssfFont_title = hssfWorkbook.createFont();//创建字体
		hssfFont_title.setFontName("微软雅黑");//设置字体
		hssfFont_title.setBold(true);//加粗
		hssfFont_title.setFontHeightInPoints((short)18);//大小
		hssfCellStyle_title.setFont(hssfFont_title);
		hssfSheet.createRow(0).createCell(0).setCellStyle(hssfCellStyle_title);
		
		
		for(int i = 2;i < 12;i++){
			HSSFRow hssfRow = hssfSheet.createRow(i);
			for(int j = 0;j < 4;j++){
				HSSFCell hssfCell = hssfRow.createCell(j);
				hssfCell.setCellStyle(hssfCellStyle_content);
			}
		}
		//日期格式
		HSSFCellStyle hssfCellStyle_date = hssfWorkbook.createCellStyle();
		hssfCellStyle_date.cloneStyleFrom(hssfCellStyle_content);
		HSSFDataFormat hssfDataFormat = hssfWorkbook.createDataFormat();
		hssfCellStyle_date.setDataFormat(hssfDataFormat.getFormat("yyyy-MM-dd hh:mm"));
		for(int i = 3;i<7;i++){
			hssfSheet.getRow(i).getCell(1).setCellStyle(hssfCellStyle_date);
		}
		hssfSheet.getRow(3).getCell(1).setCellValue(new Date());
		//合并单元格
		hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,3));//标题
		hssfSheet.addMergedRegion(new CellRangeAddress(2,2,1,3));//供应商
		hssfSheet.addMergedRegion(new CellRangeAddress(7,7,0,3));//订单明细
		//设置各个单元格的内容
		hssfSheet.getRow(0).getCell(0).setCellValue("采购单");
		hssfSheet.getRow(2).getCell(0).setCellValue("供应商");
		
		hssfSheet.getRow(3).getCell(0).setCellValue("下单日期");
		hssfSheet.getRow(3).getCell(2).setCellValue("经办人");
		
		hssfSheet.getRow(4).getCell(0).setCellValue("审核日期");
		hssfSheet.getRow(4).getCell(2).setCellValue("供应商");
		
		hssfSheet.getRow(5).getCell(0).setCellValue("采购日期");
		hssfSheet.getRow(5).getCell(2).setCellValue("经办人");
		
		hssfSheet.getRow(6).getCell(0).setCellValue("入库日期");
		hssfSheet.getRow(6).getCell(2).setCellValue("经办人");
		
		hssfSheet.getRow(7).getCell(0).setCellValue("订单明细");
		
		hssfSheet.getRow(8).getCell(0).setCellValue("商品名称");
		hssfSheet.getRow(8).getCell(1).setCellValue("数量");
		hssfSheet.getRow(8).getCell(2).setCellValue("价格");
		hssfSheet.getRow(8).getCell(3).setCellValue("金额");
		
		//设置行高和列宽
		hssfSheet.getRow(0).setHeight((short)1000);
		//设置内容部分的行高
		for(int i = 2;i<12;i++){
			hssfSheet.getRow(i).setHeight((short)500);
		}
		//设置列宽
		for(int i = 0;i<4;i++){
			hssfSheet.setColumnWidth(i, 5000);
		}
		//保存工作簿Dao"f:\\采购订单.xls"
		hssfWorkbook.write(new FileOutputStream("f:\\采购订单.xls"));
		hssfWorkbook.close();
	}

}