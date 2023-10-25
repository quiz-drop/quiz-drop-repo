package com.sparta.quizdemo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableCaching
public class QuizdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizdemoApplication.class, args);
    }

    /*
    프로젝트가 처음 실행될 때, 한 번만 실행 시켜주는 어노테이션으로
    UTC가 아닌 서울로 시간대 설정을 위해 추가
     */
    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
