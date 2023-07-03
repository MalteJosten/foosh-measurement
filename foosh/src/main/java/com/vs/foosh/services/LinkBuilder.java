package com.vs.foosh.services;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class LinkBuilder {
    private static final String host = "localhost";
    private static final int port = 8080;

    public static URI buildPath(String prefix, UUID id) {
        UriComponents uri = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host(host)
            .port(port)
            .path(prefix)
            .path(id.toString()).build();

        return uri.toUri();
    }

    public static URI getDeviceListLink() {
        UriComponents uri = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host(host)
            .port(port)
            .path("devices").build();

        return uri.toUri();
    }
}
