package cn.iverson.erp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletResponse;
import cn.iverson.erp.biz.exception.ErpException;
import org.apache.struts2.ServletActionContext;
import cn.iverson.erp.biz.ISupplierBiz;
import cn.iverson.erp.entity.Supplier;

/**
 * 供应商Action 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private ISupplierBiz supplierBiz;
	private File file;//上传文件

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	private String fileFileName;//文件名
	private String fileContentType;//文件类型

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}
	
	/**
	 * 导出供应商列表/客户表
	 */
	public void export(){
		String fileName = "";
		//据类型生成文件名
		if((getT1().getType()).equals(Supplier.Type_SUPPLIER)){
			fileName = "供应商.xls";
		}
		if(getT1().getType().equals(Supplier.Type_CUSTOMER)){
			fileName ="客户.xls";
		}
		//转码
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" + 
					new String(fileName.getBytes(),"ISO-8859-1"));
			//调业务层导出
			supplierBiz.export(response.getOutputStream(), getT1());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void doImport(){
		if(!"application/vnd.ms-excel".equals(fileContentType)){
			ajaxReturn(false,"上传的文件必须为excel文件");
			return;
		}
		try {
			supplierBiz.doImport(new FileInputStream(file));
			ajaxReturn(true,"上传文件成功");
		} catch (ErpException e){
			ajaxReturn(false,e.getMessage());
		} catch (Exception e){
			ajaxReturn(false,"上传文件失败");
			e.printStackTrace();
		}
	}
}
