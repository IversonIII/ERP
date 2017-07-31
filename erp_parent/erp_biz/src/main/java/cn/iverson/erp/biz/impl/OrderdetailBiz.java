package cn.iverson.erp.biz.impl;
import java.util.Date;
import java.util.List;

import cn.iverson.bos.ws.impl.IWaybillWs;
import cn.iverson.erp.biz.IOrderdetailBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.dao.IOrderdetailDao;
import cn.iverson.erp.dao.IStoredetailDao;
import cn.iverson.erp.dao.IStoreoperDao;
import cn.iverson.erp.dao.ISupplierDao;
import cn.iverson.erp.entity.*;

/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	private IStoredetailDao storedetailDao;
	private IStoreoperDao storeoperDao;
	private IWaybillWs waybillWs;
	private ISupplierDao supplierDao;

	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		super.setBaseDao(this.orderdetailDao);
	}

	@Override
	public void doInStore(Long uuid, Long storeUuid, Long empUuid) {
		//1.更新商品明细
		Orderdetail orderdetail = this.orderdetailDao.get(uuid);
		//判断状态     是否已经入库
		if(!(Orderdetail.STATE_NOT_IN.equals(orderdetail.getState()))){
			throw new ErpException("该明细已经入库了");
		}
		//更新状态
		orderdetail.setState(Orderdetail.STATE_IN);
		//加入操作人
		orderdetail.setEnder(empUuid);
		//入到?仓库
		orderdetail.setStoreuuid(storeUuid);
		//加入时间
		orderdetail.setEndtime(new Date());
		
		//2.更新商品仓库库存
		Storedetail storeDetail = new Storedetail();
		//设置商品编号
		storeDetail.setGoodsuuid(orderdetail.getGoodsuuid());
		//保存仓库编号
		storeDetail.setStoreuuid(storeUuid);
		List<Storedetail> storedetailList = storedetailDao.getList(storeDetail, null, null);
		//判断是否存在库存信息
		if(storedetailList.size()>0){
			storeDetail = storedetailList.get(0);
			storeDetail.setNum(storeDetail.getNum() + orderdetail.getNum());
		}else{
			//不存在的话就增加库存信息
			storeDetail.setNum(orderdetail.getNum());
			//最后加入数据库
			storedetailDao.add(storeDetail);
		}
		
		
		//3.增加商品仓库库存更新记录
		Storeoper storeoper = new Storeoper();
		//设置操作人
		storeoper.setEmpuuid(empUuid);
		//入库商品编号
		storeoper.setGoodsuuid(orderdetail.getGoodsuuid());
		//数量==>入库
		storeoper.setNum(orderdetail.getNum());
		//时间==>入库
		storeoper.setOpertime(orderdetail.getEndtime());
		//入的哪个仓库
		storeoper.setStoreuuid(storeUuid);
		//状态
		storeoper.setType(Storeoper.TYPE_IN);
		storeoperDao.add(storeoper);
		
		//4.是否需要更新订单状态的判断
		//订单信息
		Orders orders = orderdetail.getOrders();
		//构建查询条件,统计订单中State=0的明细的个数
		Orderdetail countP = new Orderdetail();
		countP.setState(Orderdetail.STATE_NOT_IN);
		countP.setOrders(orders);
		Long count = orderdetailDao.getCount(countP, null, null);
		if(count == 0){
			//更新状态
			orders.setState(Orders.STATE_END);
			//设置操作人
			orders.setEnder(empUuid);
			//设置操作时间
			orders.setEndtime(orderdetail.getEndtime());
		}
		
	}
	
	public void doOutStore(Long uuid, Long storeUuid, Long empUuid){
		//1,获取订单明细
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		//判断状态,若未出库才执行
		if(!(orderdetail.getState().equals(Orderdetail.STATE_NOT_OUT))){
			throw new ErpException("该明细已经出库了,不能重复操作");
		}
		//更新订单的明细
		orderdetail.setEnder(empUuid);
		orderdetail.setEndtime(new Date());
		orderdetail.setState(Orderdetail.STATE_OUT);
		orderdetail.setStoreuuid(storeUuid);
		
		//2,更新商品仓库库存
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
		//storedetail.setNum(num);
		storedetail.setStoreuuid(storeUuid);
		List<Storedetail> storedetailList = storedetailDao.getList(storedetail, null, null);
		if(storedetailList.size() > 0){
			storedetail = storedetailList.get(0);
			//数量减少
			//取出库存数
			long storenum = storedetail.getNum();
			//要出库的数量
			long outnum = orderdetail.getNum();
			//出完库剩下的数量
			long newStorenum = storenum - outnum;
			if(newStorenum < 0){
				throw new ErpException("库存不足");
			}
			storedetail.setNum(newStorenum);
		}else{
			//报库存不足
			throw new ErpException("库存不足");
		}
		
		//3,添加仓库变更的操作记录
		Storeoper storeoper = new Storeoper();
		storeoper.setEmpuuid(empUuid);
		storeoper.setGoodsuuid(orderdetail.getGoodsuuid());
		storeoper.setNum(orderdetail.getNum());
		storeoper.setOpertime(orderdetail.getEndtime());
		storeoper.setStoreuuid(storeUuid);
		storeoper.setType(Storeoper.TYPE_OUT);
		storeoperDao.add(storeoper);
		
		//4,检查是否订单的所有明细都已经出库
		Orderdetail queryP = new Orderdetail();
		Orders orders = orderdetail.getOrders();
		queryP.setOrders(orders);
		queryP.setState(Orders.STATE_NOT_OUT);
		Long cnt = orderdetailDao.getCount(queryP, null, null);
		if(cnt == 0){
			orders.setState(Orders.STATE_OUT);
			orders.setEnder(empUuid);
			orders.setEndtime(orderdetail.getEndtime());
			Supplier supplier = supplierDao.get(orders.getSupplieruuid());
			Long waybillsn = waybillWs.addWaybill(1l,supplier.getAddress(),supplier.getName(),supplier.getTele(),"--");
			orders.setWaybillsn(waybillsn);
		}
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
}