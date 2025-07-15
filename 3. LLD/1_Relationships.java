/*

1. "is a" Relationship (Inheritance/Interface)
Definition:
Indicates inheritance (extends) or interface implementation (implements).
A subclass is a type of its superclass

*/

class Animal {
    void eat() {
        System.out.println("Animal eats.");
    }
}

// Dog is a Animal
class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks.");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();     // Dog is-a Animal
        d.eat();              // Inherited behavior
        d.bark();             // Own behavior
    }
}

//or with interface

interface Vehicle {
    void drive();
}

class Car implements Vehicle { // Car is-a Vehicle
    public void drive() {
        System.out.println("Car drives.");
    }
}






/*

2. "has a" Relationship (Composition/Aggregation)
Definition:
Represents that a class possesses another class as a member (field/property).
Implies composition or aggregation (part-whole relationship).

*/

class Engine {
    void start() {
        System.out.println("Engine starts.");
    }
}

class Car {
    private Engine engine;  // Car has-a Engine

    public Car() {
        engine = new Engine();
    }

    public void startCar() {
        engine.start();
        System.out.println("Car starts.");
    }
}

public class Main {
    public static void main(String[] args) {
        Car c = new Car();
        c.startCar(); // Outputs both engine and car starting
    }
}






/*

3. "uses a" Relationship (Association/Dependency)
Definition:
Means a class temporarily uses another class (typically as a method parameter or local variable).
Denotes dependency but not ownership.

*/

class Printer {
    void print(String text) {
        System.out.println(text);
    }
}

class Author {
    void writeBook(Printer printer) { // Author uses-a Printer
        printer.print("Book written by Author.");
    }
}

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Author author = new Author();
        author.writeBook(printer); // Author uses Printer to print
    }
}
