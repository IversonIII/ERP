package cn.iverson.erp.dao;

import cn.iverson.erp.entity.Menu;

import java.util.List;

/**
 * 菜单数据访问接口
 * @author Administrator
 *
 */
public interface IMenuDao extends IBaseDao<Menu>{

    /**
     * 据员工的uuid获取菜单
     * @param uuid
     * @return
     */
    public List<Menu> getMenuByEmpuuid(Long uuid);
}
