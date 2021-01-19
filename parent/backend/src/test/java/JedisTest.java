import cn.ylj.constant.RedisConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author : yanglujian
 * create at:  2021/1/19  12:00 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springmvc.xml")
public class JedisTest {

    @Resource
    private JedisPool jpool;

    @Test
    public void connTest(){
//        String set = jpool.getResource().set("test", "ylj");
//        System.out.println(set);

    }

    /**
     * 获取set数据
     */
    @Test
    public void setTest(){
        Set<String> lst = jpool.getResource().smembers(RedisConstant.SETMEAL_PIC_RESOURCES);
        for (String s : lst) {
            System.out.println(s);
        }
    }

}