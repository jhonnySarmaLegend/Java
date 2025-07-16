//PET CLASS
public class Pet {
    private final String type; // Required
    private final String name; // Optional
    private final int age;     // Optional

    private Pet(Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.age = builder.age;
    }

    // Getters
    public String getType() { return type; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public void speak() {
        if ("cat".equalsIgnoreCase(type)) {
            System.out.println("Meow!");
        } else if ("dog".equalsIgnoreCase(type)) {
            System.out.println("Woof!");
        } else {
            System.out.println("Unknown sound!");
        }
    }

    // Builder Class
    public static class Builder {
        private final String type; // Required
        private String name;       // Optional
        private int age;           // Optional

        public Builder(String type) {
            this.type = type;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Pet build() {
            return new Pet(this);
        }
    }
}


//Main or Client class
public class Main {
    public static void main(String[] args) {
        // Building a dog with all parameters
        Pet dog = new Pet.Builder("dog")
                        .name("Buddy")
                        .age(5)
                        .build();

        dog.speak(); // Output: Woof!

        // Building a cat with only the type
        Pet cat = new Pet.Builder("cat")
                        .build();

        cat.speak(); // Output: Meow!
    }
}
