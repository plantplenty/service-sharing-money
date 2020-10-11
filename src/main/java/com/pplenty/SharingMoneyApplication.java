package com.pplenty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SharingMoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharingMoneyApplication.class, args);
    }

}
