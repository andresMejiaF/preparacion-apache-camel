package co.com.bancolombia.domain.usecases.processors;

import co.com.bancolombia.domain.entities.model.dtos.out.create.PersonResponse;
import co.com.bancolombia.domain.entities.model.dtos.out.standard.Data;
import co.com.bancolombia.domain.entities.model.dtos.out.standard.Links;
import co.com.bancolombia.domain.entities.model.dtos.out.standard.Meta;
import co.com.bancolombia.domain.entities.model.dtos.out.standard.RespnonseObject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Component
public class ResponseProcessor implements Processor {

    private final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public void process(Exchange exchange) throws Exception {
        String message = "el registro: ("+exchange.getMessage().getBody(String.class)
                +") fue creado exitosamente en el archivo " + exchange.getMessage().getHeader("CamelFileName");
        PersonResponse pr = new PersonResponse("message");

        RespnonseObject responseObject = new RespnonseObject();
        responseObject.setMeta(buildMeta(exchange));
        responseObject.setData(buildData(pr));

        exchange.getMessage().setBody(responseObject);

    }

    private Meta buildMeta(Exchange ex){
        Message message = ex.getMessage();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Meta meta = new Meta();
        meta.setMessageId(message.getHeader("message-id",String.class));
        meta.setApplicationId(message.getHeader("id-consumer", String.class));
        meta.setRequestDateTime(this.sdf2.format(timestamp));
        return meta;
    }

    private Data buildData(Object obj){

        Data data = new Data();
        data.setBody(obj);
        data.setHeader(null);
        return data;
    }

    private Links buildLinks(){
        return null;
    }
}
