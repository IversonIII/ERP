package cn.iverson.erp.action;
import cn.iverson.erp.biz.IGoodstypeBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.entity.Goodstype;

/**
 * 商品分类Action 
 * @author Administrator
 *
 */
public class GoodstypeAction extends BaseAction<Goodstype> {

	private IGoodstypeBiz goodstypeBiz;

	public void setGoodstypeBiz(IGoodstypeBiz goodstypeBiz) {
		this.goodstypeBiz = goodstypeBiz;
		super.setBaseBiz(this.goodstypeBiz);
	}
	public void delete(){
		try {
			goodstypeBiz.delete(getId());
			ajaxReturn(true, "删除成功");
		}catch(ErpException erpE){//删除部门之前我们要确认该部门下是否还有员工
			erpE.printStackTrace();
			ajaxReturn(false,erpE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "删除失败");
		}
	}

}
