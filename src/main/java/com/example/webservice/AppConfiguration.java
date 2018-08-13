package com.example.webservice;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfiguration extends ResourceConfig {
    public AppConfiguration() {
        register(new ApplicationBinder());
    }
}
