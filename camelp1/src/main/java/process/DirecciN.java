
package process;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "calle",
    "suite",
    "ciudad",
    "c\u00f3digo postal",
    "geo"
})
@Generated("jsonschema2pojo")
public class DirecciN {

    @JsonProperty("calle")
    private String calle;
    @JsonProperty("suite")
    private String suite;
    @JsonProperty("ciudad")
    private String ciudad;
    @JsonProperty("c\u00f3digo postal")
    private String cDigoPostal;
    @JsonProperty("geo")
    private Geo geo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("calle")
    public String getCalle() {
        return calle;
    }

    @JsonProperty("calle")
    public void setCalle(String calle) {
        this.calle = calle;
    }

    @JsonProperty("suite")
    public String getSuite() {
        return suite;
    }

    @JsonProperty("suite")
    public void setSuite(String suite) {
        this.suite = suite;
    }

    @JsonProperty("ciudad")
    public String getCiudad() {
        return ciudad;
    }

    @JsonProperty("ciudad")
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @JsonProperty("c\u00f3digo postal")
    public String getcDigoPostal() {
        return cDigoPostal;
    }

    @JsonProperty("c\u00f3digo postal")
    public void setcDigoPostal(String cDigoPostal) {
        this.cDigoPostal = cDigoPostal;
    }

    @JsonProperty("geo")
    public Geo getGeo() {
        return geo;
    }

    @JsonProperty("geo")
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
