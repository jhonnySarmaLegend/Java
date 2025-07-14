public class Person {
    private String name;
    private int age;

    // Default constructor—needed for deserialization!
    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // Optional: toString() for easy printing
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
