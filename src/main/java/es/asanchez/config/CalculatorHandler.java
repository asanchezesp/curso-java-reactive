package es.asanchez.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
public class CalculatorHandler {

    public Mono<ServerResponse> additionHandler(ServerRequest request){
        return calculatorProcess(request,(a,b) -> ServerResponse.ok().bodyValue( a+b));
    }

    public Mono<ServerResponse> substractionHandler(ServerRequest request){
        return calculatorProcess(request,(a,b) ->ServerResponse.ok().bodyValue( a-b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request){
        return calculatorProcess(request,(a,b) ->ServerResponse.ok().bodyValue( a/b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request){
        return calculatorProcess(request,(a,b) -> ServerResponse.ok().bodyValue( a*b));
    }

    private Mono<ServerResponse> calculatorProcess(ServerRequest request, BiFunction<Integer,Integer,Mono<ServerResponse>> opLogic){
        return opLogic.apply( getIntValue(request,"first"),getIntValue(request,"second"));
    }

    private int getIntValue(ServerRequest r, String key){
        return Integer.parseInt(r.pathVariable(key));
    }
}
