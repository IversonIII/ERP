package cn.iverson.erp.action;
import cn.iverson.erp.biz.IRoleBiz;
import cn.iverson.erp.entity.Role;
import cn.iverson.erp.entity.Tree;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;
	private String checkedStr;


	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		super.setBaseBiz(this.roleBiz);
	}

	/**
	 * 读取角色菜单
	 */
	public void readRoleMenus(){
		List<Tree> treeList = roleBiz.readRoleMenus(getId());
		write(JSON.toJSONString(treeList));
	}

	/**
	 * 更新角色权限
	 */
	public void updateRoleMenus(){
		try {
			roleBiz.updateRoleMenus(getId(),checkedStr);
			ajaxReturn(true,"更新角色权限成功");
		} catch (Exception e) {
			ajaxReturn(false,"更新角色权限失败");
			e.printStackTrace();
		}
	}

	//get和set方法
	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

}
