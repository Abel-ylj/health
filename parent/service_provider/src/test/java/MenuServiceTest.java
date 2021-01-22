import cn.ylj.entity.Menu;
import cn.ylj.entity.Role;
import cn.ylj.mapper.MenuMapper;
import cn.ylj.service.IMenuService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author : yanglujian
 * create at:  2021/1/22  2:18 下午
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring*.xml")
public class MenuServiceTest {

    @Resource
    MenuMapper menuMapper;
    @Resource
    IMenuService menuService;

    @Test
    public void findMenusByUsernameTest(){
        List<Menu> xiaoming = menuService.findMenusByUsername("xiaoming");
    }

    @Test
    public void findMenusByRolesTest(){
        Role r1 = new Role();
        Role r2 = new Role();
        r1.setId(1);
        r2.setId(2);
        Set<Menu> set = menuMapper.findMenusByRoles(Lists.newArrayList(r1, r2));
    }
}