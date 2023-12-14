package de.dicos.springboot.repairservice.restful.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "repairservice")
public class RepairserviceConfig
{
	private String priceList;
}
