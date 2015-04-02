package ru.trylogic.swift.protocol;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TExampleServiceHandler implements TExampleService {

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    
    public String sayHello(TUser user) throws TEmptyNameException {
        if(user == null || user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new TEmptyNameException();
        }
        
        return "Hello " + user.getFirstName();
    }

    @Override
    public ListenableFuture<String> sayHelloAsync(TUser user) throws TEmptyNameException {
        SettableFuture<String> result = SettableFuture.create();

        executorService.schedule(() -> {
            if(user == null || user.getFirstName() == null || user.getFirstName().isEmpty()) {
                result.setException(new TEmptyNameException());
                return;
            }

            result.set("Hello " + user.getFirstName());
        }, 2, TimeUnit.SECONDS);
        
        return result;
    }
}
