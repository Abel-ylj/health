import cn.ylj.entity.Checkitem;
import cn.ylj.mapper.CheckitemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : yanglujian
 * create at:  2021/1/15  10:34 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring*.xml")
public class CheckItemServiceTest {

    @Resource
    CheckitemMapper checkitemMapper;

    @Resource
    DataSource dataSource;

    @Test
    public void datasourseTest() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void add(){
        Checkitem ck = new Checkitem();
        ck.setName("测试检查项");
        ck.setCode("002");
        int r = checkitemMapper.insert(ck);
        System.out.println(r);
    }
}