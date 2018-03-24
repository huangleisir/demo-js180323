/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.reward.dubbo.service.impl.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.jst.framework.common.util.EmptyUtils;
import com.jst.framework.redis.RedisUtil;
import com.jst.prodution.base.bean.BaseBean;
import com.jst.prodution.reward.common.dto.req.RQ0001ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0002ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0003ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0004ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0005ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0006ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0011ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0012ReqDto;
import com.jst.prodution.reward.common.dto.req.RQ0013ReqDto;
import com.jst.prodution.reward.common.dto.req.RT0001ReqDto;
import com.jst.prodution.reward.common.dto.req.RT0002ReqDto;
import com.jst.prodution.reward.common.dto.req.RT0003ReqDto;
import com.jst.prodution.reward.common.dto.resp.PageResponseDTO;
import com.jst.prodution.reward.common.dto.resp.RQ0002RespDto;
import com.jst.prodution.reward.common.dto.resp.RQ0003RespDto;
import com.jst.prodution.reward.common.dto.resp.RQ0004RespDto;
import com.jst.prodution.reward.common.dto.resp.RQ0005RespDto;
import com.jst.prodution.reward.common.dto.resp.RQ0006RespDto;
import com.jst.prodution.reward.common.dto.resp.RQ0011RespDto;
import com.jst.prodution.reward.common.dto.resp.RQ0012RespDto;
import com.jst.prodution.reward.common.dto.resp.RQ0013RespDto;
import com.jst.prodution.reward.common.dto.resp.RT0001RespDto;
import com.jst.prodution.reward.common.dto.resp.RT0002RespDto;
import com.jst.prodution.reward.common.dto.resp.RT0003RespDto;
import com.jst.prodution.reward.common.enums.RespEnum;
import com.jst.prodution.reward.common.enums.TransEnum;
import com.jst.prodution.reward.service.RewardDuService;
import com.jst.prodution.reward.serviceBean.CommonConfiguration;
import com.jst.prodution.reward.serviceBean.IncentiveRecord;
import com.jst.prodution.reward.serviceBean.SigninConfiguration;
import com.jst.prodution.reward.serviceBean.UserLevel;
import com.jst.prodution.reward.serviceBean.UserLevelConfig;
import com.jst.prodution.util.EmptyUtil;
import com.jst.reward.common.constant.ConfigRedisConstant;
import com.jst.reward.common.util.CheckDate;
import com.jst.reward.service.test.interfaces.ConfigService;
import com.jst.reward.service.test.interfaces.RecordService;
import com.jst.reward.service.test.interfaces.RewardService;
import com.jst.reward.service.test.interfaces.UserLevelService;

@Service
public class RewardDuServiceImpl implements RewardDuService {
	protected Logger  log = LoggerFactory.getLogger(getClass());
	@Autowired
	RewardService rewardService ;
	
	@Autowired
	RecordService recordService ;
	
	@Autowired
	ConfigService configService ;
	
	@Autowired
	UserLevelService userLevelService ;
	
	@Override
	public BaseBean action(BaseBean input) {
		return null;
		//return testService.action(input);
	}




	@Override
	public PageResponseDTO<IncentiveRecord> selectRewardRecord(RQ0001ReqDto RQ0001ReqDto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RQ0002RespDto queryIsSignInDone(RQ0002ReqDto dto) {
		log.info("入参{}"+dto);
		RQ0002RespDto rq0002RespDto = new RQ0002RespDto();
		try {
			if(!CheckDate.check(dto, rq0002RespDto, TransEnum.TRANS_RQ0002.getCode())){
				return rq0002RespDto;
			}
			String userId=dto.getUserId();
			log.info("----userId={}----",userId);
			if(userId==null || "".equals(userId)){
				rq0002RespDto.setRespCode(RespEnum.RESP_CODE_100001.getCode());
				rq0002RespDto.setRespMsg(RespEnum.RESP_CODE_100001.getMsg());
				return rq0002RespDto;
			}
			//查询今日是否已经签到  
			if(rewardService.queryIsSignInDone(userId)){
				rq0002RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
				rq0002RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
				rq0002RespDto.setState(1);
				return rq0002RespDto;
			}else{
				rq0002RespDto.setRespCode(RespEnum.RESP_CODE_100017.getCode());
				rq0002RespDto.setRespMsg(RespEnum.RESP_CODE_100017.getMsg());
				rq0002RespDto.setState(0);
				return rq0002RespDto;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rq0002RespDto.setRespCode(RespEnum.RESP_CODE_000001.getCode());
			rq0002RespDto.setRespMsg(RespEnum.RESP_CODE_000001.getMsg());
			rq0002RespDto.setState(0);
			return rq0002RespDto;
		}
	}

	@Override
	public RQ0003RespDto queryIsShare(RQ0003ReqDto dto) {
		RQ0003RespDto rq0003RespDto = new RQ0003RespDto();
		try {
			if(!CheckDate.check(dto, rq0003RespDto, TransEnum.TRANS_RQ0003.getCode())){
				return rq0003RespDto;
			}
			String userId=dto.getUserId();
			if(userId==null || "".equals(userId)){
				rq0003RespDto.setRespCode(RespEnum.RESP_CODE_100001.getCode());
				rq0003RespDto.setRespMsg(RespEnum.RESP_CODE_100001.getMsg());
				return rq0003RespDto;
			}
			//查询今日是否已经分享
			log.info("调用今日是否已经分享rewardService.queryIsShare,userId={}",userId);
			if(rewardService.queryIsShare(userId)){
				rq0003RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
				rq0003RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
				rq0003RespDto.setState(1);
				return rq0003RespDto;
			}else{
				rq0003RespDto.setRespCode(RespEnum.RESP_CODE_100017.getCode());
				rq0003RespDto.setRespMsg(RespEnum.RESP_CODE_100017.getMsg());
				rq0003RespDto.setState(0);
				return rq0003RespDto;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rq0003RespDto.setRespCode(RespEnum.RESP_CODE_000001.getCode());
			rq0003RespDto.setRespMsg(RespEnum.RESP_CODE_000001.getMsg());
			rq0003RespDto.setState(0);
			return rq0003RespDto;
		}
	}

	public RQ0011RespDto queryShareAppScore(RQ0011ReqDto dto) {
		RQ0011RespDto rq0011RespDto = new RQ0011RespDto();
		
		if(!CheckDate.check(dto, rq0011RespDto, TransEnum.TRANS_RQ0011.getCode())){
			return rq0011RespDto;
		}
		
		CommonConfiguration  commonConfiguration =RedisUtil.getCommonConfiguration("share"+ ConfigRedisConstant.COMMONCONFIG);
		log.info("从缓存中取出,commonConfiguration={}",commonConfiguration);
		//缓存里面没有 则查数据库
		if(EmptyUtil.isEmpty(commonConfiguration)){
			 commonConfiguration = rewardService.queryShareAppScore();
			 log.info("从数据库取出,commonConfiguration={}",commonConfiguration);
			 //将数据放入缓存
			 RedisUtil.setCommonConfiguration("share"+ ConfigRedisConstant.COMMONCONFIG, commonConfiguration);
		}
		//缓存里面没有 数据库里面也没有
		if(commonConfiguration == null) {
			rq0011RespDto.setRespCode(RespEnum.RESP_CODE_900002.getCode());
			rq0011RespDto.setRespMsg(RespEnum.RESP_CODE_900002.getMsg());
			return rq0011RespDto;
		}
		rq0011RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
		rq0011RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
		rq0011RespDto.setReqSn(dto.getReqSn());
		rq0011RespDto.setCommonConfiguration(commonConfiguration);
		return rq0011RespDto;
	}

	@Override
	public RQ0012RespDto querySignInScore(RQ0012ReqDto dto) {
		RQ0012RespDto rq0012RespDto = new RQ0012RespDto();
		if(!CheckDate.check(dto, rq0012RespDto, TransEnum.TRANS_RQ0011.getCode())){
			return rq0012RespDto;
		}
		//判断userId是否为空
		String userId=dto.getUserId();
		if(userId==null || "".equals(userId)){
			rq0012RespDto.setRespCode(RespEnum.RESP_CODE_100001.getCode());
			rq0012RespDto.setRespMsg(RespEnum.RESP_CODE_100001.getMsg());
			return rq0012RespDto;
		}
		//查询今日是否已经签到  返回1则今日已经签到
		log.info("查询今日是否已经签到  返回1则今日已经签到调用rewardService.queryIsSignInDone(userId)返回值");
		if(rewardService.queryIsSignInDone(userId)){
			rq0012RespDto.setState(1);
			int signInDays = recordService.querySignInDays(userId);
			//返回签到的对象
			int nextDays=signInDays+1;
			SigninConfiguration signInConfiduration =RedisUtil.getSigninConfiguration("signIn"+nextDays+ ConfigRedisConstant.SIGNINCONFIG);
			if(signInConfiduration == null){
				signInConfiduration = rewardService.signInDays(nextDays);
				log.info("查询今日是否已经签到显示的分数,入参{},出参{}",nextDays,signInConfiduration);
				//数据库里面也没有
				if(signInConfiduration == null){
					rq0012RespDto.setRespCode(RespEnum.RESP_CODE_900002.getCode());
					rq0012RespDto.setRespMsg(RespEnum.RESP_CODE_900002.getMsg());
					rq0012RespDto.setReqSn(dto.getReqSn());
					return rq0012RespDto;
				}else{
					RedisUtil.setSigninConfiguration("signIn"+nextDays+ ConfigRedisConstant.SIGNINCONFIG, signInConfiduration);
				}
			}
			
			rq0012RespDto.setScore(signInConfiduration.getScore());
			rq0012RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
			rq0012RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
			rq0012RespDto.setReqSn(dto.getReqSn());
			return rq0012RespDto;
		}//今日未签到
		else{
			rq0012RespDto.setState(0);
			int signInDays = recordService.querySignInDays(userId);
			//返回签到的对象
			int nextDays=signInDays+1;
			SigninConfiguration signInConfiduration =RedisUtil.getSigninConfiguration("signIn"+nextDays+ ConfigRedisConstant.SIGNINCONFIG);
			if(signInConfiduration == null){
				signInConfiduration = rewardService.signInDays(nextDays);
				//数据库里面也没有
				if(signInConfiduration == null){
					rq0012RespDto.setRespCode(RespEnum.RESP_CODE_900002.getCode());
					rq0012RespDto.setRespMsg(RespEnum.RESP_CODE_900002.getMsg());
					rq0012RespDto.setReqSn(dto.getReqSn());
					return rq0012RespDto;
				}else{
					RedisUtil.setSigninConfiguration("signIn"+nextDays+ ConfigRedisConstant.SIGNINCONFIG, signInConfiduration);
				}
			}
			
			rq0012RespDto.setScore(signInConfiduration.getScore());
			rq0012RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
			rq0012RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
			rq0012RespDto.setReqSn(dto.getReqSn());
			return rq0012RespDto;
		}

	}


	@Override
	public RQ0005RespDto queryUserLevelConfigs(RQ0005ReqDto RQ0005ReqDto) {
		RQ0005RespDto rq0005RespDto = new RQ0005RespDto();
		try {
			if(!CheckDate.check(RQ0005ReqDto, rq0005RespDto, TransEnum.TRANS_RQ0005.getCode())) {
				return rq0005RespDto;
			}
			//先从缓存里面去查，如果缓存没有则去查库
			List<UserLevelConfig> userLevelConfigList=RedisUtil.getUserLevelConfigList(ConfigRedisConstant.USERLEVELCONFIG);
			if(EmptyUtils.isEmpty(userLevelConfigList)) {
				userLevelConfigList=configService.getUserLevelConfigList();
				//数据库里面也是空
				if(EmptyUtils.isEmpty(userLevelConfigList)) {
					rq0005RespDto.setRespCode(RespEnum.RESP_CODE_900002.getCode());
					rq0005RespDto.setRespMsg(RespEnum.RESP_CODE_900002.getMsg());
					return rq0005RespDto;
				}
			}
			rq0005RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
			rq0005RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
			rq0005RespDto.setDataList(userLevelConfigList);
			return rq0005RespDto;
		} catch (Exception e) {
			rq0005RespDto.setRespCode(RespEnum.RESP_CODE_999999.getCode());
			rq0005RespDto.setRespMsg(RespEnum.RESP_CODE_999999.getMsg());
			return rq0005RespDto;
		}
		
	}

	@Override
	public RQ0006RespDto queryUserLevel(RQ0006ReqDto RQ0006ReqDto) {
		RQ0006RespDto RQ0006RespDto = new RQ0006RespDto();
		try {
			if(!CheckDate.check(RQ0006ReqDto, RQ0006RespDto, TransEnum.TRANS_RQ0006.getCode())) {
				return RQ0006RespDto;
			}
			String reqSn=RQ0006ReqDto.getReqSn();
			String userId=RQ0006ReqDto.getUserId();
			if(userId == null || "".equals(userId)){
				RQ0006RespDto.setRespCode(RespEnum.RESP_CODE_100001.getCode());
				RQ0006RespDto.setRespMsg(RespEnum.RESP_CODE_100001.getMsg());
				RQ0006ReqDto.setReqSn(reqSn);
				return RQ0006RespDto;
			}
			UserLevel userLevel=userLevelService.queryUserLevel(userId);
			if(userLevel == null) {
				//说明用户第一次使用，还没有插入用户等级表
				int nextLevel=0;
				//先去缓存查询下一等级的好币值配置
				String levelCode="level"+nextLevel;
				UserLevelConfig userLevelConfig=RedisUtil.getUserLevelConfig(levelCode+ ConfigRedisConstant.USERLEVELCONFIG);
				if(EmptyUtil.isEmpty(userLevelConfig)) {
					//缓存为空，去数据库里面查询
					userLevelConfig=configService.getUserLevelConfigByLevelCode(levelCode);
				}
				if(userLevelConfig == null) {
					RQ0006RespDto.setRespCode(RespEnum.RESP_CODE_900002.getCode());
					RQ0006RespDto.setRespMsg(RespEnum.RESP_CODE_900002.getMsg());
					RQ0006RespDto.setData(userLevel);
					RQ0006ReqDto.setReqSn(reqSn);
					return RQ0006RespDto;
				}
				RQ0006RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
				RQ0006RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
				userLevel = new UserLevel();
				userLevel.setUserId(userId);
				userLevel.setUserLevel(0);
				userLevel.setUserAccountMr(0);
				userLevel.setIsPopUp(0);
				RQ0006RespDto.setData(userLevel);
				RQ0006RespDto.setNextLevelValue(userLevelConfig.getLevelAmount());
				RQ0006ReqDto.setReqSn(reqSn);
				return RQ0006RespDto;
			}
			//查询下一级需要的好币值
			//最大等级
			if(userLevel.getUserLevel()==7) {
				RQ0006RespDto.setNextLevelValue(0);
			}else {
				int nextLevel=userLevel.getUserLevel();
				//先去缓存查询下一等级的好币值配置
				String levelCode="level"+nextLevel;
				UserLevelConfig userLevelConfig=RedisUtil.getUserLevelConfig(levelCode+ ConfigRedisConstant.USERLEVELCONFIG);
				if(EmptyUtil.isEmpty(userLevelConfig)) {
					//缓存为空，去数据库里面查询
					userLevelConfig=configService.getUserLevelConfigByLevelCode(levelCode);
				}
				if(userLevelConfig == null) {
					RQ0006RespDto.setRespCode(RespEnum.RESP_CODE_900002.getCode());
					RQ0006RespDto.setRespMsg(RespEnum.RESP_CODE_900002.getMsg());
					RQ0006RespDto.setData(userLevel);
					RQ0006ReqDto.setReqSn(reqSn);
					return RQ0006RespDto;
				}
				RQ0006RespDto.setNextLevelValue(userLevelConfig.getLevelAmount());
			}
			RQ0006RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
			RQ0006RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
			RQ0006RespDto.setData(userLevel);
			RQ0006ReqDto.setReqSn(reqSn);
			return RQ0006RespDto;
		} catch (Exception e) {
			RQ0006RespDto.setRespCode(RespEnum.RESP_CODE_999999.getCode());
			RQ0006RespDto.setRespMsg(RespEnum.RESP_CODE_999999.getMsg());
			return RQ0006RespDto;
		}
	}

	
	@Override
	public RT0001RespDto signIn(RT0001ReqDto RT0001ReqDto) {
		RT0001RespDto RT0001RespDto = new RT0001RespDto();
		try {
			if(!CheckDate.check(RT0001ReqDto, RT0001RespDto, TransEnum.TRANS_RT0001.getCode())) {
				return RT0001RespDto;
			}
			String reqSn=RT0001ReqDto.getReqSn();
			String userId=RT0001ReqDto.getUserId();
			if(userId == null || "".equals(userId)){
				RT0001RespDto.setRespCode(RespEnum.RESP_CODE_100001.getCode());
				RT0001RespDto.setRespMsg(RespEnum.RESP_CODE_100001.getMsg());
				RT0001RespDto.setReqSn(reqSn);
				return RT0001RespDto;
			}
			//0 代表成功，
			//1代表今日已经签到过,
			//2代表缓存和数据库里面都没有签到配置信息 ,
			//3代表调用账户系统错误,
			//4代表缓存和数据库里面都没有用户等级配置信息
			int result=rewardService.signIn(userId);
			log.info("调用rewardService.signIn,userId={},返回{}",userId,result);
			if(result == 1) {
				RT0001RespDto.setRespCode(RespEnum.RESP_CODE_100017.getCode());
				RT0001RespDto.setRespMsg(RespEnum.RESP_CODE_100017.getMsg());
				RT0001RespDto.setReqSn(reqSn);
				return RT0001RespDto;
			}
			if(result == 2 || result == 4) {
				RT0001RespDto.setRespCode(RespEnum.RESP_CODE_999999.getCode());
				RT0001RespDto.setRespMsg(RespEnum.RESP_CODE_999999.getMsg());
				RT0001RespDto.setReqSn(reqSn);
				return RT0001RespDto;
			}
			if(result == 3) {
				RT0001RespDto.setRespCode(RespEnum.RESP_CODE_100018.getCode());
				RT0001RespDto.setRespMsg(RespEnum.RESP_CODE_100018.getMsg());
				RT0001RespDto.setReqSn(reqSn);
				return RT0001RespDto;
			}
			RT0001RespDto.setRespCode(RespEnum.RESP_CODE_000000.getCode());
			RT0001RespDto.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
			RT0001RespDto.setReqSn(reqSn);
			return RT0001RespDto;
		} catch (Exception e) {
			RT0001RespDto.setRespCode(RespEnum.RESP_CODE_999999.getCode());
			RT0001RespDto.setRespMsg(RespEnum.RESP_CODE_999999.getMsg());
			return RT0001RespDto;
		}
	}




	@Override
	public PageResponseDTO<IncentiveRecord> getIncentiveRecordPage(RQ0001ReqDto dto) {
		PageResponseDTO<IncentiveRecord> PageResponseDTO = new PageResponseDTO();
		try {
			if(!CheckDate.check(dto, PageResponseDTO, TransEnum.TRANS_RQ0001.getCode())) {
				return PageResponseDTO;
			}
			String reqSn=dto.getReqSn();
			String userId = dto.getUserId();
			//校验传入的类型是否正确
			String scoreType = dto.getScoreType();
			if(EmptyUtils.isEmpty(userId)){
				PageResponseDTO.setRespCode(RespEnum.RESP_CODE_200001.getCode());
				PageResponseDTO.setRespMsg(RespEnum.RESP_CODE_200001.getMsg());
				PageResponseDTO.setReqSn(reqSn);
				return PageResponseDTO;
			}
			
			if(!EmptyUtils.isEmpty(scoreType)){
				if(!"add".equals(scoreType) && !"sub".equals(scoreType) ){
					PageResponseDTO.setRespCode(RespEnum.RESP_CODE_100003.getCode());
					PageResponseDTO.setRespMsg(RespEnum.RESP_CODE_100003.getMsg());
					PageResponseDTO.setReqSn(reqSn);
					return PageResponseDTO;
				}
			}
			if(dto.getPageNo() == null && dto.getPageSize() == null){
				PageResponseDTO.setRespCode(RespEnum.RESP_CODE_100001.getCode());
				PageResponseDTO.setRespMsg(RespEnum.RESP_CODE_100001.getMsg());
				PageResponseDTO.setReqSn(reqSn);
				return PageResponseDTO;
			}
			if(dto.getPageNo()<1 ||  dto.getPageSize()<0){
				PageResponseDTO.setRespCode(RespEnum.RESP_CODE_100003.getCode());
				PageResponseDTO.setRespMsg(RespEnum.RESP_CODE_100003.getMsg());
				PageResponseDTO.setReqSn(reqSn);
				return PageResponseDTO;
			}
			PageInfo<IncentiveRecord> page=recordService.getPageRecord(scoreType, dto.getPageNo(), dto.getPageSize(),userId);
			PageResponseDTO.setDataList(page.getList());
			PageResponseDTO.setPageNo(page.getPageNum());
			PageResponseDTO.setPageSize(page.getPageSize());
			PageResponseDTO.setTotal(page.getTotal());
			PageResponseDTO.setPageCount(page.getPages());
			PageResponseDTO.setRespCode(RespEnum.RESP_CODE_000000.getCode());
			PageResponseDTO.setRespMsg(RespEnum.RESP_CODE_000000.getMsg());
			PageResponseDTO.setReqSn(reqSn);
			return PageResponseDTO;
		} catch (Exception e) {
			PageResponseDTO.setRespCode(RespEnum.RESP_CODE_999999.getCode());
			PageResponseDTO.setRespMsg(RespEnum.RESP_CODE_999999.getMsg());
			return PageResponseDTO;
		}
	}




	@Override
	public RT0002RespDto share(RT0002ReqDto RT0002ReqDto) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public RQ0004RespDto getSigninConfigurationList(RQ0004ReqDto dto) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public RQ0013RespDto queryAccountInfo(RQ0013ReqDto dto) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public RT0003RespDto updateUserPopUp(RT0003ReqDto dto) {
		// TODO Auto-generated method stub
		return null;
	}


}
