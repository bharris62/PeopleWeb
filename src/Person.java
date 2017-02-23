/**
 * Created by BHarris on 2/23/17.
 */
public class Person {
    int id;
    String first_name;
    String last_name;
    String email;
    String countyr;
    String ip_address;

    public Person(int id, String first_name, String last_name, String email, String countyr, String ip_address) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.countyr = countyr;
        this.ip_address = ip_address;
    }

    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountyr() {
        return countyr;
    }

    public void setCountyr(String countyr) {
        this.countyr = countyr;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
