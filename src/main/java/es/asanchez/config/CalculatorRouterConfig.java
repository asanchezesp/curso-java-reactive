package es.asanchez.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CalculatorRouterConfig {

    @Autowired
    private CalculatorHandler handler;

    @Bean
    public RouterFunction<ServerResponse> highLevelCalculatorRouter(){
        return RouterFunctions.route()
                .path("calculator",this::serverResponseCalculatorFunction)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseCalculatorFunction(){
        return RouterFunctions.route()
                .GET("{first}/{second}",isOperator("+"), handler::additionHandler)
                .GET("{first}/{second}",isOperator("-"), handler::substractionHandler)
                .GET("{first}/{second}",isOperator("/"), handler::divisionHandler)
                .GET("{first}/{second}",isOperator("*"), handler::multiplicationHandler)
                .GET("{first}/{second}",req -> ServerResponse.badRequest().bodyValue("Hedaer OP must not be empty"))
                .build();
    }

    private RequestPredicate isOperator(String op){
        return RequestPredicates.headers(headers -> op.equals(headers.asHttpHeaders().toSingleValueMap().get("OP")));
    }
}
