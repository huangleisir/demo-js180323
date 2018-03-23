package com.jst.reward.service.test;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jst.framework.redis.RedisUtil;
import com.jst.prodution.account.dubbo.service.AcctUpdMemberPointsDuService;
import com.jst.prodution.account.serviceBean.AcctUpdMemberPointsBean;
import com.jst.prodution.base.bean.BaseBean;
import com.jst.prodution.reward.serviceBean.CommonConfiguration;
import com.jst.prodution.reward.serviceBean.IncentiveRecord;
import com.jst.prodution.reward.serviceBean.SigninConfiguration;
import com.jst.prodution.reward.serviceBean.UserLevel;
import com.jst.prodution.reward.serviceBean.UserLevelConfig;
import com.jst.prodution.reward.serviceBean.UserUpgradeRecord;
import com.jst.prodution.util.EmptyUtil;
import com.jst.prodution.util.SnowflakeIdUtil;
import com.jst.reward.bean.CommonConfigurationExample;
import com.jst.reward.bean.IncentiveRecordExample;
import com.jst.reward.bean.SigninConfigurationExample;
import com.jst.reward.common.constant.ConfigRedisConstant;
import com.jst.reward.dao.CommonConfigurationMapper;
import com.jst.reward.dao.IncentiveRecordMapper;
import com.jst.reward.dao.SigninConfigurationMapper;
import com.jst.reward.dao.UserLevelMapper;
import com.jst.reward.dao.UserUpgradeRecordMapper;
import com.jst.reward.service.test.interfaces.ConfigService;
import com.jst.reward.service.test.interfaces.RecordService;
import com.jst.reward.service.test.interfaces.RewardService;
import com.jst.reward.service.test.interfaces.UserLevelService;

@Service
public class RewardServiceImpl implements RewardService {
	protected Logger  log = LoggerFactory.getLogger(getClass());

	@Autowired
	UserLevelService userLevelService;
	@Autowired
	RecordService recordService;
	@Autowired
	ConfigService configService;
	@Autowired
	IncentiveRecordMapper incentiveRecordMapper;
	@Autowired
	UserLevelMapper userLevelMapper;
	@Autowired
	UserUpgradeRecordMapper userUpgradeRecordMapper;
	@Reference
	AcctUpdMemberPointsDuService acctUpdMemberPointsDuService;
	
	@Autowired
	CommonConfigurationMapper configurationMapper;
	
	@Autowired
	SigninConfigurationMapper   signinConfigurationMapper;


	@Override
	public boolean queryIsSignInDone(String userId) {
		IncentiveRecordExample incentiveRecordExample=new IncentiveRecordExample();
		incentiveRecordExample.createCriteria().andEnableEqualTo(1).andUidEqualTo(userId)
		.andChannelEqualTo("appfront").andOperateTimeEqualTo(new Date()).andIncentiveModelEqualTo("MR");
		List<IncentiveRecord> list=incentiveRecordMapper.selectByExample(incentiveRecordExample);
		if(EmptyUtil.isEmpty(list)){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean queryIsShare(String userId) {
		IncentiveRecordExample incentiveRecordExample=new IncentiveRecordExample();
		incentiveRecordExample.createCriteria().andEnableEqualTo(1).andUidEqualTo(userId)
		.andChannelEqualTo("appfront").andOperateTimeEqualTo(new Date()).andIncentiveModelEqualTo("MR");
		List<IncentiveRecord> list=incentiveRecordMapper.selectByExample(incentiveRecordExample);
		if(EmptyUtil.isEmpty(list)){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public CommonConfiguration queryShareAppScore() {
		CommonConfigurationExample configurationExample = new CommonConfigurationExample();
		configurationExample.createCriteria().andCodeEqualTo("share").andEnableEqualTo(1);
		CommonConfiguration commonConfiguration = (CommonConfiguration) configurationMapper.selectByExample(configurationExample);
		
		return commonConfiguration;
	}

	


	/**   
	 * <p>Title: SignIn</p>   
	 * <p>Description:签到流程 </p>   
	 * @param userId
	 * @return   0 代表成功，1代表今日已经签到过，2代表缓存和数据库里面都没有签到配置信息 3代表调用账户系统错误
	 * 			4代表缓存和数据库里面都没有用户等级配置信息
	 * @see 
	 */  
	@Transactional
	@Override
	public int signIn(String userId) {
		//1.先查询今天是否已经签到
		Date now=new Date();
		IncentiveRecordExample example=new IncentiveRecordExample();
		example.isDistinct();
		example.createCriteria().andEnableEqualTo(1).andOperateTimeEqualTo(now)
		.andChannelEqualTo("appfront").andIncentiveModelEqualTo("MR").andUidEqualTo(userId);
		List<IncentiveRecord> nowlist=incentiveRecordMapper.selectByExample(example);
		//今天已经签到
		if(EmptyUtil.isNotEmpty(nowlist)) {
			log.info("用户userId是{},今日已经签到",userId);
			return 1;
		}
		//2.如果今日没有签到，判断今天是连续第几天签到，通过配置表(先读缓存，缓存没有则读库)计算出需要加的分数
		int days=recordService.querySignInDays(userId);
		log.info("用户userId是{},连续签到天数是{}",userId,days);
		SigninConfiguration signinConfiguration=RedisUtil.getSigninConfiguration("singIn"+days+ ConfigRedisConstant.SIGNINCONFIG);
		if(signinConfiguration == null) {
			signinConfiguration=configService.getSignInConfigurationByDay(days);
			if(signinConfiguration == null) {
				log.info("缓存和数据库里面都没有签到配置信息");
				//数据库里面也没有，报错
				return 2;
			}else {
				//写入缓存
				RedisUtil.setSigninConfiguration("singIn"+days+ ConfigRedisConstant.SIGNINCONFIG, signinConfiguration);
			}
		}
		//需要加的分数
		int score=signinConfiguration.getScore();
		//3.调用账户系统，增加好豆分数余额
		AcctUpdMemberPointsBean acctUpdMemberPointsBean = new AcctUpdMemberPointsBean();
//		acctUpdMemberPointsBean.setAcctId("");
		acctUpdMemberPointsBean.setUserId(userId);
		acctUpdMemberPointsBean.setPoints(new Long(score));
		acctUpdMemberPointsBean.setOperType(0);
		Date sendTime=new Date();
		acctUpdMemberPointsBean=(AcctUpdMemberPointsBean) acctUpdMemberPointsDuService.action(acctUpdMemberPointsBean);
		log.info("调用账户系统返回code："+acctUpdMemberPointsBean.getResCode());
		//调用账户系统错误
		if(!BaseBean.RES_CODE_SUCCESS.equals(acctUpdMemberPointsBean.getResCode())) {
			return 3;
		}
		//4.插入获取激励分数记录表
		IncentiveRecord record=new IncentiveRecord();
		record.setChannel("appfront");
		record.setCode("signIn");
		record.setCreateTime(new Date());
		record.setEnable(1);
		record.setId(Long.toString(SnowflakeIdUtil.generate(16)));
		record.setUid(userId);
		record.setOperateTime(new Date());
		record.setIncentiveModel("MR");
		record.setIncentiveName("签到");
		record.setScore(score);
		record.setSignInDays(days+1);
		record.setScoreType(1);
		record.setSendTime(sendTime);
		incentiveRecordMapper.insert(record);
		
		//5.判断等级是否升级
		//得到现在用户的积分总值（t_user_level表）
		UserLevel userLevel=userLevelService.queryUserLevel(userId);
		//说明是第一次插入等级表
		if(userLevel == null) {
			//插入一条新的用户等级记录
			 userLevel=new UserLevel();
			 userLevel.setEnable(1);
			 userLevel.setCreateTime(new Date());
			 userLevel.setIsPopUp(0);
			 userLevel.setId(Long.toString(SnowflakeIdUtil.generate(16)));
			 userLevel.setUserId(userId);
			 userLevel.setUserAccountMr(score);
			 userLevel.setUserLevel(0);
			 userLevelMapper.insert(userLevel);
		}else {
			//修改
			userLevel.setLastUpdateTime(new Date());
			int level=userLevel.getUserLevel();
			//判断等级是否为最高级7级,为最高级则只修改总量
			if(level>=7) {
				int userAccountMr=userLevel.getUserAccountMr();
				userLevel.setUserAccountMr(userAccountMr+score);
				userLevel.setLastUpdateTime(new Date());
				userLevelMapper.updateByPrimaryKey(userLevel);
			}else {
				//插入前的好豆总数
				int userAccountMr=userLevel.getUserAccountMr();
				//查询等级积分配置，满足升级条件则升级(userAccountMr+score大于下一级的值)
				int nextLevel=level+1;
				String levelCode="level"+level;
				UserLevelConfig userLevelConfig=RedisUtil.getUserLevelConfig(levelCode+ ConfigRedisConstant.USERLEVELCONFIG);
				if(userLevelConfig == null) {
					userLevelConfig=configService.getUserLevelConfigByLevelCode(levelCode);
					if(userLevelConfig == null) {
						log.info("缓存和数据库里面都没有UserLevelConfig配置信息");
						//数据库里面也没有，报错
						return 4;
					}else {
						//写入缓存
						RedisUtil.setUserLevelConfig(levelCode+ ConfigRedisConstant.USERLEVELCONFIG, userLevelConfig);
					}
				}
				//下一级的分数
				int nextLevelAmount=userLevelConfig.getLevelAmount();
				if(userAccountMr+score >= nextLevelAmount) {
					//升级	
					userLevel.setIsPopUp(1);
					userLevel.setUserLevel(nextLevel);
					userLevel.setUserAccountMr(userAccountMr+score);
					userLevel.setLastUpdateTime(new Date());
					userLevelMapper.updateByPrimaryKey(userLevel);
					//插入升级记录表
					UserUpgradeRecord userUpgradeRecord =new UserUpgradeRecord();
					userUpgradeRecord.setId(Long.toString(SnowflakeIdUtil.generate(16)));
					userUpgradeRecord.setUserId(userId);
					userUpgradeRecord.setEnable(1);
					userUpgradeRecord.setUserAccountMr(userAccountMr+score);
					userUpgradeRecord.setUserLevel(nextLevel);
					userUpgradeRecord.setCreateTime(new Date());
					userUpgradeRecordMapper.insert(userUpgradeRecord);
				}else {
					//不升级
					userLevel.setIsPopUp(0);
					userLevel.setUserLevel(level);
					userLevel.setUserAccountMr(userAccountMr+score);
					userLevel.setLastUpdateTime(new Date());
					userLevelMapper.updateByPrimaryKey(userLevel);
				}
			}
		}
		return 0;
	}

	@Override
	public SigninConfiguration signInDays(int i) {
		SigninConfigurationExample signinConfigurationExample = new SigninConfigurationExample();
		signinConfigurationExample.createCriteria().andConsecutiveDaysEqualTo(i).andEnableEqualTo(1);
		List<SigninConfiguration> list = signinConfigurationMapper.selectByExample(signinConfigurationExample);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	

}
