
package process;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "identificaci\u00f3n",
    "nombre",
    "nombre de usuario",
    "correo electr\u00f3nico",
    "Direcci\u00f3n",
    "tel\u00e9fono",
    "sitio web",
    "empresa",
    "name",
    "username"
})
@Generated("jsonschema2pojo")
public class DataUser {

    @JsonProperty("identificaci\u00f3n")
    private Integer identificaciN;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("nombre de usuario")
    private String nombreDeUsuario;
    @JsonProperty("correo electr\u00f3nico")
    private String correoElectrNico;
    @JsonProperty("Direcci\u00f3n")
    private DirecciN direcciN;
    @JsonProperty("tel\u00e9fono")
    private String telFono;
    @JsonProperty("sitio web")
    private String sitioWeb;
    @JsonProperty("empresa")
    private Empresa empresa;
    @JsonProperty("name")
    private String name;
    @JsonProperty("username")
    private String username;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("identificaci\u00f3n")
    public Integer getIdentificaciN() {
        return identificaciN;
    }

    @JsonProperty("identificaci\u00f3n")
    public void setIdentificaciN(Integer identificaciN) {
        this.identificaciN = identificaciN;
    }

    @JsonProperty("nombre")
    public String getNombre() {
        return nombre;
    }

    @JsonProperty("nombre")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @JsonProperty("nombre de usuario")
    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    @JsonProperty("nombre de usuario")
    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    @JsonProperty("correo electr\u00f3nico")
    public String getCorreoElectrNico() {
        return correoElectrNico;
    }

    @JsonProperty("correo electr\u00f3nico")
    public void setCorreoElectrNico(String correoElectrNico) {
        this.correoElectrNico = correoElectrNico;
    }

    @JsonProperty("Direcci\u00f3n")
    public DirecciN getDirecciN() {
        return direcciN;
    }

    @JsonProperty("Direcci\u00f3n")
    public void setDirecciN(DirecciN direcciN) {
        this.direcciN = direcciN;
    }

    @JsonProperty("tel\u00e9fono")
    public String getTelFono() {
        return telFono;
    }

    @JsonProperty("tel\u00e9fono")
    public void setTelFono(String telFono) {
        this.telFono = telFono;
    }

    @JsonProperty("sitio web")
    public String getSitioWeb() {
        return sitioWeb;
    }

    @JsonProperty("sitio web")
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    @JsonProperty("empresa")
    public Empresa getEmpresa() {
        return empresa;
    }

    @JsonProperty("empresa")
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "DataUser{" +
                "identificaciN=" + identificaciN +
                ", nombre='" + nombre + '\'' +
                ", nombreDeUsuario='" + nombreDeUsuario + '\'' +
                ", correoElectrNico='" + correoElectrNico + '\'' +
                ", direcciN=" + direcciN +
                ", telFono='" + telFono + '\'' +
                ", sitioWeb='" + sitioWeb + '\'' +
                ", empresa=" + empresa +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
