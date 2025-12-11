package org.openoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class FlowOAApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FlowOAApplication.class, args);
    }


}
