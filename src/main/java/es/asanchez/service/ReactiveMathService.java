package es.asanchez.service;

import es.asanchez.dto.MultiplyRequest;
import es.asanchez.dto.Response;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int intput){
        return Mono.fromSupplier(() -> intput * intput)
                .map(Response::new);
    }

    public Flux<Response> table(int input){
        return Flux.range(1,10)
                .doOnNext(v ->SleepUtil.sleepSeconds(1))
                .doOnNext(System.out::println)
                .map(v -> new Response(v*input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequest> dto){
        return dto.map(d -> d.getA() * d.getB())
                .map(Response::new);
    }

}
