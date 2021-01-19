package cn.ylj.jobs;

import cn.ylj.constant.RedisConstant;
import cn.ylj.utils.AliOssUtils;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 自定义Job，实现定时清理垃圾图片
 */
public class ClearImgJob {

    @Resource
    private JedisPool jedisPool;
    public void clearImg(){
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        //sdiff(set diff): 获取两个集合的差集
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null){
            for (String picName : set) {
                //获取待删除的文件名
                int i = picName.lastIndexOf("/");
                String fileName = picName.substring(i+1);
                //删除七牛云服务器上的图片
                AliOssUtils.delete(fileName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("自定义任务执行，清理垃圾图片:" + fileName);
            }
        }
    }
}
