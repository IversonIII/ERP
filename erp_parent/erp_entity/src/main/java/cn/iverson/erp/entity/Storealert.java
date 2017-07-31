package cn.iverson.erp.entity;

public class Storealert {
	
	private Long uuid;//商品编号
	private String name;//商品名称
	private Long storeNum;//库存数量
	private Long outNum;//待发货数量
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(Long storeNum) {
		this.storeNum = storeNum;
	}
	public Long getOutNum() {
		return outNum;
	}
	public void setOutNum(Long outNum) {
		this.outNum = outNum;
	}
	
}
