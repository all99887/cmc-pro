package com.higitech.cmcpro.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages={"com.higitech.cmcpro.admin.modules.system.mapper"})
@SpringBootApplication
public class CmcProAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmcProAdminApplication.class, args);
	}

}
