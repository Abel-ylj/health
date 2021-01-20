package cn.ylj.constant;

public class RedisConstant {

    //套餐图片所有图片名称(被用户上传到oss上的图片集合)
    public static final String SETMEAL_PIC_RESOURCES = "health:setmealPicResources";

    //套餐图片保存在数据库中的图片名称(被套餐实际引用的图片集合)
    public static final String SETMEAL_PIC_DB_RESOURCES = "health:setmealPicDbResources";

    //验证码
    public static final String SENDTYPE_ORDER = "health:order001:";//用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_LOGIN = "health:login002";//用于缓存手机号快速登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "health:forgetpwd003";//用于缓存找回密码时发送的验证码
}
