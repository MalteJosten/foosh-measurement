package com.vs.foosh.api.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private static final String HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;
    private static int port;

    @Bean
    private static void readInLinkConfig() {
        Properties config = new Properties();
        try (InputStream is = new FileInputStream(new File("src/main/resources/application.properties"))) {
            config.load(is);
            String serverPort = config.getProperty("server.port");

            if (serverPort == null || serverPort.equals("")) {
                port = DEFAULT_PORT;
                System.out.println(
                        "Field 'server.port' (" + serverPort
                                + ") in application.properties is either empty or non-existent!\nUsing default port: "
                                + DEFAULT_PORT);
                return;
            }

            try {
                port = Integer.parseInt(serverPort);
                System.out.println("Successfully read application.properties!\nUsing port: " + port);
            } catch (NumberFormatException e) {
                port = DEFAULT_PORT;
                System.out.println(
                        "Field 'server.port' (" + serverPort
                                + ") in application.properties cannot be converted to an Integer!\nUsing default port: "
                                + DEFAULT_PORT);
            } finally {
                LinkBuilder.setServerVariables(HOST, port);
            }
        } catch (IOException e) {
            port = DEFAULT_PORT;
            System.out.println(
                    "Encountered a problem reading application.properties!\nUsing default port: "
                            + DEFAULT_PORT);
        } finally {
            LinkBuilder.setServerVariables(HOST, port);
        }
    }
    
}
