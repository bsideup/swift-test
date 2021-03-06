package ru.trylogic.swift.protocol;

import com.facebook.swift.service.ThriftException;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.google.common.util.concurrent.ListenableFuture;

@ThriftService("example")
public interface TExampleAsyncService {

    @ThriftMethod(exception = {
            @ThriftException(type = TEmptyNameException.class, id = 1)
    })
    ListenableFuture<String> sayHello(TUser name) throws TEmptyNameException;
}
