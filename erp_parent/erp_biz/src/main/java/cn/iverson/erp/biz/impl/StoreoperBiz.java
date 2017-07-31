package cn.iverson.erp.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.iverson.erp.biz.IStoreoperBiz;
import cn.iverson.erp.dao.IEmpDao;
import cn.iverson.erp.dao.IGoodsDao;
import cn.iverson.erp.dao.IStoreDao;
import cn.iverson.erp.dao.IStoreoperDao;
import cn.iverson.erp.entity.Storeoper;
/**
 * 仓库操作记录业务逻辑类
 * @author Administrator
 *
 */
public class StoreoperBiz extends BaseBiz<Storeoper> implements IStoreoperBiz {

	private IStoreoperDao storeoperDao;
	private IStoreDao storeDao;
	private IGoodsDao goodsDao;
	private IEmpDao empDao;
	
	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
		super.setBaseDao(this.storeoperDao);
	}
	
	//分页查询
	public List<Storeoper> getListByPage(Storeoper t1,Storeoper t2,Object param,int firstResult,int maxResults){
		List<Storeoper> storeoperList = super.getListByPage(t1, t2, param, firstResult, maxResults);
		Map<Long,String> empNameMap = new HashMap<Long,String>();
		Map<Long,String> goodsNameMap = new HashMap<Long,String>();
		Map<Long,String> storeNameMap = new HashMap<Long,String>();
		for(Storeoper so: storeoperList){
			so.setEmpName(getEmpName(so.getEmpuuid(), empNameMap));
			so.setGoodsName(getGoodsName(so.getGoodsuuid(), goodsNameMap));
			so.setStoreName(getStoreName(so.getStoreuuid(), storeNameMap));
		}
		return storeoperList;
	}
	
	//创建数组存empuuid:empName值
	private String getEmpName(Long uuid,Map<Long,String> empNameMap){
		if(null == uuid){
			return null;
		}
		String empName = empNameMap.get(uuid);
		if(null == empName){
			empName = empDao.get(uuid).getName();
			empNameMap.put(uuid, empName);
		}
		return empName;
	}
	
	
	//创建数组存goodsuuid:goodsName值
	private String getGoodsName(Long uuid,Map<Long,String> goodsNameMap){
		if(null == uuid){
			return null;
		}
		String goodsName = goodsNameMap.get(uuid);
		if(null == goodsName){
			goodsName = goodsDao.get(uuid).getName();
			goodsNameMap.put(uuid, goodsName);
		}
		return goodsName;
	}
	
	//创建数组存storeuuid:storeName值
	private String getStoreName(Long uuid,Map<Long,String> storeNameMap){
		if(null == uuid){
			return null;
		}
		String storeName = storeNameMap.get(uuid);
		if(null == storeName){
			storeName = storeDao.get(uuid).getName();
			storeNameMap.put(uuid, storeName);
		}
		return storeName;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}
}
