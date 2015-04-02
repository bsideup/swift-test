package ru.trylogic.swift.protocol;

import org.springframework.stereotype.Component;

@Component
public class TExampleServiceHandler implements TExampleService {

    public String sayHello(TUser user) throws TEmptyNameException {
        if(user == null || user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new TEmptyNameException();
        }
        
        return "Hello " + user.getFirstName();
    }
}
