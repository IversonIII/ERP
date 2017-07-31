package cn.iverson.erp.biz.impl;
import cn.iverson.erp.biz.IRoleBiz;
import cn.iverson.erp.dao.IMenuDao;
import cn.iverson.erp.dao.IRoleDao;
import cn.iverson.erp.entity.Menu;
import cn.iverson.erp.entity.Role;
import cn.iverson.erp.entity.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色业务逻辑类
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;
	private IMenuDao menuDao;


	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(this.roleDao);
	}

	/**
	 * 读取角色菜单
	 * @param uuid
	 * @return
	 */
	@Override
	public List<Tree> readRoleMenus(Long uuid) {
		Role role = roleDao.get(uuid);
		//角色下的菜单
		List<Menu> roleMenuList = role.getMenus();
		//所有菜单
		Menu rootMenu = menuDao.get("0");
		//一级菜单
		List<Menu> menu_1 = rootMenu.getMenus();
		//返回的所有的一级菜单
		List<Tree> treeList = new ArrayList<>();
		//把菜单转成Tree
		Tree tree = null;
		//遍历一级菜单
		for (Menu m_1 : menu_1){
			//用tree来接收一级菜单
			tree = createTree(m_1);
			//二级菜单
			if(null != m_1.getMenus() && m_1.getMenus().size() > 0){
				for (Menu m_2 : m_1.getMenus()){
					Tree tree2 = createTree(m_2);
					if (roleMenuList.contains(m_2)){
						tree2.setChecked(true);
					}
					tree.getChildren().add(tree2);
				}
			}
			treeList.add(tree);
		}
		return treeList;
	}

	@Override
	public void updateRoleMenus(Long uuid, String checkedStr) {
		//获取角色,持久化状态
		Role role = roleDao.get(uuid);
		//清空角色下的菜单权限
		role.setMenus(new ArrayList<Menu>());
		//获取菜单的ID数组
		String [] ids = checkedStr.split(",");
		//创建一个空的菜单去接收与**员工下的菜单
		Menu menu = null;
		for (String id : ids){
			//通过id去获取菜单
			menu = menuDao.get(id);
			//往表中插数据
			role.getMenus().add(menu);
		}
	}

	//增加目录树的方法
	private Tree createTree(Menu menu){
		Tree tree = new Tree();
		tree.setId(menu.getMenuid());
		tree.setText(menu.getMenuname());
		tree.setChildren(new ArrayList<Tree>());
		return tree;
	}
	//get和set方法
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
}
