package ru.trylogic.swift.protocol;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TExampleServiceHandler implements TExampleAsyncService {

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    
    @Override
    public ListenableFuture<String> sayHello(TUser user) throws TEmptyNameException {
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
