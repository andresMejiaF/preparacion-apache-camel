package co.com.bancolombia.domain.entities.model.dtos.out.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonResponse {

    private String messsage;

    public PersonResponse(String messsage) {
        this.messsage = messsage;
    }
}
