//package com.jst.demo.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.jst.demo.bean.ConfigBean;
//import com.jst.demo.bo.BaseBo;
//import com.jst.demo.service.test.interfaces.TestMqService;
//import com.jst.framework.common.bean.Result;
//import com.jst.framework.redis.RedisUtil;
//
//import io.swagger.annotations.ApiParam;
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//	private final Logger log = LoggerFactory.getLogger(TestController.class);
//	
//	@Autowired
//	TestMqService testMqService;
//	
//	 @RequestMapping(value = "/testMq", method = RequestMethod.GET)
//	 @ResponseBody
//	 public Result testMq(HttpServletRequest request, HttpServletResponse response) {
//	    Result result = new Result();
////	    List<ConfigBean> list=RedisUtil.getConfigBeanList("configBeanList");
//	    
//	    
//	    log.info("=================操作testMq接口开始，【入参={}】");
//	    String str="hello world";
//	    testMqService.testMq(str);
//	    log.info("=================操作testMq接口结束");
//	    return result;
//	 }
//	 
// 	@RequestMapping(value = "/testdemo", method = RequestMethod.POST)
//    @ResponseBody
//    public Result test(@ApiParam(name="BaseBo",value="测试baseBo",required=true)@RequestBody BaseBo baseBo) {
//        Result result = new Result();
//        return result;
//    }
//}
