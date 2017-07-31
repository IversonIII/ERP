package cn.iverson.erp.action;

import cn.iverson.erp.entity.Emp;
import com.alibaba.fastjson.JSON;
import cn.iverson.erp.biz.IMenuBiz;
import cn.iverson.erp.entity.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {
	private static final Logger log = LoggerFactory.getLogger(MenuAction.class);
	private IMenuBiz menuBiz;

	/**
	 * 获取菜单树
	 */
	public void getMenuTree(){
		Emp emp = getUser();
		if(null != emp) {
			Menu menu = menuBiz.readMenusByEmpuuid(emp.getUuid());
			String menuJsonString = JSON.toJSONString(menu);
			log.debug(menuJsonString);
			write(menuJsonString);
		}else{
			log.info("用户没有登陆");
		}
	}

	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}

}
