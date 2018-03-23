package com.jst.reward.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jst.prodution.reward.serviceBean.BasicConfiguration;
import com.jst.reward.bean.BasicConfigurationExample;

//@Mapper
public interface BasicConfigurationMapper {
    long countByExample(BasicConfigurationExample example);

    int deleteByExample(BasicConfigurationExample example);

    int deleteByPrimaryKey(String id);

    int insert(BasicConfiguration record);

    int insertSelective(BasicConfiguration record);

    List<BasicConfiguration> selectByExample(BasicConfigurationExample example);

    BasicConfiguration selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BasicConfiguration record, @Param("example") BasicConfigurationExample example);

    int updateByExample(@Param("record") BasicConfiguration record, @Param("example") BasicConfigurationExample example);

    int updateByPrimaryKeySelective(BasicConfiguration record);

    int updateByPrimaryKey(BasicConfiguration record);
    
    
}