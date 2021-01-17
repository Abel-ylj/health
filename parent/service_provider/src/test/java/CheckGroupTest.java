import cn.ylj.mapper.CheckgroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/17  5:10 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring*.xml")
public class CheckGroupTest {

    @Resource
    private CheckgroupMapper checkgroupMapper;



    @Test
    public void insertRelTest(){
        checkgroupMapper.insertRel(15, new Integer[]{96,97});
    }
}