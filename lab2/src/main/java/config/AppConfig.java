package config;

import functions.FunctionSystem;
import functions.LogarithmFunction;
import functions.TrigonometricFunctions;
import functions.stubs.LnStub;
import functions.stubs.SinStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Bean
    @Profile("test")
    public TrigonometricFunctions testTrigonometricFunctions() {
        return new TrigonometricFunctions(new SinStub()::sin);
    }

    @Bean
    @Profile("test")
    public LogarithmFunction testLogarithmFunction() {
        return new LogarithmFunction(new LnStub()::ln);
    }

    @Bean
    @Profile("!test")
    public TrigonometricFunctions trigonometricFunctions() {
        return new TrigonometricFunctions(Math::sin);
    }

    @Bean
    @Profile("!test")
    public LogarithmFunction logarithmFunction() {
        return new LogarithmFunction(Math::log);
    }

    @Bean
    public FunctionSystem functionSystem(TrigonometricFunctions trigFunctions,
                                         LogarithmFunction logFunction) {
        return new FunctionSystem(trigFunctions, logFunction);
    }
}