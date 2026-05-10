package com.anaeltech.bookea_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

  @Value("${server.port:8080}")
  private String serverPort;

  @Bean
  public OpenAPI customOpenApi() {

    Server server = new Server()
        .url("http://localhost:" + serverPort)
        .description("The API server developement");

    Info info = new Info()
        .title("Bookea API")
        .version("1.0.0")
        .description("Bookea API");

    return new OpenAPI()
        .info(info)
        .servers(List.of(server));
  }

}
