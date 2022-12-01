package co.com.bancolombia.domain.entities.model.dtos.out.standard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Links {

    private String self;
    private String frist;
    private String prev;
    private String next;
    private String last;
}
