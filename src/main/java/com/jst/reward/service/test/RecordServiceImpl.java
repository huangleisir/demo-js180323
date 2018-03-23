package com.jst.reward.service.test;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jst.prodution.reward.serviceBean.IncentiveRecord;
import com.jst.prodution.util.EmptyUtil;
import com.jst.reward.bean.IncentiveRecordExample;
import com.jst.reward.dao.IncentiveRecordMapper;
import com.jst.reward.service.test.interfaces.RecordService;

@Service
public class RecordServiceImpl implements RecordService {
	protected Logger  log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	IncentiveRecordMapper incentiveRecordMapper;
	

	@Override
	public int querySignInDays(String uid) {
		//1.先查询今天是否已经签到
		Date now=new Date();
		IncentiveRecordExample example=new IncentiveRecordExample();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1).andOperateTimeEqualTo(now)
		.andChannelEqualTo("appfront").andIncentiveModelEqualTo("MR").andUidEqualTo(uid);
		List<IncentiveRecord> nowlist=incentiveRecordMapper.selectByExample(example);
		if(EmptyUtil.isNotEmpty(nowlist)) {
			//今天已经签到，得到签到总数
			int signInDays=nowlist.get(0).getSignInDays();
			//得到连续签到次数
			return signInDays%7;
		}
		//2.今天没有签到 查询昨天是连续第几天
		//得到昨天日期
		Calendar cal=Calendar.getInstance();
		//System.out.println(Calendar.DATE);//5
		cal.add(Calendar.DATE,-1);
		Date time=cal.getTime();
		//查询记录表，查询条件是操作日期为昨天，uid,操作类型为签到
		example.clear();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1).andOperateTimeEqualTo(time)
		.andChannelEqualTo("appfront").andIncentiveModelEqualTo("MR").andUidEqualTo(uid);
		List<IncentiveRecord> list=incentiveRecordMapper.selectByExample(example);
		if(EmptyUtil.isEmpty(list)) {
			//昨天没有签到
			return 0;
		}
		IncentiveRecord incentiveRecord=list.get(0);
		//得到昨天签到总数
		int signInDays=incentiveRecord.getSignInDays();
		//得到连续签到次数
		return signInDays%7;
	}


	@Override
	public PageInfo<IncentiveRecord> getPageRecord(String type,int pageNo,int pageSize,String userId) {
		
		IncentiveRecordExample incentiveRecordExample=new IncentiveRecordExample();
		PageInfo<IncentiveRecord> page = null;
		log.info("查询type={},pageNo={},pageSize={}",type,pageNo,pageSize);
		if(type == null || "".equals(type)){
			log.info("查询全部");
			incentiveRecordExample.isDistinct();
			incentiveRecordExample.createCriteria().andEnableEqualTo(1).andUidEqualTo(userId)
			.andChannelEqualTo("appfront").andIncentiveModelEqualTo("MR");
			
			long beginTime = System.currentTimeMillis();
	        PageHelper.startPage(pageNo, pageSize);
	        page = new PageInfo(incentiveRecordMapper.selectByExample(incentiveRecordExample));
	        log.info("=================结束请求执行数据库，【耗时={}】，【返回数据={}】", (System.currentTimeMillis() - beginTime), JSON
	                .toJSONString(page));
		}else if("add".equals(type)){
			log.info("查询加分");
			incentiveRecordExample.clear();
			incentiveRecordExample.isDistinct();
			incentiveRecordExample.createCriteria().andEnableEqualTo(1).andScoreTypeEqualTo(1).andUidEqualTo(userId)
			.andChannelEqualTo("appfront").andIncentiveModelEqualTo("MR");
			
			long beginTime = System.currentTimeMillis();
	        PageHelper.startPage(pageNo, pageSize);
	        page = new PageInfo(incentiveRecordMapper.selectByExample(incentiveRecordExample));
	        log.info("=================结束请求执行数据库，【耗时={}】，【返回数据={}】", (System.currentTimeMillis() - beginTime), JSON
	                .toJSONString(page));
		}else{
			log.info("查询减分");
			incentiveRecordExample.clear();
			incentiveRecordExample.isDistinct();
			incentiveRecordExample.createCriteria().andEnableEqualTo(1).andScoreTypeEqualTo(0).andUidEqualTo(userId)
			.andChannelEqualTo("appfront").andIncentiveModelEqualTo("MR");
			
			long beginTime = System.currentTimeMillis();
	        PageHelper.startPage(pageNo, pageSize);
	        page = new PageInfo(incentiveRecordMapper.selectByExample(incentiveRecordExample));
	        log.info("=================结束请求执行数据库，【耗时={}】，【返回数据={}】", (System.currentTimeMillis() - beginTime), JSON
	                .toJSONString(page));
		}
		
		return page;
		
	}
	 





}
