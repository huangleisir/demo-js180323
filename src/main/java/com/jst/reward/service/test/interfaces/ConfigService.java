package com.jst.reward.service.test.interfaces;

import java.util.List;

import com.jst.prodution.reward.serviceBean.BasicConfiguration;
import com.jst.prodution.reward.serviceBean.CommonConfiguration;
import com.jst.prodution.reward.serviceBean.SigninConfiguration;
import com.jst.prodution.reward.serviceBean.UserLevelConfig;

public interface ConfigService {

	
	public List<BasicConfiguration> getBasicConfigurationList();
	
	public List<CommonConfiguration> getCommonConfigurationList();
	
	public List<SigninConfiguration> getSignInConfigurationList();
	
	public SigninConfiguration getSignInConfigurationByDay(int day);
	
	public List<UserLevelConfig> getUserLevelConfigList();
	
	public UserLevelConfig getUserLevelConfigByLevelCode(String levelCode);
	
	void TestRpc();
}
