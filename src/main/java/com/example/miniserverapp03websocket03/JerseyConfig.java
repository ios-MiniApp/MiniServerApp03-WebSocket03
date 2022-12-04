package com.example.miniserverapp03websocket03;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("com.example.miniserverapp03websocket03");
    }

}
