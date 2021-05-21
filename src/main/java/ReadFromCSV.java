import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.net.URI;
import java.net.http.HttpClient;

import java.io.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFromCSV {

    private String auditUrl;

    private String xConsumerProfile;

    private String xConsumerRole;

    private String xConsumerId;

    private String fileLocation;

    private String targetLocation;

    private Float latitude;

    private Float longitude;


    private HttpResponse<String> asynchronousRequest(String targetUrl, String xConsumerProfile, String xConsumerRole, String xConsumerId, String data) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .setHeader("X-Consumer-Profile", xConsumerProfile)
                .setHeader("X-Consumer-Role",xConsumerRole)
                .setHeader("X-Consumer-Id",xConsumerId)
                .setHeader("Content-Type","application/json" )
                .build();

        return client.send(request,
                HttpResponse.BodyHandlers.ofString());

    }

    public HttpResponse<String> addAudits(String url, String xConsumerProfile, String xConsumerRole, String xConsumerId, LoanAuditDto dto) throws Exception {
        try {
            return asynchronousRequest(url, xConsumerProfile, xConsumerRole, xConsumerId, new ObjectMapper().writeValueAsString(dto));
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    public void parseCsv() throws Exception {
        readPropertiesFromFile();
        File file = new File(fileLocation);
        Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(targetLocation));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("loanId", "loanReqId", "packetId", "packetWeight", "smartDNAState","status code","message","status"));
        for (CSVRecord record : records) {
            LoanAuditDto dto = new LoanAuditDto();
            dto.setLoanReqId(record.get("loanReqId"));
            dto.setLatitude(latitude);
            dto.setLongitude(longitude);
            List<LoanAuditReqDto> loanAuditReqDtoList = new ArrayList<>();
            LoanAuditReqDto loanAuditReqDto = new LoanAuditReqDto();
            loanAuditReqDto.setId(record.get("loanId"));
            List<PacketAuditDto> packetAuditDtoList = new ArrayList<>();
            PacketAuditDto packetAuditDto = new PacketAuditDto();
            packetAuditDto.setId(record.get("packetId"));
            packetAuditDto.setSmartDnaState(Integer.parseInt(record.get("smartDNAState")));
            packetAuditDto.setWeight(Double.parseDouble(record.get("packetWeight")));
            packetAuditDtoList.add(packetAuditDto);
            loanAuditReqDto.setAssets(packetAuditDtoList);
            loanAuditReqDtoList.add(loanAuditReqDto);
            dto.setLoans(loanAuditReqDtoList);
            HttpResponse<String> response = addAudits(auditUrl, xConsumerProfile, xConsumerRole, xConsumerId, dto);
            System.out.println(response.body());
            csvPrinter.printRecord(record.get("loanId"), record.get("loanReqId"), record.get("packetId"), record.get("packetWeight"),record.get("smartDNAState"),response.statusCode(),response.body(),response.statusCode()/100==2?"Success":"Failure");
        }
        csvPrinter.flush();
    }

    private void readPropertiesFromFile() throws IOException{
        GetPropertyValues propertyValues = new GetPropertyValues();
        propertyValues.getPropValues();
        auditUrl = propertyValues.getAuditUrl();
        xConsumerProfile = propertyValues.getXConsumerProfile();
        xConsumerRole = propertyValues.getXConsumerRole();
        xConsumerId = propertyValues.getXConsumerId();
        fileLocation = propertyValues.getFileLocation();
        targetLocation = propertyValues.getTargetLocation();
        latitude = propertyValues.getLatitude();
        longitude = propertyValues.getLongitude();
    }

    public static void main(String[] args){
        try {
            new ReadFromCSV().parseCsv();
            //asynchronousRequest("http://");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
