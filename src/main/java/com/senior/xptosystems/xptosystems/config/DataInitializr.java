package com.senior.xptosystems.xptosystems.config;

import com.senior.xptosystems.xptosystems.model.City;
import com.senior.xptosystems.xptosystems.services.ICityComponent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ICityComponent cityComponent;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("Start");

        System.out.println("Read CSV file to database");

        List<City> list = cityComponent.readCSV();

        if (list.isEmpty()) {
            System.err.println("Empty csv file!");
            return;
        }

        System.out.println("waiting.... total cities found " + list.size());

        cityComponent.storeAll(list);

        System.out.println("Success, registed cities");

    }

}
