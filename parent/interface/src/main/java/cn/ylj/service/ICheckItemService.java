package cn.ylj.service;

import cn.ylj.entity.Checkitem;
import cn.ylj.model.PageResult;
import cn.ylj.model.QueryPageBean;
import cn.ylj.model.Result;

public interface ICheckItemService {

    void add(Checkitem checkitem);

    Result deleteById(Integer id);

    PageResult pageQuery(QueryPageBean pb);

    Result findById(Integer itemId);
}
