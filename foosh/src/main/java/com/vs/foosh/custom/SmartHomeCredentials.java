package com.vs.foosh.custom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vs.foosh.api.model.AbstractSmartHomeCredentials;

public class SmartHomeCredentials extends AbstractSmartHomeCredentials {

    @Override
    public void loadSmartHomeCredentials() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("src/main/java/com/vs/foosh/custom/secrets.json"));
            ObjectMapper mapper = new ObjectMapper();

            SmartHomeCredentials jsonCredentials = mapper.readValue(jsonData, SmartHomeCredentials.class);
            this.uri = jsonCredentials.getUri();
            this.credentials = jsonCredentials.getCredentials();
        } catch (IOException e) {
            System.err.println("Something went wrong while reading secrets.json:\n" + e);
        }
    }
    
}
