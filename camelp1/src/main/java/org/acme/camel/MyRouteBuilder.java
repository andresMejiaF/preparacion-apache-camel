package org.acme.camel;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /*
        from("timer:foo?period={{myPeriod}}")
            .bean("myBean", "hello")
            .log("${body}")
            .bean("myBean", "bye")
            .log("${body}");

         */
        from("timer:simple?period=1000")
                .log("Procesando mensajes")
                .end();

    }
}
