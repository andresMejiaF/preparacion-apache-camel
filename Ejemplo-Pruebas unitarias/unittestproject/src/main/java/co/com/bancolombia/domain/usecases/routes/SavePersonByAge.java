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
				.when().simple("${body.edad} >= 18") // toma la edad del body y las evalua
					.setHeader("CamelFileName",constant("mayores-de-edad.txt"))
					.to("direct:SavePersonToFile").id("savePersonFileOlder")
				.otherwise() // sino cumple con el criterio
					.setHeader("CamelFileName",constant("menores-de-edad.txt"))
					.to("direct:SavePersonToFile").id("savePersonFileYounger")
			.end();

	}

}
