package co.com.bancolombia.infracstructure.adapters.driven;

import co.com.bancolombia.domain.entities.model.dtos.in.PersonRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class BuildLineProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception { // processor del body de llegada
        PersonRequest inbody = exchange.getIn().getBody(PersonRequest.class); // trae el objeto
        String outbody = inbody.getNombre() +" "+inbody.getEdad()+" "+inbody.getGenero(); // en este sentido
        exchange.getIn().setBody(outbody); // toma la salida
    }
}
