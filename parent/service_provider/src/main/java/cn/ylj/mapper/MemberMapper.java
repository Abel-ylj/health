package cn.ylj.mapper;

import cn.ylj.entity.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    /**
     * Mobile H5:  查讯该身份证的用户是否已经存在
     *
     * @param idcard
     * @return
     */
    Member findCntByIdCard(@Param("idcard") String idcard);
}