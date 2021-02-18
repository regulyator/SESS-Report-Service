package org.sess.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SESSReportService {

    public static void main(String[] args) {
        SpringApplication.run(SESSReportService.class, args);
    }
}
