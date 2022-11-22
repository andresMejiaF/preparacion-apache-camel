package process;

import model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SetDataExchangeProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        /*
        System.out.println("Body IN ="+ exchange.getIn().getBody());
        exchange.getOut().setBody("Body definido a partir de process");
        exchange.getOut().setHeader("Cabecera1", "Cabecera establecida en process");
         */
        /*
        Person person = new Person("Jaime", 34);
        exchange.getOut().setBody(person);
        */
        //pasando el objeto en cabecera
        Person person = new Person("Jaime", 34);
        exchange.getOut().setHeader("person", person);
    }
}
