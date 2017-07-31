package cn.iverson.erp.action;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.iverson.bos.ws.Waybilldetail;
import cn.iverson.bos.ws.impl.IWaybillWs;
import org.apache.struts2.ServletActionContext;
import com.alibaba.fastjson.JSON;
import cn.iverson.erp.biz.IOrdersBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.entity.Emp;
import cn.iverson.erp.entity.Orderdetail;
import cn.iverson.erp.entity.Orders;

/**
 * 订单Action 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;
	private String json;
	private IWaybillWs waybillWs;
	private Long waybillsn;//运单号

	public void setWaybillsn(Long waybillsn) {
		this.waybillsn = waybillsn;
	}

	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
	}

	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		super.setBaseBiz(this.ordersBiz);
	}
	
	public void add(){
		Emp emp = getUser();
		if(null == emp){
			ajaxReturn(false, "还没登陆不可提交订单");
			return;
		}
		try {
			//获取订单对象
			Orders orders = getT();
			//
			orders.setCreater(emp.getUuid());
			//将订单详情从json里面转换成数组
			List<Orderdetail> orderdetails = JSON.parseArray(json, Orderdetail.class);
			//将订单详情放到订单中
			orders.setOrderdetails(orderdetails);
			//保存订单
			ordersBiz.add(orders);
			ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "添加订单失败");
		}
		
	}
	
	//审核
	public void doCheck(){
		Emp emp = getUser();
		if(null == emp){
			ajaxReturn(false, "还没登陆不能审核订单");
			return;
		}
		try {
			ordersBiz.doCheck(getId(), emp.getUuid());
			ajaxReturn(true, "审核成功");
		}catch(ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "审核异常");
		}
	}

	//确认
	public void doStart(){
		Emp emp = getUser();
		if(null == emp){
			ajaxReturn(false, "还没登陆不能确认订单");
			return;
		}
		try {
			ordersBiz.doStart(getId(), emp.getUuid());
			ajaxReturn(true, "确认成功");
		}catch(ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "确认异常");
		}
	}
	
	//自定义,我的订单
	public void myListByPage(){
		if(null == getT1()){
			//若为空添加查询条件
			setT1(new Orders());
		}
		//设置订单的创建人
		Emp emp = getUser();
		if(null != emp){
			Orders t1 = getT1();
			t1.setCreater(emp.getUuid());
			super.listByPage();
		}
		
	}
	
	public void exportById(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=orders" + getId() + ".xls");
			ordersBiz.exportById(response.getOutputStream(), getId());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询运单详情
	 */
	public void waybilldetailList(){
		List<Waybilldetail> waybilldetailList = waybillWs.waybilldetailList(waybillsn);
		write(JSON.toJSONString(waybilldetailList));
	}
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	

}
