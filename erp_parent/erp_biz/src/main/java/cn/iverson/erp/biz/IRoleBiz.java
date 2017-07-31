package cn.iverson.erp.biz;
import cn.iverson.erp.entity.Role;
import cn.iverson.erp.entity.Tree;

import java.util.List;

/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{
    /**
     * 读取角色菜单
     * @param uuid
     * @return
     */
    public List<Tree> readRoleMenus(Long uuid);

    /**
     * 更新角色权限
     * @param uuid
     * @param checkedStr
     */
    public void updateRoleMenus(Long uuid,String checkedStr);
}

