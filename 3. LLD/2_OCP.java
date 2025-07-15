//WRONG

// Step 1: Initial shapes
class Circle {
    private double radius;
    public Circle(double radius) { this.radius = radius; }
    public double getRadius() { return radius; }
}

class Rectangle {
    private double width;
    private double height;
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}

// AreaCalculator - VIOLATES OCP
class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return Math.PI * circle.getRadius() * circle.getRadius();
        } else if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            return rectangle.getWidth() * rectangle.getHeight();
        }
        // --- PROBLEM: If you add Triangle, you MUST modify this method ---
        throw new IllegalArgumentException("Unknown shape type!");
    }
}

public class MainBadOCP {
    public static void main(String[] args) {
        AreaCalculator calculator = new AreaCalculator();

        Circle circle = new Circle(5);
        Rectangle rectangle = new Rectangle(4, 6);

        System.out.println("Circle Area: " + calculator.calculateArea(circle));
        System.out.println("Rectangle Area: " + calculator.calculateArea(rectangle));

        // Now, let's say we want to add a Triangle class...
        // This 'MainBadOCP' class wouldn't run a Triangle directly
        // because AreaCalculator.calculateArea doesn't know about Triangle yet.
    }
}


//CORRECT

// Step 1: Define an abstraction for shapes
interface Shape {
    double area(); // All shapes must know how to calculate their own area
}

// Step 2: Implement shapes based on the abstraction
class Circle implements Shape {
    private double radius;
    public Circle(double radius) { this.radius = radius; }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private double width;
    private double height;
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}

// AreaCalculator - OCP COMPLIANT
class AreaCalculator {
    // This method is now CLOSED FOR MODIFICATION
    public double calculateArea(Shape shape) { // Works with the abstraction
        return shape.area(); // Delegates the calculation to the shape itself
    }

    // You could also calculate total area of many shapes
    public double calculateTotalArea(Shape[] shapes) {
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.area(); // Works with the abstraction
        }
        return totalArea;
    }
}

public class MainGoodOCPInitial {
    public static void main(String[] args) {
        AreaCalculator calculator = new AreaCalculator();

        Circle circle = new Circle(5);
        Rectangle rectangle = new Rectangle(4, 6);

        System.out.println("Circle Area: " + calculator.calculateArea(circle));
        System.out.println("Rectangle Area: " + calculator.calculateArea(rectangle));

        Shape[] shapes = {circle, rectangle};
        System.out.println("Total Area: " + calculator.calculateTotalArea(shapes));
    }
}
