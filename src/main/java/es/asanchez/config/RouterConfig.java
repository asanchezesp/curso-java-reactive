package es.asanchez.config;

import es.asanchez.dto.FailedValidationResponse;
import es.asanchez.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter(){
        return RouterFunctions.route()
                .path("router",this::serverResponseRouterFunction)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
                //El predicado sirve como filtro
                .GET("square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHandler::square)
                .GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("Only 10 to 20 allowed"))
                .GET("tableEvent/{input}",requestHandler::table)
                .POST("multiply",requestHandler::multiply)
                .onError(InputValidationException.class,exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
        return (err,req) -> {
            InputValidationException ex = (InputValidationException) err;
            FailedValidationResponse res = new FailedValidationResponse();
            res.setInput(ex.getINPUT());
            res.setMessage(ex.getMessage());
            res.setErrorCode(ex.getErrorCode());
          return ServerResponse.badRequest().bodyValue(res);
        };
    }
}
