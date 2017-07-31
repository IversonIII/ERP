package cn.iverson.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import cn.iverson.erp.dao.IMenuDao;
import cn.iverson.erp.entity.Menu;

import java.util.List;

/**
 * 菜单数据访问类
 * @author Administrator
 *
 */
public class MenuDao extends BaseDao<Menu> implements IMenuDao {

	/**
	 * 构建查询条件
	 * @param menu1
	 * @param menu2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Menu menu1,Menu menu2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Menu.class);
		return dc;
	}

	/**
	 * 据员工的uuid获取菜单
	 * @param uuid
	 * @return
	 */
	@Override
	public List<Menu> getMenuByEmpuuid(Long uuid) {
		String hql = "Select m from Emp e join e.roles r join r.menus m where e.uuid=?";
		List<Menu> menuList = (List<Menu>) this.getHibernateTemplate().find(hql,uuid);
		return menuList;
	}
}
