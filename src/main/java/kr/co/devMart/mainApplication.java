package kr.co.devMart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("kr.co.devMart.mapper")
public class mainApplication {
    public static void main(String[] args) {
        SpringApplication.run(mainApplication.class, args);
    }
}