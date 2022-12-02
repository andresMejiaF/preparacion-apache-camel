package co.com.bancolombia.infracstructure.adapters.driven;

import co.com.bancolombia.domain.entities.model.dtos.in.PersonRequest;
import co.com.bancolombia.domain.usecases.processors.ResponseProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileAdapter extends RouteBuilder{

	@Autowired
	private BuildLineProcessor buildLineProcessor;

	@Override
	public void configure() throws Exception {
				
		from("direct:SavePersonToFile").routeId("savePersonToFile") // llegada, primer endpoint, // id de la ruta
				.process(buildLineProcessor).id("buildLineProcessor") // construye una linea, segundo endpoint que es un processor
				.to("file://target/routetofile?fileExist=Append&appendChars=\n").id("toWriteFile") // envia al archivo, tercer endpoint que escribe en el archivo
				.log("${body}")
				.process(new ResponseProcessor()).id("responseProcessor")// ultimo endpoint que corresponde a un procces
				.marshal().json();// conversion de mensajes
		
	}



}
