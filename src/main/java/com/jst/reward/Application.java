package com.jst.reward;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.codecentric.boot.admin.config.EnableAdminServer;



/**
 * 
 * 
 * @Package: com.jst.message  
 * @ClassName: Application 
 * @Description: SpringBoot启动类
 *
 * @author: lixin 
 * @date: 2016年12月15日 下午2:56:58 
 * @version V1.0
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.jst")
@EnableAdminServer
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
@ImportResource(locations={"classpath:spring-beans.xml"})
@EnableCaching
@MapperScan("com.jst.reward.dao")//将项目中对应的mapper类的路径加进来就可以了
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	

}
