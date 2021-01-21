import cn.ylj.entity.User;
import cn.ylj.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 后台管理员服务测试
 * @author : yanglujian
 * create at:  2021/1/21  8:37 下午
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring*.xml")
public class UserTest {

    @Resource
    IUserService userService;

    /**
     * 获取后台管理员(user-role-permission测试)
     */
    @Test
    public void getUserTest(){
        User user = userService.getUserByName("admin");
    }
}