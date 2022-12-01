package co.com.bancolombia.infracstructure.configuration;

import co.com.bancolombia.integracion.camel.processors.LogEventProcessorHeaders;
import co.com.bancolombia.integracion.camel.routes.KafkaLogEventRoute;
import co.com.bancolombia.integracion.processors.logevent.LogEventProcessorTransactionalHeaders;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig extends RouteBuilder {
    @Autowired
    private CamelContext camelContext;

    @Value("${service.internal.application}")
    private String application;

    @Value( "${service.internal.executionEnvironment}")
    private String executionEnvironment;

    @Value("${service.internal.environment}")
    private String environment;

    @Value("${service.internal.integrationPlatform}")
    private String integrationPlatform;

    private static final String EVENT_LEVEL_INFO = "INFO";

    private static final String EVENT_TYPE_REQUEST = "REQUEST";

    private static final String EVENT_TYPE_RESPONSE = "RESPONSE";

    private static final String EVENT_TYPE_ERROR = "ERROR";

    @Value("${integracion.kafka.topic}")
    private String kafkaTopic;

    @Value("${integracion.kafka.brokers}")
    private String kafkaBroker;

    @Value("${key-managers.key-store.resource}")
    private String kafkaJKSPath;

    @Value("${key-store.password}")
    private String kafkaJKSPass;

    public void configure() throws Exception {
        camelContext.addRoutes(new KafkaLogEventRoute());

        System.setProperty("integracion.kafka.topic", kafkaTopic);
        System.setProperty("integracion.kafka.brokers", kafkaBroker);
        System.setProperty("integracion.kafka.securityProtocol", "SSL");
        System.setProperty("integracion.kafka.sslEndpointAlgorithm", "");
        System.setProperty("integracion.kafka.sslTruststorePassword", kafkaJKSPass);
        System.setProperty("integracion.kafka.sslTruststoreLocation", kafkaJKSPath);
        System.setProperty("integracion.kafka.sslKeystorePassword", kafkaJKSPass);
        System.setProperty("integracion.kafka.sslKeystoreLocation", kafkaJKSPath);

        camelContext.addRoutes(new KafkaLogEventRoute());

        from("direct:loggingAdapterRequest")
                .setHeader(LogEventProcessorHeaders.MESSAGE_TYPE.getName(),
                        constant(LogEventProcessorHeaders.TRANSACTIONAL_MESSAGE.getName()))
                .setProperty(LogEventProcessorHeaders.EVENT_LEVEL.getName(), constant(EVENT_LEVEL_INFO))
                .setProperty(LogEventProcessorHeaders.EVENT_TYPE.getName(), constant(EVENT_TYPE_REQUEST))
                .setProperty(LogEventProcessorTransactionalHeaders.APPLICATION.getName(), constant(application))
                .setProperty(LogEventProcessorTransactionalHeaders.EXECUTION_ENVIRONMENT.getName(),
                        constant(executionEnvironment))
                .setProperty(LogEventProcessorTransactionalHeaders.ENVIRONMENT.getName(), constant(environment))
                .setProperty(LogEventProcessorTransactionalHeaders.INTEGRATION_PLATFORM.getName(),
                        constant(integrationPlatform))
                .to("direct:kafka-log-event");

        from("direct:loggingAdapterResponse")
                .setHeader(LogEventProcessorHeaders.MESSAGE_TYPE.getName(),
                        constant(LogEventProcessorHeaders.TRANSACTIONAL_MESSAGE.getName()))
                .setProperty(LogEventProcessorHeaders.EVENT_LEVEL.getName(), constant(EVENT_LEVEL_INFO))
                .setProperty(LogEventProcessorHeaders.EVENT_TYPE.getName(), constant(EVENT_TYPE_RESPONSE))
                .setProperty(LogEventProcessorTransactionalHeaders.APPLICATION.getName(), constant(application))
                .setProperty(LogEventProcessorTransactionalHeaders.EXECUTION_ENVIRONMENT.getName(),
                        constant(executionEnvironment))
                .setProperty(LogEventProcessorTransactionalHeaders.ENVIRONMENT.getName(), constant(environment))
                .setProperty(LogEventProcessorTransactionalHeaders.INTEGRATION_PLATFORM.getName(),
                        constant(integrationPlatform))
                .to("direct:kafka-log-event");

        from("direct:loggingAdapterError")
                .setHeader(LogEventProcessorHeaders.MESSAGE_TYPE.getName(),
                        constant(LogEventProcessorHeaders.TRANSACTIONAL_MESSAGE.getName()))
                .setProperty(LogEventProcessorHeaders.EVENT_LEVEL.getName(), constant(EVENT_LEVEL_INFO))
                .setProperty(LogEventProcessorHeaders.EVENT_TYPE.getName(), constant(EVENT_TYPE_ERROR))
                .setProperty(LogEventProcessorTransactionalHeaders.APPLICATION.getName(), constant(application))
                .setProperty(LogEventProcessorTransactionalHeaders.EXECUTION_ENVIRONMENT.getName(),
                        constant(executionEnvironment))
                .setProperty(LogEventProcessorTransactionalHeaders.ENVIRONMENT.getName(), constant(environment))
                .setProperty(LogEventProcessorTransactionalHeaders.INTEGRATION_PLATFORM.getName(),
                        constant(integrationPlatform))
                .to("direct:kafka-log-event");
    }
}
