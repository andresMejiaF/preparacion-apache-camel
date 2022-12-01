package co.com.bancolombia.domain.usecases.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SavePersonByAge extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("direct:savePersonByAge").routeId("savePersonByAge")
			.log("Im bussines logic")
			.choice()
				.when().simple("${body.edad} >= 18")
					.setHeader("CamelFileName",constant("mayores-de-edad.txt"))
					.to("direct:SavePersonToFile").id("savePersonFileOlder")
				.otherwise()
					.setHeader("CamelFileName",constant("menores-de-edad.txt"))
					.to("direct:SavePersonToFile").id("savePersonFileYounger")
			.end();

	}

}
