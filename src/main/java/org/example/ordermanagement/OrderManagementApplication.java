package org.example.ordermanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class OrderManagementApplication {

    public static void main(String[] args) {
        log.info("OrderManagementApplication started");
        SpringApplication.run(OrderManagementApplication.class, args);
    }

}
