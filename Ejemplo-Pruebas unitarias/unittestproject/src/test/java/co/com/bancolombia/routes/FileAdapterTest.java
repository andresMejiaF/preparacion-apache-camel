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

    /**
     * 1. dejar todo listo para construir escenarios ( -> ruta a probar FileAdapter con su respectivo proccessor )
     * 2. etapa de los test donde se ejecuta como tal el test
     * 3. verificacion (test a probar que sean lo que esta probando)
     * 4. borrar datos (en camel no es necesario, dado que crea un contexto aparte sin interferir)
     */

    @Override  // fase de set-up
    protected RouteBuilder createRouteBuilder() throws Exception {
        FileAdapter fileAdapter = new FileAdapter();
        buildLineProcessor = new BuildLineProcessor(); // atributo del file Adapter
        //en algunas ocasiones el buildProccessor puede generar null pointer, es por ello que se puede
        // inicializar de la siguiente manera:
    //    ReflectionTestUtils.setField(fileAdapter, "buildLineProcessor", buildLineProcessor); esta es otra opcion
        //donde mockeamos el processor

        return fileAdapter; // retorno de la prueba a testear
    }

    @Override// etapa de test, met1
    public boolean isUseAdviceWith() {
        return true;
    }

    @BeforeEach
    public void setupTestRoute() throws Exception {  // Reemplaza o elimina endpoint de nuestra ruta
        RouteDefinition route1 = context.getRouteDefinition("savePersonToFile"); //toma la definicion de ruta, con el id de la ruta
        //De esta manera
        AdviceWith.adviceWith(route1, context, new AdviceWithRouteBuilder() { // ruta 1, contexto de prueba y advance

            /**
             * remplazo de endpoints relacionados en la ruta
             * @throws Exception
             */
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:testFileAdapter"); // remplaza el from con "direct:testFileAdapter" en lugar de "direct:SavePersonToFile"
                weaveById("buildLineProcessor").replace().to("mock:buildLineProc");// a partir del id remplazar para tener otro endpoint
                weaveById("toWriteFile").replace().to("mock:writeFile");
                //weaveById("responseProcessor").replace().to("mock:respProc");
                weaveById("responseProcessor").remove(); // cuando se remplaza no afecta entonces removemos

                //se puede remplazar elid, la uri, anexar al final, al inicio, etc

                //si no quiero probar puedo darle .remove() y elimino el endpoint
            }
        });
    }

    /**
     * Etapa del test a gusto del artista
     * es decir, como este diseñada
     */
    @Test
    public void testFileAdapter() throws Exception {
        context.start(); //context start por test
        //Los mochendpoint nos permite tomar en espacio y tiempo los mocks creados en el set up
        /**
         * los mock endpoints nos permite traer en espacio y tiempo los mocks que acabamos de crear en el set-up
         */
        PersonRequest personRequest = setupPersonRequest();
        MockEndpoint mockBuildLineProc = getMockEndpoint("mock:buildLineProc");//processor
        MockEndpoint mockWriteFile = getMockEndpoint("mock:writeFile");
       // MockEndpoint mockRespProc = getMockEndpoint("mock:respProc"); -> este mock valida lo anterior al endpoint
        //dado eso no es necesario agragralo

        //por cada endpoint lo que hacemos es validar que por cada uno obtiene lo que necesita
        /**
         * 1. en primer instancia necesitamos antes de llegar a la ruta validar el
         * svaPersonByAge (choice de la anterior rura para la validacion de mayor o menos de edad
         * ) y un in.Body() de personRequest, dado esto necesitamos un header que definio el choice y un body
         * personRequest
         *
         *
         *
         */

        /**
         * Esperamos para el primer endpoint un header
         * y un body de tipo personRequest
         */

        mockBuildLineProc.expectedBodiesReceived(personRequest);
        mockBuildLineProc.expectedHeaderReceived("CamelFileName","mayores-de-edad.txt"); // espera en el header (camelFileName)
        // y con valor mayores-de-edad-.txt
        mockBuildLineProc.whenAnyExchangeReceived(buildLineProcessor); // cuando cualquier exchange reciba se envie a un proccesor


        //el endpoint que espera recibir el archivo espera un body de tipo "Arturo 40 M"; nombre, edad, genero
        mockWriteFile.expectedBodiesReceived("Arturo 40 M"); // este es el archivo que espera recibir

        /**
         * el template.send envia un exchange a un endpoint
         * en este caso al "direct:testFileAdapter", el cual
         * salio del remplazo del set-up
         */

        template.send("direct:testFileAdapter", exchange -> {
            exchange.getIn().setHeader("CamelFileName","mayores-de-edad.txt"); //envia el header
            exchange.getIn().setBody(personRequest); // y envia el body mani
        });

        //Esto es validar que todo se cumpla
        mockBuildLineProc.assertIsSatisfied();
        mockWriteFile.assertIsSatisfied();
      //  mockRespProc.assertIsSatisfied();
        context.stop(); // context stop, cierre de contexto
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
