//WRONG

// Rectangle.java (Base Class - implicitly assumes width and height can vary independently)
class Rectangle {
    protected double width;
    protected double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double area() {
        return width * height;
    }
}

// Square.java (Subtype - Violates LSP conceptually if used where Rectangle's independent dimensions are assumed)
class Square extends Rectangle {
    public Square(double side) {
        super(side, side); // A square is a rectangle with equal sides
    }

    // No explicit override of area() needed, but the underlying assumption is the problem.
    // The "violation" comes when a client assumes a Rectangle can have its dimensions changed independently
    // (even if no setters are present here, the conceptual model of a Rectangle allows it),
    // and then a Square is substituted, which cannot.
    // If we only have 'area()' then the violation is subtle but still there in a broader context.
    // However, to make it concrete in this simplified example, let's assume client code that might try
    // to treat it as a general rectangle for certain operations that assume independent dimensions.
}

public class MainLSPViolationAreaOnly {
    // A function that uses the Rectangle abstraction
    public static void printCalculatedArea(Rectangle r) {
        System.out.println("Calculated Area: " + r.area());
    }

    public static void main(String[] args) {
        System.out.println("--- Testing with Rectangle ---");
        Rectangle rect = new Rectangle(2, 3); // A 2x3 rectangle
        printCalculatedArea(rect); // Output: Calculated Area: 6.0 (Correct)

        System.out.println("\n--- Testing with Square ---");
        Square sq = new Square(4); // A 4x4 square
        printCalculatedArea(sq); // Output: Calculated Area: 16.0 (Correct)

        // The area calculation itself is fine here. The LSP violation in this
        // simplified 'area()' only example is subtle, focusing on the broader
        // "is-a" relationship and what it implies. A Square *is* a Rectangle
        // only if the Rectangle abstraction is limited to what a Square can do.
        // If the Rectangle abstraction implies independent width/height (which it usually does),
        // then a Square cannot fully substitute it.
    }
}




//CORRECT

// Common Abstraction
interface Shape {
    double area(); // All shapes must know how to calculate their own area
}

// Rectangle.java (Independent Class implementing Shape)
class Rect implements Shape { // Renamed to avoid confusion with the concept of a general rectangle
    protected double width;
    protected double height;

    public Rect(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}

// Square.java (Independent Class implementing Shape)
class Sq implements Shape { // Renamed for clarity
    protected double side;

    public Sq(double side) {
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }
}

public class MainLSPCorrectAreaOnly {
    // This function now expects a general Shape.
    // It only calls the 'area()' method, which is consistent across all implementations.
    public static void printShapeArea(Shape shape) {
        System.out.println("Area: " + shape.area());
    }

    public static void main(String[] args) {
        System.out.println("--- Testing with Rect ---");
        Rect rect = new Rect(2, 3);
        printShapeArea(rect); // Output: Area: 6.0

        System.out.println("\n--- Testing with Sq ---");
        Sq sq = new Sq(4);
        printShapeArea(sq); // Output: Area: 16.0

        System.out.println("\n--- Using polymorphic list ---");
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Rect(5, 10));
        shapes.add(new Sq(7));
        
        for (Shape shape : shapes) {
            printShapeArea(shape); // Works correctly and consistently for both
        }
    }
}
