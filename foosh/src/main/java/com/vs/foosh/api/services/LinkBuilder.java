package com.vs.foosh.api.services;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class LinkBuilder {
    //TODO: Read in variables from config file
    private static final String host = "localhost";
    private static final int port = 8080;

    public static URI buildPath(List<String> paths) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host(host)
            .port(port);

        paths.forEach(path -> {
            uriBuilder
                .path(path + '/');
        });

        return uriBuilder.build().toUri();
    }

    public static URI getDeviceListLink() {
        UriComponents uri = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host(host)
            .port(port)
            .path("devices")
            .build();

        return uri.toUri();
    }

    public static URI getDeviceLink(String id) {
        UriComponents uri = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host(host)
            .port(port)
            .path("device/")
            .path(id)
            .build();

        return uri.toUri();
    }

    public static Map<String, Object> getJSONLinkBlock(Map<String, String> linkMapping) {
        Map<String, Object> linkBlock = new HashMap<>();

        for (String key: linkMapping.keySet()) {
            linkBlock.put(key, linkMapping.get(key));
        }

        return linkBlock;
    }
}
