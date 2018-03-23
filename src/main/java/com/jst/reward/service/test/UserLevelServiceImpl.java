package com.jst.reward.service.test;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jst.prodution.reward.serviceBean.UserLevel;
import com.jst.prodution.util.EmptyUtil;
import com.jst.reward.bean.UserLevelExample;
import com.jst.reward.dao.UserLevelMapper;
import com.jst.reward.service.test.interfaces.UserLevelService;

@Service
public class UserLevelServiceImpl implements UserLevelService {
	protected Logger  log = LoggerFactory.getLogger(getClass());

	@Autowired
	UserLevelMapper userLevelMapper;

	@Override
	public UserLevel queryUserLevel(String userId) {
		UserLevelExample example=new UserLevelExample();
		example.createCriteria().andUserIdEqualTo(userId).andEnableEqualTo(1);
		List<UserLevel> list=userLevelMapper.selectByExample(example);
		if(EmptyUtil.isEmpty(list)) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	
	

}
