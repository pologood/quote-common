package com.baidu.stock.util;

import com.baidu.stock.common.AssetType;

public class AssetTypeUtil {
	  /* 深圳:
		                指数：股票代码以39开头
		     A股： 股票代码以000 001 002 003 004或30开头
		     B股： 股票代码以20开头
		                基金：股票代码以15或16或18开头
		                国债：股票代码以10开头
		                企债：股票代码以11或12开头
		                回购：股票代码以13开头
		                上海：
		               指数：股票代码以000开头
		     A股： 股票代码以60开头
		     B股： 股票代码以900开头
		                基金：股票代码以50 51开头
		                回购：股票代码以20开头
		                国债：股票代码以02或01开头
		                企债：股票代码以12或11开头
		                其他：权证，期货，期权*/
		                
		/**
		 * 根据股票代码和交易所区分股票的类型
		 * 证券类型定义 0:A股票 1：期货，2：期权 3：外汇，4指数，5：场内基金，6：债券、7：认购权证，8：认沽权证，9： 牛证，10：熊证， 11：其他，12：表示场外，13：表示货币，14：表示B股，15：回购，16：场内的货币基金，17：港股基金
		 * @param SC 交易所 sh/sz
		 * @param ZQDM 股票代码
		 * @return
		 */
		public static int requireTypes(String SC, String ZQDM){
			if (SC.equalsIgnoreCase("sz")){
				if (ZQDM.startsWith("39")){
					return AssetType.INDEX.value();
				}
				else if (ZQDM.startsWith("000") || ZQDM.startsWith("001")|| ZQDM.startsWith("002")|| ZQDM.startsWith("003")|| ZQDM.startsWith("004")||ZQDM.startsWith("30")){
					return AssetType.A_STOCK.value();
				}else if (ZQDM.startsWith("20")){
					return AssetType.B_STOCK.value();
				}
	            else if (ZQDM.startsWith("15") || ZQDM.startsWith("16")||ZQDM.startsWith("18")){
	            	return AssetType.FUND.value();
				}else if (ZQDM.startsWith("13")){
					return AssetType.REPURCHASE.value();
				}
			}else if(SC.equalsIgnoreCase("sh")){
				if (ZQDM.startsWith("000")){
					return AssetType.INDEX.value();
				}
				else if (ZQDM.startsWith("60")){
					return AssetType.A_STOCK.value();
				}
	           else if (ZQDM.startsWith("900")){
	        	   return AssetType.B_STOCK.value();
				}
	            else if (ZQDM.startsWith("50")||ZQDM.startsWith("51")){
	            	return AssetType.FUND.value();
				}else if (ZQDM.startsWith("20")){
					return AssetType.REPURCHASE.value();
				}
			}
			return AssetType.OTHERS.value();
		}
}

