package cn.iverson.erp.biz;
import cn.iverson.erp.entity.Orderdetail;
/**
 * 订单明细业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrderdetailBiz extends IBaseBiz<Orderdetail>{
	
	public void doInStore(Long uuid,Long storeUuid,Long empUuid);
	
	public void doOutStore(Long uuid, Long storeUuid, Long empUuid);
}
