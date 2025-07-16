// Product Interface
public interface Animal {
    void speak();
}


// Concrete Products
public class Cat implements Animal {
    @Override
    public void speak() {
        System.out.println("Meow!");
    }
}

public class Dog implements Animal {
    @Override
    public void speak() {
        System.out.println("Woof!");
    }
}


//Factory Class
public class AnimalFactory {
    public static Animal getAnimal(String type) {
        if ("cat".equalsIgnoreCase(type)) {
            return new Cat();
        } else if ("dog".equalsIgnoreCase(type)) {
            return new Dog();
        }
        return null;
    }
}


//CLIENT
public class Main {
    public static void main(String[] args) {
        Animal cat = AnimalFactory.getAnimal("cat");
        Animal dog = AnimalFactory.getAnimal("dog");

        if (cat != null) cat.speak(); // Output: Meow!
        if (dog != null) dog.speak(); // Output: Woof!
    }
}
