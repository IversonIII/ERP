package cn.iverson.erp.action;

import cn.iverson.erp.biz.IEmpBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.entity.Emp;
import cn.iverson.erp.entity.Tree;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private String oldPwd;
	private String newPwd;
	private IEmpBiz empBiz;
	private String checkedStr;

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}

	/**
	 * 修改密码
	 */
	public void updatePwd(){
		try {
			empBiz.updatePwd(getUser().getUuid(),oldPwd,newPwd);
			ajaxReturn(true,"密码修改成功");
		} catch (ErpException e) {
			e.printStackTrace();
			ajaxReturn(false,e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			ajaxReturn(false,"密码修改异常");
		}
	}

	/**
	 *
	 */
	public void updatePwd_reset(){
		try {
			empBiz.updatePwd_reset(getId(),newPwd);
			ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改失败");
		}
	}

	/**
	 * 获取用户角色信息
	 */
	public void readEmpRoles(){
		List<Tree> treeList = empBiz.readEmpRoles(getId());
		write(JSON.toJSONString(treeList));
	}

	public void updateEmpRoles(){
		try {
			empBiz.updateEmpRoles(getId(),checkedStr);
			ajaxReturn(true,"更新用户角色成功");
		} catch (Exception e) {
			ajaxReturn(false,"更新用户角色失败");
			e.printStackTrace();
		}
	}

	//get和set方法
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public IEmpBiz getEmpBiz() {
		return empBiz;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

}
