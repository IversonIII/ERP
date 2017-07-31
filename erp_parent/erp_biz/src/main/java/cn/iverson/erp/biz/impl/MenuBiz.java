package cn.iverson.erp.biz.impl;
import cn.iverson.erp.biz.IMenuBiz;
import cn.iverson.erp.dao.IMenuDao;
import cn.iverson.erp.entity.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}

	/**
	 * 据员工的uuid获取菜单
	 * @param uuid
	 * @return
	 */
	@Override
	public List<Menu> getMenuByEmpuuid(Long uuid){
		return menuDao.getMenuByEmpuuid(uuid);
	}

	public Menu readMenusByEmpuuid(Long uuid) {
		List<Menu> menuList = menuDao.getMenuByEmpuuid(uuid);
		//根菜单
		Menu menu = menuDao.get("0");
		//复制根菜单(tMenu)
		Menu tMenu = cloneMenu(menu);
		//复制一级菜单
		//创建一二级菜单全局变量,接收值
		Menu menu_1 = null;//接收一级菜单的值
		Menu menu_2 = null;//接收二级菜单的值
		for (Menu menu1 : menu.getMenus() ){//遍历获取并克隆一级菜单的值
			menu_1 = cloneMenu(menu1);
			for (Menu menu2 : menu1.getMenus()){//遍历获取二级菜单的值
				if(menuList.contains(menu2)){//判断用户菜单是否包含该菜单的值,包含的话克隆到menu_2里面,并添加到menu_1里面
					menu_2 = cloneMenu(menu2);
					menu_1.getMenus().add(menu_2);
				}
			}
			if (menu_1.getMenus().size() > 0){//判断一级菜单是是否有二级菜单,有的话就将该一级菜单添加到根菜单下
				tMenu.getMenus().add(menu_1);
			}
		}
		return tMenu;
	}

	private Menu cloneMenu(Menu src){
		Menu newMenu = new Menu();
		newMenu.setIcon(src.getIcon());
		newMenu.setMenuid(src.getMenuid());
		newMenu.setMenuname(src.getMenuname());
		newMenu.setUrl(src.getUrl());
		newMenu.setMenus(new ArrayList<Menu>());
		return newMenu;
	}
}
