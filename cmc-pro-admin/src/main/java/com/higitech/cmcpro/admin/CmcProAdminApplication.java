package com.higitech.cmcpro.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(basePackages={"com.higitech.cmcpro.admin.modules.system.mapper"})
@EnableCaching
@SpringBootApplication
public class CmcProAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmcProAdminApplication.class, args);
	}

}
