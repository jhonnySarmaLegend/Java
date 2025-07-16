// PET CLASS
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Pet {
    private String type; // Required
    private String name; // Optional
    private int age;     // Optional

    public void speak() {
        if ("cat".equalsIgnoreCase(type)) {
            System.out.println("Meow!");
        } else if ("dog".equalsIgnoreCase(type)) {
            System.out.println("Woof!");
        } else {
            System.out.println("Unknown sound!");
        }
    }
}


// Client or main class
public class Main {
    public static void main(String[] args) {
        // Build a dog with all parameters
        Pet dog = Pet.builder()
            .type("dog")
            .name("Buddy")
            .age(5)
            .build();

        dog.speak(); // Output: Woof!

        // Build a cat with only the type
        Pet cat = Pet.builder()
            .type("cat")
            .build();

        cat.speak(); // Output: Meow!
    }
}
