package com.baidu.stock.common;

/**
 * 证券类型定义 0:A股票 1：期货，2：期权 3：外汇，4指数，5：场内基金，6：债券、7：认购权证，8：认沽权证，9： 牛证，10：熊证， 11：其他，
 * 12：表示场外，13：表示货币，14：表示B股，15：回购，16：场内的货币基金，17：港股基金
 */
public enum AssetType {
    A_STOCK(0), FUTURE(1), OPTION(2), FOREX(3), INDEX(4), FUND(5), BOND(6), CALL_WARRANT(7), PUT_WARRANT(8),
    BULL_WARRANT(9), BEAR_WARRANT(10), OTHERS(11), B_STOCK(14), REPURCHASE(15), HK_FUND(17);

    private int value = 0;

    private AssetType(int value) {
        this.value = value;
    }

    public static AssetType valueOf(int value) {
        switch (value) {
            case 0:
                return A_STOCK;
            case 1:
                return FUTURE;
            case 2:
                return OPTION;
            case 3:
                return FOREX;
            case 4:
                return INDEX;
            case 5:
                return FUND;
            case 6:
                return BOND;
            case 7:
                return CALL_WARRANT;
            case 8:
                return PUT_WARRANT;
            case 9:
                return BULL_WARRANT;
            case 10:
                return BEAR_WARRANT;
            case 11:
                return OTHERS;
            case 14:
                return B_STOCK;
            case 15:
                return REPURCHASE;
            case 17:
                return HK_FUND;
            default:
                return OTHERS;
        }
    }

    public int value() {
        return this.value;
    }
}
