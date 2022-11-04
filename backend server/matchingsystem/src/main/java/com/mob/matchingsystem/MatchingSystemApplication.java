package com.mob.matchingsystem;

import com.mob.matchingsystem.service.MatchingService;
import com.mob.matchingsystem.service.impl.MatchingServiceimpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        MatchingServiceimpl.matchingpool.start();
        SpringApplication.run(MatchingSystemApplication.class, args);
    }
}