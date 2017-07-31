package cn.iverson.erp.biz;

import cn.iverson.erp.entity.Emp;
import cn.iverson.erp.entity.Tree;

import java.util.List;

/**
 * 员工业务逻辑层接口
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp>{

	/**
	 * 登录方法
	 * @param username
	 * @param password
	 * @return
	 */
	public Emp findByUsernameAndPsw(String username,String password);

	/**
	 * 修改密码
	 * @param uuid
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(Long uuid, String oldPwd, String newPwd);
	
	public void updatePwd_reset(Long uuid,String newPwd);

	/**
	 * 获取用户角色
	 * @param uuid
	 * @return
	 */
	public List<Tree> readEmpRoles(Long uuid);

	/**
	 * 更新用户-->角色
	 * @param uuid
	 * @param checkedStr
	 */
	public void updateEmpRoles(Long uuid,String checkedStr);
}

