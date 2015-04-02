package ru.trylogic.swift.protocol;

import org.springframework.stereotype.Component;

@Component
public class TExampleServiceHandler implements TExampleService {

    public String sayHello(String name) throws TEmptyNameException {
        if(name == null || name.isEmpty()) {
            throw new TEmptyNameException();
        }
        
        return "Hello " + name;
    }
}
