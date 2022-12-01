package co.com.bancolombia.domain.entities.model.dtos.out.standard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"_messageId", "_requestDateTime", "_applicationId"})
public class Meta {

    @JsonProperty("_messageId")
    private String messageId;
    @JsonProperty("_requestDateTime")
    private String requestDateTime;
    @JsonProperty("_applicationId")
    private String applicationId;

}
