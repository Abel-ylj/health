package cn.ylj.enums;

public enum OrderStatusEnum {

    PENDING(0, "未到诊"),
    COMPLETE(1, "到诊"),
    ;

    private int code;

    private String msg;

    OrderStatusEnum(int code, String msg) {
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
