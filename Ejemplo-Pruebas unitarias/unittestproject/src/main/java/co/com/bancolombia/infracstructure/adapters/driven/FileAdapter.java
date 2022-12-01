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
				
		from("direct:SavePersonToFile").routeId("savePersonToFile")
				.process(buildLineProcessor).id("buildLineProcessor")
				.to("file://target/routetofile?fileExist=Append&appendChars=\n").id("toWriteFile")
				.log("${body}")
				.process(new ResponseProcessor()).id("responseProcessor")
				.marshal().json();
		
	}



}
