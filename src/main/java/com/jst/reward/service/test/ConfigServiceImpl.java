package com.jst.reward.service.test;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jst.prodution.demo.service.ConfigDuService;
import com.jst.prodution.reward.serviceBean.BasicConfiguration;
import com.jst.prodution.reward.serviceBean.CommonConfiguration;
import com.jst.prodution.reward.serviceBean.SigninConfiguration;
import com.jst.prodution.reward.serviceBean.UserLevelConfig;
import com.jst.prodution.util.EmptyUtil;
import com.jst.reward.bean.BasicConfigurationExample;
import com.jst.reward.bean.CommonConfigurationExample;
import com.jst.reward.bean.SigninConfigurationExample;
import com.jst.reward.bean.UserLevelConfigExample;
import com.jst.reward.dao.BasicConfigurationMapper;
import com.jst.reward.dao.CommonConfigurationMapper;
import com.jst.reward.dao.SigninConfigurationMapper;
import com.jst.reward.dao.UserLevelConfigMapper;
import com.jst.reward.service.test.interfaces.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {
	protected Logger  log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	BasicConfigurationMapper basicConfigurationMapper;
	
	@Autowired
	CommonConfigurationMapper commonConfigurationMapper;
	
	@Autowired
	SigninConfigurationMapper signinConfigurationMapper;
	
	@Autowired
	UserLevelConfigMapper userLevelConfigMapper;
	
	@Reference
	ConfigDuService  configDuService;
	 


	@Override
	public void TestRpc() {
		log.info("================into TestRpc======");
		configDuService.testRpc();
	}

	@Override
	public List<BasicConfiguration> getBasicConfigurationList() {
		log.info("================into getBasicConfigurationList======");
		BasicConfigurationExample example=new BasicConfigurationExample();
		example.isDistinct();
		List<BasicConfiguration> basicConfigurationList=basicConfigurationMapper.selectByExample(example);
		return basicConfigurationList;
//		return null;
	}

	@Override
	public List<CommonConfiguration> getCommonConfigurationList() {
		log.info("================into getCommonConfigurationList======");
		CommonConfigurationExample example=new CommonConfigurationExample();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1);
		List<CommonConfiguration> commonConConfigurationList=commonConfigurationMapper.selectByExample(example);
		return commonConConfigurationList;
//		return null;
	}

	@Override
	public List<SigninConfiguration> getSignInConfigurationList() {
		log.info("================into getSignInConfigurationList======");
		SigninConfigurationExample example=new SigninConfigurationExample();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1);
		List<SigninConfiguration> signinConfigurationList=signinConfigurationMapper.selectByExample(example);
		return signinConfigurationList;
//		return null;
	}

	@Override
	public List<UserLevelConfig> getUserLevelConfigList() {
		log.info("================into getUserLevelConfigList======");
		UserLevelConfigExample example=new UserLevelConfigExample();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1);
	
		List<UserLevelConfig> userLevelConfigList=userLevelConfigMapper.selectByExample(example);
		return userLevelConfigList;
//		return null;
	}

	@Override
	public UserLevelConfig getUserLevelConfigByLevelCode(String levelCode) {
		log.info("================into getUserLevelConfigByLevelCode======");
		UserLevelConfigExample example=new UserLevelConfigExample();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1).andLevelCodeEqualTo(levelCode);
		List<UserLevelConfig> userLevelConfigList=userLevelConfigMapper.selectByExample(example);
		if(EmptyUtil.isEmpty(userLevelConfigList)){
			return null;
		}else{
			return userLevelConfigList.get(0);
		}
	}

	@Override
	public SigninConfiguration getSignInConfigurationByDay(int day) {
		log.info("================into getSignInConfigurationByDay======");
		SigninConfigurationExample example=new SigninConfigurationExample();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1).andConsecutiveDaysEqualTo(day);
		List<SigninConfiguration> signinConfigurationList=signinConfigurationMapper.selectByExample(example);
		if(EmptyUtil.isEmpty(signinConfigurationList)){
			return null;
		}else{
			return signinConfigurationList.get(0);
		}
	}



}
