package co.com.bancolombia.domain.entities.model.dtos.out.standard;

import co.com.bancolombia.domain.entities.model.dtos.out.create.PersonResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;
import java.util.Map;

@Getter
@Setter
public class Data {

    private Map<String,Object> header;
    private Object body;

}
