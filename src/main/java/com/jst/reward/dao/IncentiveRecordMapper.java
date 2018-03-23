package com.jst.reward.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jst.prodution.reward.serviceBean.IncentiveRecord;
import com.jst.reward.bean.IncentiveRecordExample;

@Mapper
public interface IncentiveRecordMapper {
    long countByExample(IncentiveRecordExample example);

    int deleteByExample(IncentiveRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(IncentiveRecord record);

    int insertSelective(IncentiveRecord record);

    List<IncentiveRecord> selectByExample(IncentiveRecordExample example);

    IncentiveRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IncentiveRecord record, @Param("example") IncentiveRecordExample example);

    int updateByExample(@Param("record") IncentiveRecord record, @Param("example") IncentiveRecordExample example);

    int updateByPrimaryKeySelective(IncentiveRecord record);

    int updateByPrimaryKey(IncentiveRecord record);
}