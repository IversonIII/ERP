package cn.iverson.erp.realm;

import cn.iverson.erp.biz.IEmpBiz;
import cn.iverson.erp.biz.IMenuBiz;
import cn.iverson.erp.entity.Emp;
import cn.iverson.erp.entity.Menu;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * ErpRealm
 * Created by Iverson on 2017/7/30.
 */
public class ErpRealm extends AuthorizingRealm {

    private IEmpBiz empBiz;
    private IMenuBiz menuBiz;

    /**
     * 授权方法(doGetAuthorizationInfo)
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权方法........");
        //获取当前登录的用户
        Emp emp = (Emp) principalCollection.getPrimaryPrincipal();
        //获取当前用户所对应的权限集合
        List<Menu> menuList = menuBiz.getMenuByEmpuuid(emp.getUuid());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Menu menu : menuList){
            simpleAuthorizationInfo.addStringPermission(menu.getMenuname());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证方法(doGetAuthenticationInfo)
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证方法.........");
        //转成实现类就可以得到用户名和密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //获取usernamePasswordToken里面的密码,因为数据库里面的密码已经经过MD5加密了,对不上我们页面上输入的密码;
        String pwd = new String(usernamePasswordToken.getPassword());
        //据用户名(username)和密码(pwd)查找用户
        Emp emp = empBiz.findByUsernameAndPsw(usernamePasswordToken.getUsername(),pwd);
        //验证成功
        if(null != emp){
            //principal,主角,就是登陆的User
            //credentials,证书的凭证,这里就是我们的密码
            //realmName,当前的realm的名称
            return new SimpleAuthenticationInfo(emp,pwd,getName());
        }
        //emp==null 时返回空
        return null;
    }

    //get和set方法所在地
    public void setEmpBiz(IEmpBiz empBiz) {
        this.empBiz = empBiz;
    }
    public void setMenuBiz(IMenuBiz menuBiz) {
        this.menuBiz = menuBiz;
    }
}
