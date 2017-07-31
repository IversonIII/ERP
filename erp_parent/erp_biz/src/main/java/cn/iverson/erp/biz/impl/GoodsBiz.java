package cn.iverson.erp.biz.impl;

import cn.iverson.erp.biz.IGoodsBiz;
import cn.iverson.erp.dao.IGoodsDao;
import cn.iverson.erp.entity.Goods;
/**
 * 商品业务逻辑类
 * @author Administrator
 *
 */
public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

	private IGoodsDao goodsDao;
	
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		super.setBaseDao(this.goodsDao);
	}
}
