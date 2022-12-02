package co.com.bancolombia.routes;

import co.com.bancolombia.domain.entities.model.dtos.in.PersonRequest;
import co.com.bancolombia.domain.usecases.routes.SavePersonByAge;
import co.com.bancolombia.infracstructure.adapters.driven.BuildLineProcessor;
import co.com.bancolombia.infracstructure.adapters.driven.FileAdapter;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;

public class SavePersonByAgeTest  extends CamelTestSupport {

    @Override  // fase de set-up
    protected RouteBuilder createRouteBuilder() throws Exception {
        //Este no tiene parametros entonces no es ncesario
        return new SavePersonByAge();;
    }

    @Override// etapa de test, met1
    public boolean isUseAdviceWith() {
        return true;
    }

    @BeforeEach
    public void setupTestRoute() throws Exception{
        RouteDefinition route1 = context.getRouteDefinition("savePersonByAge");
        AdviceWith.adviceWith(route1, context, new AdviceWithRouteBuilder() { // ruta 1, contexto de prueba y advance

            /**
             * remplazo de endpoints relacionados en la ruta
             * @throws Exception
             */
            @Override
            public void configure() throws Exception { //el adviceWithRouteBuilder : remplaza los endpoints con el objetivo de ir probandolos

                replaceFromWith("direct:testSavePersonByAge"); // remplaza el from con "direct:testFileAdapter" en lugar de "direct:SavePersonToFile"
                weaveById("savePersonFileOlder").replace().to("mock:writeOlder");// a partir del id remplazar para tener otro endpoint
                weaveById("savePersonFileYounger").replace().to("mock:writeYounger"); // cuando se remplaza no afecta entonces removemos
            }
        });
    }

    @Test //traer la de Jupiter
    public void testSavePersonByAgeOlders() throws InterruptedException {
        context.start();
        MockEndpoint mockWhen= getMockEndpoint("mock:writeOlder");
        MockEndpoint mockOtherwise=  getMockEndpoint("mock:writeYounger");

        mockWhen.expectedHeaderReceived("CamelFileName", "mayores-de-edad.txt");
        mockWhen.expectedHeaderReceived("edad", "20" );
        mockWhen.expectedMinimumMessageCount(1);

        mockOtherwise.expectedMessageCount(0);

        template.send("direct:testSavePersonByAge", exchange -> {
            PersonRequest bodyEntrada= definePersonRequest("20");
            exchange.getIn().setBody(bodyEntrada);
        });

        mockWhen.assertIsSatisfied();
        mockOtherwise.assertIsSatisfied();

        context.stop();
    }

    @Test //traer la de Jupiter
    public void testSavePersonByAgeYounger() throws InterruptedException {
        context.start();
        MockEndpoint mockWhen= getMockEndpoint("mock:writeOlder");
        MockEndpoint mockOtherwise=  getMockEndpoint("mock:writeYounger");

        mockOtherwise.expectedHeaderReceived("CamelFileName", "menores-de-edad.txt");
        mockOtherwise.expectedHeaderReceived("edad", "17" );
        mockOtherwise.expectedMinimumMessageCount(1);

        mockWhen.expectedMessageCount(0);

        template.send("direct:testSavePersonByAge", exchange -> {
            PersonRequest bodyEntrada= definePersonRequest("17");
            exchange.getIn().setBody(bodyEntrada);
        });

        mockWhen.assertIsSatisfied();
        mockOtherwise.assertIsSatisfied();

        context.stop();
    }

    private  PersonRequest definePersonRequest( String edad) {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setGenero("M");
        personRequest.setNombre("Santiago");
        personRequest.setApellido("Mejia");
        personRequest.setEdad(edad);

        return personRequest;
    }
}
