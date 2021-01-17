package cn.ylj.service.impl;

import cn.ylj.entity.Checkgroup;
import cn.ylj.mapper.CheckgroupMapper;
import cn.ylj.service.ICheckgroupService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/17  4:44 下午
 */
@Service
public class CheckgroupServiceImpl implements ICheckgroupService {

    @Resource
    private CheckgroupMapper checkgroupMapper;

    /**
     * 新增检查组: 事务
     * @param checkgroup
     * @param checkitemIds
     * @return
     */
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int add(Checkgroup checkgroup, Integer[] checkitemIds) {
        //插入后，还会执行SELECT LAST_INSERT_ID() 获取最后插入数据的id
        checkgroupMapper.insert(checkgroup);
        //插入关系
        checkgroupMapper.insertRel(checkgroup.getId(),checkitemIds);
        return checkgroup.getId();
    }
}