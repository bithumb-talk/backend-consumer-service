package com.bithumb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication(scanBasePackages={"com.bithumb.consumer","com.bithumb.websocket","com.bithumb.quoteinit","com.bithumb.coin","com.bithumb.candlestick","com.bithumb.common","com.bithumb.changerate"})
@EnableEurekaClient
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
