package co.com.bancolombia.domain.entities.model.dtos.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequest {

    private String id;
    private String nombre;
    private String apellido;
    private String edad;
    private String genero;
}
