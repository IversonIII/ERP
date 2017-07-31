package cn.iverson.erp.dao;

import cn.iverson.erp.entity.Emp;
/**
 * 员工数据访问接口
 * @author Administrator
 *
 */
public interface IEmpDao extends IBaseDao<Emp>{
	public Emp findByUsernameAndPsw(String username,String password);
	
	public void updatePwd(String newPwd, Long uuid);
}
