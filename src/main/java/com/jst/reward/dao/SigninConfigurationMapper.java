package com.jst.reward.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jst.prodution.reward.serviceBean.SigninConfiguration;
import com.jst.reward.bean.SigninConfigurationExample;

@Mapper
public interface SigninConfigurationMapper {
    long countByExample(SigninConfigurationExample example);

    int deleteByExample(SigninConfigurationExample example);

    int deleteByPrimaryKey(String id);

    int insert(SigninConfiguration record);

    int insertSelective(SigninConfiguration record);

    List<SigninConfiguration> selectByExample(SigninConfigurationExample example);

    SigninConfiguration selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SigninConfiguration record, @Param("example") SigninConfigurationExample example);

    int updateByExample(@Param("record") SigninConfiguration record, @Param("example") SigninConfigurationExample example);

    int updateByPrimaryKeySelective(SigninConfiguration record);

    int updateByPrimaryKey(SigninConfiguration record);
}