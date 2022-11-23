package org.acme.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import process.DataUser;
import process.ProcessDataChangeProccesor;
import process.ProcessDataResponseWSRest;
import process.SetDataExchangeProcessor;

public class MyRouteBuilder extends RouteBuilder {

    private JacksonDataFormat jacksonDataFormat;

    public MyRouteBuilder(){
        jacksonDataFormat= new JacksonDataFormat(DataUser.class);
    }

    @Override
    public void configure() throws Exception {

        /*
        from("timer:foo?period={{myPeriod}}")
            .bean("myBean", "hello")
            .log("${body}")
            .bean("myBean", "bye")
            .log("${body}");

         */
        /* Normal
        from("timer:simple?period=1000")
                .log("disparando procedimiento")
                .setHeader("Cabecera1", constant("valor cabecera 1"))
                .setBody(constant("Mensaje a procesar"))
                .to("direct:procesar")
                .end();
        */
        //Con process
        /*
        from("timer:simple?period=1000")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Body IN ="+ exchange.getIn().getBody());
                        exchange.getOut().setBody("Body definido a partir de process");
                        exchange.getOut().setHeader("Cabecera1", "Cabecera establecida en process");
                    }
                })
                .to("direct:procesar")
                .end();
        */
        //Con expresion lambda
        /*
        from("timer:simple?period=1000")
                .process(exchange -> {
                    System.out.println("Body IN ="+ exchange.getIn().getBody());
                    exchange.getOut().setBody("Body definido a partir de process");
                    exchange.getOut().setHeader("Cabecera1", "Cabecera establecida en process");
                })
                .to("direct:procesar")
                .end();
*/
        //Con clase alterna
        from("timer:simple?period=1000")
          //      .process(new SetDataExchangeProcessor())
            //    .to("direct:procesar")
                .to("direct:consumirWSRest")
                .end();
        /* Normal
        from("direct:procesar")
                .log("inicio procesamiento de mensaje")
                .log("Body = ${body}, Cabecera1 = ${header.Cabecera1}")
                .end();
        */
        //Con process
        /*
        from("direct:procesar")
                .log("inicio procesamiento de mensaje")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Body In = " + exchange.getIn().getBody());
                        System.out.println("Cabecera1 = " + exchange.getIn().getHeader("Cabecera1"));

                    }
                })
                .end();

         */
        //Process y lambda
        /*
        from("direct:procesar")
                .log("inicio procesamiento de mensaje")
                .process(exchange -> {
                    System.out.println("Body In = " + exchange.getIn().getBody());
                    System.out.println("Cabecera1 = " + exchange.getIn().getHeader("Cabecera1"));
                })
                .end();
                */
         //Con clase alterna
        from("direct:procesar")
                .log("inicio procesamiento de mensaje")
                .process(new ProcessDataChangeProccesor())
                .end();

        from("direct:consumirWSRest")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("https://jsonplaceholder.typicode.com/users/1")
                .unmarshal(jacksonDataFormat)
                .process(new ProcessDataResponseWSRest()).end();

    }
}
