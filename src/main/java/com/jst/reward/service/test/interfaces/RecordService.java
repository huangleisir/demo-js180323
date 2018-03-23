package com.jst.reward.service.test.interfaces;

import com.github.pagehelper.PageInfo;
import com.jst.prodution.reward.serviceBean.IncentiveRecord;

public interface RecordService {

	//查询今天是连续第几天
	public int querySignInDays(String uid);
	
	
	//分页查询记录
	public PageInfo<IncentiveRecord> getPageRecord(String type,int pageNo,int pageSize, String userId);
}
