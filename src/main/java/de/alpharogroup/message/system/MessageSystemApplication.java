package de.alpharogroup.message.system;

import de.alpharogroup.message.system.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({ ApplicationProperties.class })
public class MessageSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageSystemApplication.class, args);
	}

}
