//pojo
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Account {
    private String username;

    @JsonIgnore
    private String password; // Will NOT appear in JSON

    public Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

//code
public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = new Account("myuser", "mysecret");

        // Serialize
        String json = mapper.writeValueAsString(acc);
        System.out.println(json); // {"username":"myuser"}

        // Deserialize
        String jsonString = "{\"username\":\"demo\",\"password\":\"shouldIgnore\"}";
        Account acc2 = mapper.readValue(jsonString, Account.class);
        System.out.println(acc2.getPassword()); // null
    }
}
