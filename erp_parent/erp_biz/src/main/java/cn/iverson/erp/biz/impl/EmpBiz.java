package cn.iverson.erp.biz.impl;

import cn.iverson.erp.dao.IRoleDao;
import cn.iverson.erp.entity.Role;
import cn.iverson.erp.entity.Tree;
import org.apache.shiro.crypto.hash.Md5Hash;
import cn.iverson.erp.biz.IEmpBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.dao.IEmpDao;
import cn.iverson.erp.entity.Emp;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	private IRoleDao roleDao;

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}
	
	//密码加密
	public void add(Emp emp){
		//设置盐
		String salt = emp.getUsername();
		//设置默认密码为用户名
		Md5Hash md5 = new Md5Hash(salt,salt,2);
		emp.setPwd(md5.toString());
		empDao.add(emp);
	}
	//用户登录
	@Override
	public Emp findByUsernameAndPsw(String username, String password) {
		Md5Hash md5 = new Md5Hash(password, username, 2);
		return empDao.findByUsernameAndPsw(username,md5.toString());
	}
	
	//修改密码
	public void updatePwd(Long uuid,String oldPwd,String newPwd){
		Emp emp = empDao.get(uuid);
		//加密旧密码,与数据库对比查出对象
		Md5Hash md5_1 = new Md5Hash(oldPwd, emp.getUsername(), 2);
		if(!emp.getPwd().equals(md5_1.toString())){
			throw new ErpException("输入原密码不正确");
		}
		//加密新密码,更新数据库
		Md5Hash md5_2 = new Md5Hash(newPwd,emp.getUsername(),2);
		empDao.updatePwd(md5_2.toString(), uuid);
	}
	
	//管理员管理各种用户的密码
	public void updatePwd_reset(Long uuid,String newPwd){
		Emp emp = empDao.get(uuid);
		Md5Hash md5_2 = new Md5Hash(newPwd,emp.getUsername(),2);
		empDao.updatePwd(md5_2.toString(), uuid);
	}

	/**
	 * 获取用户角色
	 * @param uuid
	 * @return
	 */
	@Override
	public List<Tree> readEmpRoles(Long uuid) {
		//新建一个集合保存用户的菜单集合
		List<Tree> treeList = new ArrayList<>();
		//获取用户
		Emp emp = empDao.get(uuid);
		//获取用户下的角色列表
		List<Role> empRoleList= emp.getRoles();
		//获取所有的角色
		List<Role> roleList = roleDao.getList(null,null,null);
		//创建菜单树
		Tree tree = null;
		for (Role role : roleList){
			tree = new Tree();
			tree.setId(role.getUuid() + "");
			tree.setText(role.getName());
			if (empRoleList.contains(role)){
				tree.setChecked(true);
			}
			treeList.add(tree);
		}
		return treeList;
	}

	/**
	 * 更新用户-->角色
	 * @param uuid
	 * @param checkedStr
	 */
	@Override
	public void updateEmpRoles(Long uuid, String checkedStr) {
		//获取用户
		Emp emp = empDao.get(uuid);
		//清空用户的角色
		emp.setRoles(new ArrayList<Role>());
		//用数组存放角色的id
		String [] ids = checkedStr.split(",");
		Role role = null;
		for (String id : ids){
			//获取角色通过id
			role = roleDao.get(Long.valueOf(id));
			//给用户添加角色
			emp.getRoles().add(role);
		}

	}

	//get和set方法所在地
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
