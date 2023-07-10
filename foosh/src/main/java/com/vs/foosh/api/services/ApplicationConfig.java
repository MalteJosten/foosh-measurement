package com.vs.foosh.api.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vs.foosh.api.model.SmartHomeCredentials;

@Configuration
public class ApplicationConfig {
    private static final String HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;
    private static int port;

    private static SmartHomeCredentials smartHomeCredentials;

    @Bean
    private static void readInApplicationProperties() {
        Properties config = new Properties();
        try (InputStream is = new FileInputStream(new File("src/main/resources/application.properties"))) {
            config.load(is);

            String serverPort = config.getProperty("server.port");
            setupServerPort(serverPort);

            String smartHomeCredentialPath = config.getProperty("smartHomeCredentialPath");
            setupSmartHomeCredentials(smartHomeCredentialPath);

        } catch (IOException e) {
            port = DEFAULT_PORT;
            System.out.println(
                    "Encountered a problem reading application.properties!\nUsing default port: "
                            + DEFAULT_PORT);
        } finally {
            LinkBuilder.setServerVariables(HOST, port);
        }
    }

    private static void setupServerPort(String serverPort) {
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
    }

    private static void setupSmartHomeCredentials(String path) {
        if (path == null || path.equals("")) {
            System.err.println(
                    "Field 'smartHomeCredentialsPath' (" + path
                            + ") in application.properties is either empty or non-existent! This might become a problem when trying to communicate with the SmartHome API.\n"
                            + "Please set a correct path and restart the server!");
            return;
        }

        File secrets = new File(path);
        if (secrets.exists() && !secrets.isDirectory()) {
            try {
                byte[] jsonData = Files.readAllBytes(Paths.get("src/main/java/com/vs/foosh/custom/secrets.json"));
                ObjectMapper mapper = new ObjectMapper();

                smartHomeCredentials = mapper.readValue(jsonData, SmartHomeCredentials.class);
                System.out.println(smartHomeCredentials);
            } catch (IOException e) {
                System.err.println("Something went wrong while reading secrets.json:\n" + e);
            }
        }
    }

    public static SmartHomeCredentials getSmartHomeCredentials() {
        return smartHomeCredentials;
    }
    
}