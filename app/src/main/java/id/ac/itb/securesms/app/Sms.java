package id.ac.itb.securesms.app;

/**
 * Created by Rakhmatullah Yoga S on 17/04/2016.
 */
public class Sms {
    public static final String DELIMITER = "\n--\n";

    private String address, body, datetime;

    public Sms() {

    }

    public Sms(String address, String body, String datetime) {
        this.address = address;
        this.body = body;
        this.datetime = datetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
