package cn.iverson.erp.biz.impl;
import cn.iverson.erp.biz.IStoreBiz;
import cn.iverson.erp.dao.IStoreDao;
import cn.iverson.erp.entity.Store;
/**
 * 仓库业务逻辑类
 * @author Administrator
 *
 */
public class StoreBiz extends BaseBiz<Store> implements IStoreBiz {

	private IStoreDao storeDao;
	
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
		super.setBaseDao(this.storeDao);
	}
	
}
