package cn.iverson.erp.biz;
import java.io.OutputStream;

import cn.iverson.erp.entity.Orders;
/**
 * 订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{
	public void add(Orders orders);
	
	//采购订单审核
	public void doCheck(Long uuid,Long empUuid);
	
	public void doStart(Long uuid,Long empUuid);
	
	/**
	 * 导出excel文件
	 * @param os
	 * @param uuid
	 * @throws Exception
	 */
	public void exportById(OutputStream os,Long uuid) throws Exception;
}

