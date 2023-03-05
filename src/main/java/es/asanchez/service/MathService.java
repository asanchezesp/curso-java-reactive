package es.asanchez.service;

import es.asanchez.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public Response findSquare(int input){
        return new Response(input*input);
    }

    public List<Response> multiplicationTable(int input){
        return IntStream.range(1,11)
                //Por cada elemento
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> System.out.println("Procesando: " + i))
                .mapToObj(i -> new Response(input*i))
                .collect(Collectors.toList());
    }

}
