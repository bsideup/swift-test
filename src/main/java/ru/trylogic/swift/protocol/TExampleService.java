package ru.trylogic.swift.protocol;

import com.facebook.swift.service.ThriftException;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

@ThriftService
public interface TExampleService {

    @ThriftMethod(exception = {
            @ThriftException(type = TEmptyNameException.class, id = 1)
    })
    String sayHello(String name) throws TEmptyNameException;
}
