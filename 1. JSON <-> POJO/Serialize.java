// Serialize POJO to JSON String

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person("Alice", 30);

        // Serialize
        String jsonString = mapper.writeValueAsString(person);
        System.out.println(jsonString); // Output: {"name":"Alice","age":30}
    }
}
