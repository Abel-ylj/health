package cn.ylj.service.impl;

import cn.ylj.entity.Member;
import cn.ylj.mapper.MemberMapper;
import cn.ylj.service.IMemberService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/20  10:17 下午
 */
@Service
public class MemberServiceImpl implements IMemberService {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public Member findById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }
}