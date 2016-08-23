package com.baidu.stock.jms.model;

public class Message {

	public static String GROUP = "JMSXGroupID" ;

	//快照类型
	public static String SNAPSHOT_TYPE = "snapshot" ;
	//成交明细
	public static String DEAL_DETAIL_TYPE = "deal_detail" ;
	//股票信息
	public static String STOCK_INFO_TYPE = "stock_info" ;
	//基础信息更新通知
	public static String MATERIAL_UPDATE_TYPE = "material_update" ;
	
	protected String groupId;
	protected String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
