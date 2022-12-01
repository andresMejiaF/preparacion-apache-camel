package co.com.bancolombia.routes;

import co.com.bancolombia.domain.entities.model.dtos.in.PersonRequest;
import co.com.bancolombia.infracstructure.adapters.driven.BuildLineProcessor;
import co.com.bancolombia.infracstructure.adapters.driven.FileAdapter;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class FileAdapterTest extends CamelTestSupport {

    @Autowired
    private BuildLineProcessor buildLineProcessor;
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        FileAdapter fileAdapter = new FileAdapter();
        buildLineProcessor = new BuildLineProcessor();
        return fileAdapter;
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @BeforeEach
    public void setupTestRoute() throws Exception {
        RouteDefinition route1 = context.getRouteDefinition("savePersonToFile");
        AdviceWith.adviceWith(route1, context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:testFileAdapter");
                weaveById("buildLineProcessor").replace().to("mock:buildLineProc");
                weaveById("toWriteFile").replace().to("mock:writeFile");
                weaveById("responseProcessor").replace().to("mock:respProc");
            }
        });
    }

    @Test
    public void testFileAdapter() throws Exception {
        context.start();
        MockEndpoint mockBuildLineProc = getMockEndpoint("mock:buildLineProc");
        MockEndpoint mockWriteFile = getMockEndpoint("mock:writeFile");
        MockEndpoint mockRespProc = getMockEndpoint("mock:respProc");

        mockBuildLineProc.expectedHeaderReceived("CamelFileName","mayores-de-edad.txt");
        mockBuildLineProc.whenAnyExchangeReceived(buildLineProcessor);

        mockWriteFile.expectedBodiesReceived("Arturo 40 M");

        PersonRequest personRequest = setupPersonRequest();
        template.send("direct:testFileAdapter", exchange -> {
            exchange.getIn().setHeader("CamelFileName","mayores-de-edad.txt");
            exchange.getIn().setBody(personRequest);
        });

        mockBuildLineProc.assertIsSatisfied();
        mockWriteFile.assertIsSatisfied();
        mockRespProc.assertIsSatisfied();
        context.stop();
    }

    private PersonRequest setupPersonRequest() {
        PersonRequest body = new PersonRequest();
        body.setId("1");
        body.setNombre("Arturo");
        body.setApellido("Cabrera");
        body.setEdad("40");
        body.setGenero("M");
        return body;
    }
}
