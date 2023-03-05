package es.asanchez;

import es.asanchez.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

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

}
