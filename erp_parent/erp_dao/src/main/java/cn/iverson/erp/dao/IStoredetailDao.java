package cn.iverson.erp.dao;

import java.util.List;

import cn.iverson.erp.entity.Storealert;
import cn.iverson.erp.entity.Storedetail;
/**
 * 仓库库存数据访问接口
 * @author Administrator
 *
 */
public interface IStoredetailDao extends IBaseDao<Storedetail>{
	
	/**
	 * 获取预警列表
	 * @return
	 */
	public List<Storealert> getStorealertList();
}
