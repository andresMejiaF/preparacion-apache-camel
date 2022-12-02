package co.com.bancolombia.infracstructure.adapters.driver;

import co.com.bancolombia.domain.entities.model.dtos.in.PersonRequest;
import co.com.bancolombia.infracstructure.properties.RestParams;
import co.com.bancolombia.infracstructure.properties.RestProperties;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapter extends RouteBuilder { // COntrolador de la peticion rest

    @Autowired
    private RestProperties restProperties;

    public void configure() throws Exception {

        restConfiguration()
                .component(restProperties.getComponent())
                .bindingMode(RestBindingMode.off)
                .enableCORS(restProperties.isEnableCORS())
                .clientRequestValidation(restProperties.isClientRequestValidation())
                .host(restProperties.getHost())
                .port(restProperties.getPort())
                .apiProperty("api.title", restProperties.getApiTitle())
                .apiProperty("api.version", restProperties.getApiVersion());

        rest(RestParams.BASE_PATH)
                .post(RestParams.SERVICE_PATH)
                .description(RestParams.SERVICE_DESC)
                .consumes("application/json")
                .produces("application/json")
                .param()
                    .name("accept")
                    .type(RestParamType.header)
                    //.dataType(Type)
                    .required(false)
                .endParam()

                .param()
                    .name("api-version")
                    .type(RestParamType.header)
                    //.dataType(Type.STRING)
                    .required(false)
                .endParam()

                .param()
                    .name("api-key")
                    .type(RestParamType.header)
                   // .dataType(Type.STRING)
                    .required(true)
                .endParam()

                .param()
                    .name("message-id")
                    .type(RestParamType.header)
                // .dataType(Type.STRING)
                    .required(true)
                .endParam()

                .param()
                    .name("authorization")
                    .type(RestParamType.header)
                //.dataType(Type.STRING)
                    .required(false)
                .endParam()

                .param()
                    .name("json-web-token")
                    .type(RestParamType.header)
                //.dataType(Type.STRING)
                    .required(false)
                .endParam()

                .param()
                    .name("id-consumer")
                    .type(RestParamType.header)
                //.dataType(Type.STRING)
                    .required(true)
                .endParam()

                .param()
                    .name("service-name")
                    .type(RestParamType.header)
                //.dataType(Type.STRING)
                    .required(true)
                .endParam()

                .param()
                    .name("service-operation")
                    .type(RestParamType.header)
                //.dataType(Type.STRING)
                    .required(false)
                .endParam()

                .param()
                .name("requestBody")
                    .type(RestParamType.body)
                    .required(true)
                .endParam()
                .route()
                .unmarshal().json(PersonRequest.class)
                .to("direct:savePersonByAge") // redirige
                .setHeader("CamelHttpResponseCode",constant("201"));

    }
}
