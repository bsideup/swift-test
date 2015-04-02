package ru.trylogic.swift;

import com.facebook.nifty.client.HttpClientConnector;
import com.facebook.swift.service.ThriftClientManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.trylogic.swift.protocol.TEmptyNameException;
import ru.trylogic.swift.protocol.TExampleService;

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
    
    @Before
    public void setUp() throws Exception {
        HttpClientConnector connector = new HttpClientConnector(URI.create("http://localhost:" + port + "/thrift/"));

        ThriftClientManager clientManager = new ThriftClientManager();
        service = clientManager.createClient(connector, TExampleService.class).get();

    }

    @Test
    public void testSayHello() throws Exception {
        assertEquals("Hello world", service.sayHello("world"));
    }

    @Test(expected = TEmptyNameException.class)
    public void testSayHelloWithEmptyName() throws Exception {
        service.sayHello(null);
    }
}
