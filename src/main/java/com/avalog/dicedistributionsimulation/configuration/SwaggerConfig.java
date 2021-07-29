package com.avalog.dicedistributionsimulation.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class SwaggerConfig {

  @Bean
  public OpenAPI diceSimulationApi(@Value("${application-version}") String appVersion) {
    return new OpenAPI()
        .info(
            new Info()
                .title("Dice Roller Simulation API")
                .description("Randomly Dice Roll")
                .version(appVersion));
  }
}
