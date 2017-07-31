package cn.iverson.erp.biz;
import cn.iverson.erp.entity.Menu;

import java.util.List;

/**
 * 菜单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IMenuBiz extends IBaseBiz<Menu>{

    /**
     * 据员工的uuid获取菜单
     * @param uuid
     * @return
     */
    public List<Menu> getMenuByEmpuuid(Long uuid);

    /**
     * 据员工的uuid获取菜单
     * @param uuid
     * @return
     */
    public Menu readMenusByEmpuuid(Long uuid);
}

