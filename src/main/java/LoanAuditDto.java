import java.util.List;

public class LoanAuditDto {

    private String loanReqId;

    private Float latitude;

    private Float longitude;

    private List<LoanAuditReqDto> loans;

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getLoanReqId() {
        return loanReqId;
    }

    public void setLoanReqId(String loanReqId) {
        this.loanReqId = loanReqId;
    }

    public List<LoanAuditReqDto> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanAuditReqDto> loans) {
        this.loans = loans;
    }
}
