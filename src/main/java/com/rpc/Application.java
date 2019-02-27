package com.rpc;

import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;  
  
  
@SpringBootApplication  
@ImportResource(locations={"classpath:redis.xml","classpath:service.xml"})
public class Application {  
  
    public static void main(String[] args) {  
        SpringApplication.run(Application.class, args);  
    }  
}  