// Deserialize JSON String to POJO

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"name\":\"Bob\",\"age\":25}";

        // Deserialize
        Person person = mapper.readValue(jsonString, Person.class);
        System.out.println(person); // Output: Person{name='Bob', age=25}
    }
}
