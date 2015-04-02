package ru.trylogic.swift;

import com.facebook.nifty.processor.NiftyProcessorAdapters;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.trylogic.swift.protocol.TExampleAsyncService;
import ru.trylogic.swift.protocol.TExampleService;

import javax.servlet.Servlet;
import java.util.Arrays;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);;
    }
    
    @Bean
    TProtocolFactory protocolFactory() {
        return new TBinaryProtocol.Factory();
    }
    
    @Bean
    ThriftCodecManager thriftCodecManager() {
        return new ThriftCodecManager();
    }
    
    @Bean
    Servlet thrift(ThriftCodecManager thriftCodecManager, TProtocolFactory protocolFactory, TExampleAsyncService exampleService) {
        ThriftServiceProcessor processor = new ThriftServiceProcessor(thriftCodecManager, Arrays.<ThriftEventHandler>asList(), exampleService);

        return new TServlet(
                NiftyProcessorAdapters.processorToTProcessor(processor),
                protocolFactory,
                protocolFactory
        );
    }
}
