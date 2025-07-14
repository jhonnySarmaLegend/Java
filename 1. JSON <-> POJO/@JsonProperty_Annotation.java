// pojo class
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
    @JsonProperty("name") // Will use "name" in JSON instead of "fullName"
    private String fullName;

    private int age;

    public User() {}

    public User(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}

// code
public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("John Doe", 28);

        // Serialize
        String json = mapper.writeValueAsString(user);
        System.out.println(json); // {"name":"John Doe","age":28}

        // Deserialize
        String jsonString = "{\"name\":\"Jane Doe\",\"age\":32}";
        User desUser = mapper.readValue(jsonString, User.class);
        System.out.println(desUser.getFullName()); // Jane Doe
    }
}
