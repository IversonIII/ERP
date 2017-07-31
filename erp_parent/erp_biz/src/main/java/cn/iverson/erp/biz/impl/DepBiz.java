package cn.iverson.erp.biz.impl;

import cn.iverson.erp.biz.IDepBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.dao.IDepDao;
import cn.iverson.erp.dao.IEmpDao;
import cn.iverson.erp.entity.Dep;
import cn.iverson.erp.entity.Emp;
/**
 * 部门业务逻辑类
 * @author Administrator
 *
 */
public class DepBiz extends BaseBiz<Dep> implements IDepBiz {

	private IDepDao depDao;
	
	private IEmpDao empDao;
	
	public void setDepDao(IDepDao depDao) {
		this.depDao = depDao;
		super.setBaseDao(this.depDao);
	}
	
	public void delete(Long uuid){
		Emp emp = new Emp();//员工
		Dep dep = new Dep();//部门
		dep.setUuid(uuid);
		emp.setDep(dep);
		//计数
		long count = empDao.getCount(emp,null,null);
		if(count > 0){
			throw new ErpException("这个部门下还存在,不可以删除");
		}
			super.delete(uuid);
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}
}
