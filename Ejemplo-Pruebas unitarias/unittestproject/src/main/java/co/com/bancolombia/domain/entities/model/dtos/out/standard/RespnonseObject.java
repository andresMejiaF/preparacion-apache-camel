package co.com.bancolombia.domain.entities.model.dtos.out.standard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespnonseObject {
    private Meta meta;
    private Data data;
    private Links links;
}
