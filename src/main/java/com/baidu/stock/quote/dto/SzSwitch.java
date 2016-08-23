/**
 * @author 作者 : 楚运动
 */
package com.baidu.stock.quote.dto;

import java.io.Serializable;

/**
 * 开关状态
 */
public class SzSwitch implements Serializable {
    // 开关类型
    private String switchType;
    // 开关状态
    private String switchStatus;

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

}