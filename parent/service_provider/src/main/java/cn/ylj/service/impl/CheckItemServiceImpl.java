package cn.ylj.service.impl;

import cn.ylj.entity.Checkitem;
import cn.ylj.mapper.CheckitemMapper;
import cn.ylj.service.ICheckItemService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/15  10:29 下午
 */
@Service
public class CheckItemServiceImpl implements ICheckItemService {

    @Resource
    private CheckitemMapper checkitemMapper;

    public void add(Checkitem checkitem) {
        checkitemMapper.insert(checkitem);
    }
}