import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class GetPropertyValues {

        private String auditUrl;
        private InputStream inputStream;
        private String xConsumerProfile;
        private String xConsumerRole;
        private String xConsumerId;
        private String fileLocation;
        private String targetLocation;
        private Float latitude;
        private Float longitude;

        public void getPropValues() throws IOException {
            try {
                Properties prop = new Properties();
                String propFileName = "application.properties";
                inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
                auditUrl = prop.getProperty("audit.url");
                xConsumerProfile= prop.getProperty("x-consumer-profile");
                xConsumerRole = prop.getProperty("x-consumer-role");
                xConsumerId = prop.getProperty("x-consumer-id");
                fileLocation = prop.getProperty("file.location");
                targetLocation = prop.getProperty("target.location");
                latitude= Float.parseFloat(prop.getProperty("latitude"));
                longitude= Float.parseFloat(prop.getProperty("longitude"));
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
                inputStream.close();
            }
        }

        public String getAuditUrl(){
            return this.auditUrl;
        }

        public String getXConsumerProfile(){
            return this.xConsumerProfile;
        }

        public String getXConsumerRole(){
            return this.xConsumerRole;
        }

        public String getXConsumerId(){
            return this.xConsumerId;
        }

        public String getFileLocation(){
            return this.fileLocation;
        }

        public String getTargetLocation(){
            return this.targetLocation;
        }

        public Float getLatitude(){
            return this.latitude;
        }

        public Float getLongitude(){
            return this.longitude;
        }
    }