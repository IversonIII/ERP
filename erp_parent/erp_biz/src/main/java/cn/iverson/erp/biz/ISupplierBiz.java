package cn.iverson.erp.biz;
import java.io.InputStream;
import java.io.OutputStream;

import cn.iverson.erp.entity.Supplier;
/**
 * 供应商业务逻辑层接口
 * @author Administrator
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{

	/**
	 * 导出供应商列表/客户表
	 * @param os
	 * @param t1
	 */
	public void export(OutputStream os,Supplier t1);

	/**
	 * 数据导入
	 * @param is
	 * @throws Exception
	 */
	public void doImport(InputStream is) throws Exception;
}

