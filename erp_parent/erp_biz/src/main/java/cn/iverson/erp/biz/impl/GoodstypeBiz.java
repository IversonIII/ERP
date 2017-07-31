package cn.iverson.erp.biz.impl;
import cn.iverson.erp.biz.IGoodstypeBiz;
import cn.iverson.erp.biz.exception.ErpException;
import cn.iverson.erp.dao.IGoodsDao;
import cn.iverson.erp.dao.IGoodstypeDao;
import cn.iverson.erp.entity.Goods;
import cn.iverson.erp.entity.Goodstype;
/**
 * 商品分类业务逻辑类
 * @author Administrator
 *
 */
public class GoodstypeBiz extends BaseBiz<Goodstype> implements IGoodstypeBiz {
	private IGoodsDao goodsDao;
	private IGoodstypeDao goodstypeDao;
	
	public void setGoodstypeDao(IGoodstypeDao goodstypeDao) {
		this.goodstypeDao = goodstypeDao;
		super.setBaseDao(this.goodstypeDao);
	}
	
	public void delete(Long uuid){
		Goods goods = new Goods();
		Goodstype goodstype = new Goodstype();
		goodstype.setUuid(uuid);
		goods.setGoodstype(goodstype);
		long count = goodsDao.getCount(goods,null,null);
		if(count > 0){
			throw new ErpException("这个类型下还存在商品,不可以删除");
		}
			super.delete(uuid);
	}
	
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
}
