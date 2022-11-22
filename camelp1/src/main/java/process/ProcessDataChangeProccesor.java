package process;

import model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessDataChangeProccesor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        /*
        System.out.println("Body In = " + exchange.getIn().getBody());
        System.out.println("Cabecera1 = " + exchange.getIn().getHeader("Cabecera1"));
         */
        System.out.println("Body In = ");
       // Person person =  exchange.getIn().getBody(Person.class); // castea a persona
        Person person = exchange.getIn().getHeader("person", Person.class);
        if(person != null){
            System.out.println("Person name: " + person.getName());
        }else{
            System.out.println("Person NUll");
        }

      //  System.out.println("Cabecera1 = " + exchange.getIn().getHeader("Cabecera1"));
    }
}
