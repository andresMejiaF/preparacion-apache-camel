package process;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessDataResponseWSRest implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        //System.out.println("Body -> "+ exchange.getIn().getBody(String.class));

        DataUser dataUser = exchange.getIn().getBody(DataUser.class);
        if(dataUser != null){
            System.out.println("Datauser ready");
         //   System.out.println("dataUser = " + dataUser.toString());

        }else{
            System.out.println("DataUser paila");
        }
    }
}
