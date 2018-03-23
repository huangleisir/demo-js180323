package com.jst.reward.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jst.prodution.reward.serviceBean.UserUpgradeRecord;
import com.jst.reward.bean.UserUpgradeRecordExample;

@Mapper
public interface UserUpgradeRecordMapper {
    long countByExample(UserUpgradeRecordExample example);

    int deleteByExample(UserUpgradeRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserUpgradeRecord record);

    int insertSelective(UserUpgradeRecord record);

    List<UserUpgradeRecord> selectByExample(UserUpgradeRecordExample example);

    UserUpgradeRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserUpgradeRecord record, @Param("example") UserUpgradeRecordExample example);

    int updateByExample(@Param("record") UserUpgradeRecord record, @Param("example") UserUpgradeRecordExample example);

    int updateByPrimaryKeySelective(UserUpgradeRecord record);

    int updateByPrimaryKey(UserUpgradeRecord record);
}