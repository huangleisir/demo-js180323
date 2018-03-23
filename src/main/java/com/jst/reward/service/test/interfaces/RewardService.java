package com.jst.reward.service.test.interfaces;

import com.jst.prodution.reward.serviceBean.CommonConfiguration;
import com.jst.prodution.reward.serviceBean.SigninConfiguration;

public interface RewardService {
	/**
	 * 查询是否签到
	* @Title: queryIsSignInDone
	* @Description: 查询是否签到
	* @param @param userId
	* @param @return    参数
	* @return boolean  返回true是已经签到
	* @throws
	 */
	boolean queryIsSignInDone(String userId);

	boolean queryIsShare(String userId);

	CommonConfiguration queryShareAppScore();
	
	/**   
	 * <p>Title: SignIn</p>   
	 * <p>Description:签到流程 </p>   
	 * @param userId
	 * @return  
	 * 	0 代表成功，
	 * 	1代表今日已经签到过,
	 * 	2代表缓存和数据库里面都没有签到配置信息 ,
	 * 	3代表调用账户系统错误,
	 * 	4代表缓存和数据库里面都没有用户等级配置信息
	 */  
	int signIn(String userId);

	SigninConfiguration signInDays(int i);
}
