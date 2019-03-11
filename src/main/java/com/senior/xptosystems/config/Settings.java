package com.senior.xptosystems.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Settings implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${settings.limit}")
    private Integer limit;

    @Value("${settings.resource}")
    private String resource;

    public Settings() {
        this.limit = 0;
//        this.resource = "";
    }

    public Settings(Integer limit, String resource) {
        this.limit = limit;
        this.resource = resource;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

}
