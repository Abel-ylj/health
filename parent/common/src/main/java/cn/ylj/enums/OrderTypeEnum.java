package cn.ylj.enums;

public enum OrderTypeEnum {
    TEL(0, "电话预约"),
    WECHAT(1, "微信预约"),
    ;

    private int code;

    private String msg;

    OrderTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }

    public int code() {
        return code;
    }
}
