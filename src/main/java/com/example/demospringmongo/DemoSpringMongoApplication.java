package com.example.demospringmongo;

import com.example.demospringmongo.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RedisConfig.class)
public class DemoSpringMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringMongoApplication.class, args);
    }

}
