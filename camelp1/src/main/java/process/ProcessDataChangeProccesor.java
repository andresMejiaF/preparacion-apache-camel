package process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessDataChangeProccesor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Body In = " + exchange.getIn().getBody());
        System.out.println("Cabecera1 = " + exchange.getIn().getHeader("Cabecera1"));
    }
}
