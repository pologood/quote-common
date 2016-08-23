package com.baidu.stock.common;

/**
 * 证券状态定义 0: 停盘 1: 退市 2: 正常 3: 未上市 4: 可恢复熔断 5: 不可恢复熔断
 */
public enum StockStatusType {
    SUSPENDED(0), DELIST(1), NORMAL(2), UNLISTED(3), PBLOWN(4), NBLOWN(5);

    private int value = 0;

    private StockStatusType(int value) {
        this.value = value;
    }

    public static StockStatusType valueOf(int value) {
        switch (value) {
            case 0:
                return SUSPENDED;
            case 1:
                return DELIST;
            case 2:
                return NORMAL;
            case 3:
                return UNLISTED;
            case 4:
                return PBLOWN;
            case 5:
                return NBLOWN;
            default:
                return NORMAL;
        }
    }

    public int value() {
        return this.value;
    }
}
