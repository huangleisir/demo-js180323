package com.jst.reward.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jst.framework.common.util.EmptyUtils;
import com.jst.framework.redis.RedisUtil;
import com.jst.prodution.reward.serviceBean.BasicConfiguration;
import com.jst.prodution.reward.serviceBean.CommonConfiguration;
import com.jst.prodution.reward.serviceBean.SigninConfiguration;
import com.jst.prodution.reward.serviceBean.UserLevelConfig;
import com.jst.reward.common.constant.ConfigRedisConstant;
import com.jst.reward.service.test.interfaces.ConfigService;

@Component
//@Order(value=1)//有多个CommandLineRunner接口时可以指定执行顺序（小的先执行）
public class DataInitConfig implements CommandLineRunner {//CommandLineRunner表示在所有的bean以及applicationCOntenxt完成后的操作
	protected Logger  log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ConfigService configService;
	
	  @Override
	  public void run(String... args) throws Exception {
		  log.info("###############初始化信息开始###############");
		  log.info("###############执行数据初始化操作###############");
		  //获取会员等级配置信息
		  List<UserLevelConfig> userLevelConfigList=configService.getUserLevelConfigList(); 		  
		  //获取基础配置表信息
		  List<BasicConfiguration> basicConfigurationList=configService.getBasicConfigurationList(); 
		  //获取签到配置详情表信息
		  List<SigninConfiguration> signinConfigurationList=configService.getSignInConfigurationList(); 
		  //获取常规配置详情表信息
		  List<CommonConfiguration> commonConfigurationList=configService.getCommonConfigurationList(); 
		  log.info("###############将数据放入缓存###############");
		  //先放如集合
		  log.info("###############将数据的集合list放入缓存开始###############");
		  RedisUtil.setUserLevelConfigList(ConfigRedisConstant.USERLEVELCONFIG, userLevelConfigList);
		  RedisUtil.setBasicConfigurationList(ConfigRedisConstant.BASICCONFIG, basicConfigurationList);
		  RedisUtil.setCommonConfigurationList(ConfigRedisConstant.COMMONCONFIG, commonConfigurationList);
		  RedisUtil.setSigninConfigurationList(ConfigRedisConstant.SIGNINCONFIG, signinConfigurationList);
		  log.info("###############将数据的集合list放入缓存结束###############");
		  //在逐条放入
		  log.info("###############将数据逐条放入缓存开始###############");
		  //必须先判断list是否为空
		  //userLevelConfigList
		  if(EmptyUtils.isNotEmpty(userLevelConfigList)) {
			  log.info("###############初始化USERLEVELCONFIG信息开始###############");
			  userLevelConfigList.forEach(n ->
			  { 
				  log.info("###############会员等级配置项USERLEVELCONFIG的code={}###############",n.getLevelCode());
				  RedisUtil.setUserLevelConfig(n.getLevelCode()+ ConfigRedisConstant.USERLEVELCONFIG, n);
			  });	
			  log.info("###############初始化USERLEVELCONFIG信息结束###############");
		  }
		  //basicConfigurationList
		  if(EmptyUtils.isNotEmpty(basicConfigurationList)) {
			  log.info("###############初始化BASICCONFIG信息开始###############");
			  basicConfigurationList.forEach(n ->
			  { 
				  log.info("###############基础配置项BASICCONFIG的code={}###############",n.getCode());
				  RedisUtil.setBasicConfiguration(n.getCode()+ ConfigRedisConstant.BASICCONFIG, n);
			  });	
			  log.info("###############初始化BASICCONFIG信息结束###############");
		  }
		  //signinConfigurationList
		  if(EmptyUtils.isNotEmpty(signinConfigurationList)) {
			  log.info("###############初始化SIGNINCONFIG信息开始###############");
			  signinConfigurationList.forEach(n ->
			  { 
				  log.info("###############签到配置项SIGNINCONFIG的code={}###############",n.getCode());
				  log.info("###############签到配置项SIGNINCONFIG的consecutivedays={}###############",n.getConsecutiveDays());
				  RedisUtil.setSigninConfiguration(n.getCode()+n.getConsecutiveDays()+ ConfigRedisConstant.SIGNINCONFIG, n);
			  });	
			  log.info("###############初始化SIGNINCONFIG信息结束###############");
		  }
		  //commonConfigurationList
		  if(EmptyUtils.isNotEmpty(commonConfigurationList)) {
			  log.info("###############初始化COMMONCONFIG信息结束###############");
			  commonConfigurationList.forEach(n ->
			  { 
				  log.info("###############通用配置项COMMONCONFIG的code={}###############",n.getCode());
				  RedisUtil.setCommonConfiguration(n.getCode()+ ConfigRedisConstant.COMMONCONFIG, n);
			  });	
			  log.info("###############初始化COMMONCONFIG信息结束###############");
		  }
			  log.info("##############将数据逐条放入缓存结束###############");
			  log.info("###############将数据放入缓存结束###############");
			  log.info("#############初始化信息结束#############");
		  }
}
