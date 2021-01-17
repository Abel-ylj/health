package cn.ylj.service;

import cn.ylj.entity.Checkgroup;

public interface ICheckgroupService {

    int add(Checkgroup checkgroup, Integer[] checkitemIds);
}
