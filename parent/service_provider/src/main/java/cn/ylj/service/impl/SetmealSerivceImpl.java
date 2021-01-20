package cn.ylj.service.impl;

import cn.ylj.constant.MessageConstant;
import cn.ylj.constant.RedisConstant;
import cn.ylj.entity.Checkgroup;
import cn.ylj.entity.Setmeal;
import cn.ylj.mapper.CheckgroupMapper;
import cn.ylj.mapper.SetmealMapper;
import cn.ylj.model.QueryPageBean;
import cn.ylj.service.ISetMealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/18  1:25 下午
 */
@Service(interfaceClass = ISetMealService.class)
public class SetmealSerivceImpl implements ISetMealService {

    @Resource
    SetmealMapper setmealMapper;
    @Resource
    private JedisPool jedisPool;
    @Resource
    private CheckgroupMapper checkgroupMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void add(Setmeal setmeal, Integer[] ids) {
        setmealMapper.insert(setmeal);
        //插入关系
        setmealMapper.insertRel(setmeal.getId(), ids);
        //将该套餐附带的图片 添加到redis已被引用图片记录的set中

        //TODO 思考，redis和transcation的关系
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    public void delete(Integer id) {
        //查询是否已被预约，若已经被预约，则无法修改

    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void update(Setmeal setmeal, Integer[] ids) {

    }

    public Page<Setmeal> findPage(QueryPageBean pb) {
        PageHelper.startPage(pb.getCurrentPage(), pb.getPageSize());
        Page<Setmeal> page = setmealMapper.findPage(pb.getQueryString());
        return page;
    }

    public Setmeal findOneById(Integer id) {
        return setmealMapper.selectByPrimaryKey(id);
    }

    public List<Integer> findRelBySetmealId(Integer id){
       Integer[] ids = setmealMapper.findRelBySetmealId(id);
       return Arrays.asList(ids);
    }

    @Override
    public List<Setmeal> getSetmealList() {
        return setmealMapper.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealMapper.findDetailById(id);
        if (setmeal.getCheckgroupList() != null && setmeal.getCheckgroupList().size() > 0){
            //将检查组对应的检查项 写入列表
            for (Checkgroup checkgroup : setmeal.getCheckgroupList()) {
                Checkgroup cg = checkgroupMapper.findByIdWithRel(checkgroup.getId());
                checkgroup.setCheckitemList(cg.getCheckitemList());
            }
        }
        return setmeal;
    }
}