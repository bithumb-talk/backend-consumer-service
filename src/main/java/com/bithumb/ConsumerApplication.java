package com.bithumb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.bithumb.consumer","com.bithumb.websocket","com.bithumb.quoteinit","com.bithumb.coin","com.bithumb.candlestick","com.bithumb.common","com.bithumb.changerate"})
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
