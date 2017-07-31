package cn.iverson.erp.action;
import cn.iverson.erp.biz.IStoreBiz;
import cn.iverson.erp.entity.Store;

/**
 * 仓库Action 
 * @author Administrator
 *
 */
public class StoreAction extends BaseAction<Store> {

	private IStoreBiz storeBiz;

	public void setStoreBiz(IStoreBiz storeBiz) {
		this.storeBiz = storeBiz;
		super.setBaseBiz(this.storeBiz);
	}
	
	//只显示当前登录用户下的仓库

}
