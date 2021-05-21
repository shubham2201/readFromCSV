import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class PacketAuditDto {

    private String id;

    private Integer smartDnaState;

    private Double weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSmartDnaState() {
        return smartDnaState;
    }

    public void setSmartDnaState(Integer smartDnaState) {
        this.smartDnaState = smartDnaState;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
