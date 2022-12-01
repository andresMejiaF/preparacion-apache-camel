package co.com.bancolombia.infracstructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class RestProperties {

    @Value("${restconfig.component}")
    private String component;
    @Value("${restconfig.cors.enabled}")
    private boolean enableCORS;
    @Value("${restconfig.client.request.validation}")
    private boolean clientRequestValidation;
    @Value("${restconfig.host}")
    private String host;
    @Value("${restconfig.port}")
    private String port;
    @Value("${restconfig.api-property.title}")
    private String apiTitle;
    @Value("${restconfig.api-property.version}")
    private String apiVersion;
}
