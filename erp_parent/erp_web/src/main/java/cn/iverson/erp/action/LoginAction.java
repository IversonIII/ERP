package cn.iverson.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import cn.iverson.erp.biz.IEmpBiz;
import cn.iverson.erp.entity.Emp;

public class LoginAction {
	private String username;
	private String password;
	private static final Logger log = LoggerFactory.getLogger(LoginAction.class);
	
	public void checkUser(){
		try {
			//创建令牌
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
			//获取主题
			Subject subject = SecurityUtils.getSubject();
			//执行login方法
			subject.login(usernamePasswordToken);
			ajaxReturn(true,"登录成功");
		} catch (Exception e) {
			ajaxReturn(false, "登录失败");
			log.error("登录失败",e);
		}
	}
	public void showName(){
		Emp emp = (Emp) SecurityUtils.getSubject().getPrincipal();
		if(emp != null){
			ajaxReturn(true,emp.getName());
		}else{
			ajaxReturn(false, "");
		}
	}
	
	public void loginOut(){
		//ActionContext.getContext().getSession().remove("user");
		SecurityUtils.getSubject().logout();
	}
	
	public void ajaxReturn(boolean success, String message){
		//返回前端的JSON数据
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success",success);
		rtn.put("message",message);
		write(JSON.toJSONString(rtn));
	}
	
	/**
	 * 输出字符串到前端
	 * @param jsonString
	 */
	public void write(String jsonString){
		try {
			//响应对象
			HttpServletResponse response = ServletActionContext.getResponse();
			//设置编码
			response.setContentType("text/html;charset=utf-8"); 
			//输出给页面
			response.getWriter().write(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//getter和setter方法
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
