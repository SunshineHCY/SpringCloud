package com.feign.springfeign.servcie;

import org.springframework.stereotype.Component;

@Component
public class HelloServiceHystric implements  HelloService{

    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}
