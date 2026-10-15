package com.whydigit.fms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Views {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

//    @PostConstruct
//    public void initialize() {
//        try {
//            executeQueries();
//        } catch (Exception e) {
//            throw new RuntimeException("Error initializing database", e);
//        }
//    }

  

}
