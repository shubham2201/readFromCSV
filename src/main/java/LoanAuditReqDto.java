import java.util.List;

public class LoanAuditReqDto {

    private String id;

    private List<PacketAuditDto> assets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PacketAuditDto> getAssets() {
        return assets;
    }

    public void setAssets(List<PacketAuditDto> assets) {
        this.assets = assets;
    }
}
