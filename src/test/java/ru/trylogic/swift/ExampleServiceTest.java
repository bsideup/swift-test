package ru.trylogic.swift;

import com.facebook.nifty.client.HttpClientConnector;
import com.facebook.swift.service.ThriftClientManager;
import com.google.common.util.concurrent.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.trylogic.swift.protocol.TEmptyNameException;
import ru.trylogic.swift.protocol.TExampleAsyncService;
import ru.trylogic.swift.protocol.TExampleService;
import ru.trylogic.swift.protocol.TUser;

import java.net.URI;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({
        "server.port:0"
})
public class ExampleServiceTest {

    @Value("${local.server.port}")
    protected int port;

    TExampleService service;
    
    TExampleAsyncService asyncService;
    
    @Before
    public void setUp() throws Exception {
        HttpClientConnector connector = new HttpClientConnector(URI.create("http://localhost:" + port + "/thrift/"));

        ThriftClientManager clientManager = new ThriftClientManager();
        service = clientManager.createClient(connector, TExampleService.class).get();
        
        asyncService = clientManager.createClient(connector, TExampleAsyncService.class).get();

    }

    @Test
    public void testSayHello() throws Exception {
        assertEquals("Hello Sergei", service.sayHello(new TUser("Sergei", null)));
    }

    @Test(expected = TEmptyNameException.class)
    public void testSayHelloWithEmptyName() throws Exception {
        service.sayHello(null);
    }

    @Test
    public void testSayHelloAsync() throws Exception {
        assertEquals("Hello Sergei", asyncService.sayHello(new TUser("Sergei", "Egorov")).get());
    }

    @Test(expected = TEmptyNameException.class)
    public void testSayHelloAsyncWithEmptyName() throws Throwable {
        ListenableFuture<String> future = asyncService.sayHello(null);

        Futures.get(future, TEmptyNameException.class);
    }
}
