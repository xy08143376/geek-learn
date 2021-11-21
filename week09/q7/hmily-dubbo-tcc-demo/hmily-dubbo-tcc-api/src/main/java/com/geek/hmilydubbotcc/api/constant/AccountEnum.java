package com.geek.hmilydubbotcc.api.constant;

/**
 * @author itguoy
 * @date 2021-11-19 10:48
 */
public enum AccountEnum {

    /**
     * 人民币
     */
    CNY(1),
    /**
     * 美元
     */
    DOLLAR(2);

    private int type;

    AccountEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
