package com.jst.reward.common.util;

import com.jst.prodution.reward.common.dto.XGenericRequestDTO;
import com.jst.prodution.reward.common.dto.XGenericResponseDTO;
import com.jst.prodution.reward.common.enums.RespEnum;

public class CheckDate {

	public static boolean check(XGenericRequestDTO requestDTO,XGenericResponseDTO responseDTO,String rightTransCode){
		String reqSn = requestDTO.getReqSn();
		//校验流水号是否为空
		if(reqSn == null || "".equals(reqSn)){
			responseDTO.setRespCode(RespEnum.RESP_CODE_100001.getCode());
			responseDTO.setRespMsg(RespEnum.RESP_CODE_100001.getMsg());
			return false;
		}
		//长度是否30位
		if(reqSn.length()!=30){
			responseDTO.setRespCode(RespEnum.RESP_CODE_100015.getCode());
			responseDTO.setRespMsg(RespEnum.RESP_CODE_100015.getMsg());
			responseDTO.setReqSn(reqSn);
			return false;
		}
		//校验校验码
		String code = requestDTO.getTransCode();
		if(!code.equals(rightTransCode)){
			responseDTO.setRespCode(RespEnum.RESP_CODE_100016.getCode());
			responseDTO.setRespMsg(RespEnum.RESP_CODE_100016.getMsg());
			responseDTO.setReqSn(reqSn);
			return false;
		}
		return true;
	}
}
