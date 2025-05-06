package config;

import functions.stubs.LnStub;
import functions.stubs.SinStub;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public SinStub sinStub() {
        return new SinStub();
    }

    @Bean
    public LnStub lnStub() {
        return new LnStub();
    }
}