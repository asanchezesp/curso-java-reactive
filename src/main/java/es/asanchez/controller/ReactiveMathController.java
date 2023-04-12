package es.asanchez.controller;

import es.asanchez.dto.MultiplyRequest;
import es.asanchez.dto.Response;
import es.asanchez.exception.InputValidationException;
import es.asanchez.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("react/math")
public class ReactiveMathController {

    @Autowired
    private ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) throws InputValidationException {
        return Mono.just(input)
                .handle(((integer, sink) -> {
                    if (integer >= 5 && integer <= 20)
                        sink.next(integer);
                    else sink.error(new InputValidationException(integer));
                }))
                .cast(Integer.class)
                .flatMap(reactiveMathService::findSquare);
    }

    //Misma funcion que arriba pero no lanza excepcion
    @GetMapping("square2/{input}")
    public Mono<ResponseEntity<Response>> findSquare2(@PathVariable int input) {
        return Mono.just(input)
                .filter(i-> i  >= 10 && i <= 20)
                .flatMap(reactiveMathService::findSquare)
                .map(ResponseEntity::ok)
                //Si no se cumple la condicion del filter salta aqui
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("table/{input}")
    public Flux<Response> table(@PathVariable int input) {
        return reactiveMathService.table(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //Aqui no se puede declarar input como Mono, ya que el valor viene en la peticion
    public Flux<Response> tableStream(@PathVariable int input) {
        return reactiveMathService.table(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequest> dto) {
        return reactiveMathService.multiply(dto);
    }

    @GetMapping("nse")
    public Response error() {
        throw new NoSuchElementException("No hay na");
    }
}
