package cn.iverson.erp.biz.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import cn.iverson.erp.biz.exception.ErpException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import cn.iverson.erp.biz.ISupplierBiz;
import cn.iverson.erp.dao.ISupplierDao;
import cn.iverson.erp.entity.Supplier;
/**
 * 供应商业务逻辑类
 * @author Administrator
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		super.setBaseDao(this.supplierDao);
	}

	/**
	 * 导出供应商列表/客户表
	 */
	@Override
	public void export(OutputStream os, Supplier t1) {
		//获取供应商列表/客户表
		List<Supplier> supplierList = super.getList(t1, null, null);
		//创建execl表格
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = null;
		//根据type的值来设置工作表的名称
		if(t1.getType().equals(Supplier.Type_SUPPLIER)){
			hssfSheet = hssfWorkbook.createSheet("供应商");
		}
		if(t1.getType().equals(Supplier.Type_CUSTOMER)){
			hssfSheet = hssfWorkbook.createSheet("客户");
		}
		//创建表头
		HSSFRow hssfRow = hssfSheet.createRow(0);
		//定义每一列的标题(用数组来存值,省步奏,用循环即可取值)
		String [] headerNames = {"名称","地址","联系人","电话","E-mail"};
		//设置每一列的宽度
		int [] columnWidths = {4000,8000,2000,3000,8000};
		HSSFCell hssfCell = null;
		for(int i = 0;i<headerNames.length;i++){
			hssfCell = hssfRow.createCell(i);
			hssfCell.setCellValue(headerNames[i]);
			hssfSheet.setColumnWidth(i,columnWidths[i]);
		}
		//写入内容
		int i = 1;
		for(Supplier supplier:supplierList){
			hssfRow = hssfSheet.createRow(i);
			hssfRow.createCell(0).setCellValue(supplier.getName());
			hssfRow.createCell(1).setCellValue(supplier.getAddress());
			hssfRow.createCell(2).setCellValue(supplier.getContact());
			hssfRow.createCell(3).setCellValue(supplier.getTele());
			hssfRow.createCell(4).setCellValue(supplier.getEmail());
			i++;
		}
		try {
			//写出到输出流
			hssfWorkbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				//关闭工作簿
				hssfWorkbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doImport(InputStream is) throws Exception {
		HSSFWorkbook hssfWorkbook = null;
		try {
				hssfWorkbook = new HSSFWorkbook(is);
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
				String type = "";
				if ("供应商".equals(hssfSheet.getSheetName())) {
					type = Supplier.Type_SUPPLIER;
				} else if ("客户".equals(hssfSheet.getSheetName())) {
					type = Supplier.Type_CUSTOMER;
				} else {
					throw new ErpException("工作表名称不正确");
				}
				int lastRow = hssfSheet.getLastRowNum();
				Supplier supplier = null;
				for (int i = 1; i <= lastRow; i++) {
					supplier = new Supplier();
					supplier.setName(hssfSheet.getRow(i).getCell(0).getStringCellValue());
					//判断是否已经存在,通过名称来判断
					List<Supplier> supplierList = supplierDao.getList(null, supplier, null);
					if (supplierList.size() > 0) {
						supplier = supplierList.get(0);
					}
					supplier.setAddress(hssfSheet.getRow(i).getCell(1).getStringCellValue());//地址
					supplier.setContact(hssfSheet.getRow(i).getCell(2).getStringCellValue());//联系人
					supplier.setTele(hssfSheet.getRow(i).getCell(3).getStringCellValue());//电话
					supplier.setEmail(hssfSheet.getRow(i).getCell(4).getStringCellValue());//Email
					if (supplierList.size() == 0) {
						supplier.setType(type);
						supplierDao.add(supplier);
					}
				}
			} finally {
				if(null != hssfWorkbook){
					try{
						hssfWorkbook.close();
					}catch (IOException e){
						e.printStackTrace();
					}
				}
		}
	}

}
