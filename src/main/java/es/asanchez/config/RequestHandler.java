package es.asanchez.config;

import es.asanchez.dto.MultiplyRequest;
import es.asanchez.dto.Response;
import es.asanchez.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
public class RequestHandler {

    @Autowired
    private ReactiveMathService mathService;

    public Mono<ServerResponse> square(ServerRequest request){
        return ServerResponse.ok().body(mathService.findSquare(Integer.valueOf(request.pathVariable("input"))), Response.class);
    }

    public Mono<ServerResponse> table(ServerRequest request){
        Flux<Response> response = mathService.table(Integer.valueOf(request.pathVariable("input")));
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(response, Response.class);
    }

    public Mono<ServerResponse> multiply(ServerRequest request){
        Mono<MultiplyRequest> mono = request.bodyToMono(MultiplyRequest.class);
        return ServerResponse.ok()
                .body(mathService.multiply(mono), Response.class);
    }

}
