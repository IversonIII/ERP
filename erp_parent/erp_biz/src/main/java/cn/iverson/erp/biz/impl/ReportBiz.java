package cn.iverson.erp.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iverson.erp.biz.IReportBiz;
import cn.iverson.erp.dao.IReportDao;

public class ReportBiz implements IReportBiz {
	private IReportDao reportDao;
	
	//销售报表统计
	@Override
	public List<?> orderReport(Date startDate, Date endDate) {
		return reportDao.orderReport(startDate, endDate);
	}
	
	//销售趋势
	@Override
	public List<Map<String, Object>> trendReport(int year) {
		//保存每个月的销售额
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(12);
		//获取当年每个月的销售额
		List<Map<String, Object>> yearData = reportDao.getSumMoney(year);
		//
		Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
		for(Map<String,Object> m:yearData){
			map.put((String) m.get("month"), m);
		}
		Map<String,Object> data = null;
		//按照12个月来对数据进行封装,最终以List<Map<String,Object>>的形式返回
		for(int i = 1;i <= 12;i++){
			data = map.get(i+"月");
			if(null == data){//如果当月没有营业额,就用月份:0补上
				data = new HashMap<String,Object>();
				data.put("month", i+"月");
				data.put("y", 0);
			}
			result.add(data);
		}
		return result;
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	
}
