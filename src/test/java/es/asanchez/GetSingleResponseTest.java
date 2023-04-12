package es.asanchez;

import es.asanchez.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        Response response = webClient.get().uri("react/math/square/{number}",12).retrieve()
                .bodyToMono(Response.class)
                .block();
        System.out.println(response);
    }

    @Test
    public void stepVerifierTest() {
        Mono<Response> response = webClient.get().uri("react/math/square/{number}",5).retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response).expectNextMatches(r -> r.getOutput() == 25).verifyComplete();
    }

}
