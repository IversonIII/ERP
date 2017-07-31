package cn.iverson.erp.action;
import cn.iverson.erp.biz.IDepBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.entity.Dep;

/**
 * 部门Action 
 * @author Administrator
 *
 */
public class DepAction extends BaseAction<Dep> {

	private IDepBiz depBiz;

	public void setDepBiz(IDepBiz depBiz) {
		this.depBiz = depBiz;
		super.setBaseBiz(this.depBiz);
	}
	
public void delete(){
		try {
			depBiz.delete(getId());
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
