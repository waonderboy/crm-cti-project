package com.brogs.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/app.properties")
@SpringBootApplication
public class CrmCtiProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmCtiProjectApplication.class, args);
    }

}
