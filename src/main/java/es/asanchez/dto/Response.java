package es.asanchez.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class Response {

    public Response(int output) {
        this.output = output;
    }

    private Date date = new Date();
    private int output;
}
