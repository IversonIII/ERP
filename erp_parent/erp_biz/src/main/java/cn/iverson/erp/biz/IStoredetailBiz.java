package cn.iverson.erp.biz;
import java.util.List;

import javax.mail.MessagingException;

import cn.iverson.erp.entity.Storealert;
import cn.iverson.erp.entity.Storedetail;
/**
 * 仓库库存业务逻辑层接口
 * @author Administrator
 *
 */
public interface IStoredetailBiz extends IBaseBiz<Storedetail>{
	
	/**
	 * 获取预警列表
	 * @return
	 */
	public List<Storealert> getStorealertList();
	
	public void sendStoreAlertMail() throws MessagingException;
}

