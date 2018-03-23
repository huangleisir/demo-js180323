package com.jst.reward.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jst.prodution.reward.serviceBean.CommonConfiguration;
import com.jst.reward.bean.CommonConfigurationExample;

@Mapper
public interface CommonConfigurationMapper {
    long countByExample(CommonConfigurationExample example);

    int deleteByExample(CommonConfigurationExample example);

    int deleteByPrimaryKey(String id);

    int insert(CommonConfiguration record);

    int insertSelective(CommonConfiguration record);

    List<CommonConfiguration> selectByExample(CommonConfigurationExample example);

    CommonConfiguration selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CommonConfiguration record, @Param("example") CommonConfigurationExample example);

    int updateByExample(@Param("record") CommonConfiguration record, @Param("example") CommonConfigurationExample example);

    int updateByPrimaryKeySelective(CommonConfiguration record);

    int updateByPrimaryKey(CommonConfiguration record);
}