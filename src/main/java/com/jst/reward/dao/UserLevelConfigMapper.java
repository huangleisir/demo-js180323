package com.jst.reward.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jst.prodution.reward.serviceBean.UserLevelConfig;
import com.jst.reward.bean.UserLevelConfigExample;

@Mapper
public interface UserLevelConfigMapper {
    long countByExample(UserLevelConfigExample example);

    int deleteByExample(UserLevelConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserLevelConfig record);

    int insertSelective(UserLevelConfig record);

    List<UserLevelConfig> selectByExample(UserLevelConfigExample example);

    UserLevelConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserLevelConfig record, @Param("example") UserLevelConfigExample example);

    int updateByExample(@Param("record") UserLevelConfig record, @Param("example") UserLevelConfigExample example);

    int updateByPrimaryKeySelective(UserLevelConfig record);

    int updateByPrimaryKey(UserLevelConfig record);
}