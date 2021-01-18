package cn.ylj.service.impl;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Checkgroup;
import cn.ylj.mapper.CheckgroupMapper;
import cn.ylj.model.QueryPageBean;
import cn.ylj.service.ICheckgroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/17  4:44 下午
 */
//让dubbo正常发布事务包装的服务
@Service(interfaceClass = ICheckgroupService.class)
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
        if (checkgroup.getId() != null && checkitemIds.length > 0){
            checkgroupMapper.insertRel(checkgroup.getId(),checkitemIds);
        }
        return checkgroup.getId();
    }

    public PageInfo<Checkgroup> findPage(QueryPageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());
        List list = checkgroupMapper.findPage(pageBean.getQueryString());
        return new PageInfo<Checkgroup>(list);
    }

    public void deleteById(Integer id) {
        //查询依赖
        int cnt = checkgroupMapper.selectSetMealCntByCheckGroup(id);
        if (cnt > 0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKGROUP_MSG);
        }
        checkgroupMapper.deleteByPrimaryKey(id);
    }

    public Checkgroup findById(Integer id) {
        return checkgroupMapper.findByIdWithRel(id);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void update(Checkgroup checkgroup, Integer[] ids) {
        checkgroupMapper.updateByPrimaryKeySelective(checkgroup);
        //清空原先的关系，更新为最新的关系
        checkgroupMapper.deleteRelByCheckgroupId(checkgroup.getId());
        checkgroupMapper.insertRel(checkgroup.getId(), ids);
    }
}