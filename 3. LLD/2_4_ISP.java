//WRONG

// BAD EXAMPLE - Violates ISP
// Multi-function interface with all possible actions
interface MultiFunctionDevice {
    void print(String document);
    void fax(String document);
    void scan(String document);
    void copy(String document);
    void manageQueue();
    void checkTonerLevel();
}

// BasicPrinter: Can only print
class BasicPrinter implements MultiFunctionDevice {
    @Override
    public void print(String document) {
        System.out.println("BasicPrinter: Printing: " + document);
    }

    @Override
    public void fax(String document) {
        // This printer cannot fax. What to do?
        throw new UnsupportedOperationException("Fax not supported on this printer!");
        // Or leave empty, which is also bad (silent failure)
    }

    @Override
    public void scan(String document) {
        // This printer cannot scan.
        throw new UnsupportedOperationException("Scan not supported on this printer!");
    }

    @Override
    public void copy(String document) {
        // This printer cannot copy.
        throw new UnsupportedOperationException("Copy not supported on this printer!");
    }

    @Override
    public void manageQueue() {
        // Basic printers might not have an advanced queue system
        System.out.println("BasicPrinter: Simple queue management.");
    }

    @Override
    public void checkTonerLevel() {
        System.out.println("BasicPrinter: Checking toner.");
    }
}

// Client Code (e.g., a simple print utility)
class SimplePrintClient {
    public void doSimplePrint(MultiFunctionDevice device, String doc) {
        device.print(doc);
        // This client doesn't need fax, scan, copy, etc.
        // But it's forced to depend on an interface that includes them.
        // If it accidentally calls device.fax(doc), it gets an exception.
    }
}

public class MainBadISP {
    public static void main(String[] args) {
        BasicPrinter basicPrinter = new BasicPrinter();
        SimplePrintClient client = new SimplePrintClient();

        client.doSimplePrint(basicPrinter, "My simple document");

        // Attempting to call an unsupported method will crash:
        try {
            basicPrinter.fax("Fax to Bob");
        } catch (UnsupportedOperationException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}





//CORRECT

// GOOD EXAMPLE - ISP Compliant

// 1. Core Printing Functionality
interface IPrinter {
    void print(String document);
}

// 2. Fax Functionality
interface IFax {
    void fax(String document);
}

// 3. Scanner Functionality
interface IScanner {
    void scan(String document);
}

// 4. Copier Functionality (Can be built from Scanner + Printer)
interface ICopier extends IScanner, IPrinter { // Combines scanner and printer
    void copy(String document);
}

// 5. Printer Management Functionality
interface IPrinterManagement {
    void manageQueue();
    void checkTonerLevel();
}

// Concrete Implementations:

// BasicPrinter: Only implements what it can do
class BasicPrinter implements IPrinter, IPrinterManagement {
    @Override
    public void print(String document) {
        System.out.println("BasicPrinter: Printing: " + document);
    }

    @Override
    public void manageQueue() {
        System.out.println("BasicPrinter: Simple queue management.");
    }

    @Override
    public void checkTonerLevel() {
        System.out.println("BasicPrinter: Checking toner.");
    }
}

// AdvancedMultiFunctionPrinter: Implements multiple interfaces
class AdvancedMultiFunctionPrinter implements IPrinter, IFax, IScanner, ICopier, IPrinterManagement {
    // Note: ICopier already extends IScanner and IPrinter, so no need to explicitly list them again.
    // However, for clarity in this example, listing them helps.
    // In actual code, just implementing ICopier would also cover IPrinter and IScanner.

    @Override
    public void print(String document) {
        System.out.println("AdvancedPrinter: Printing: " + document);
    }

    @Override
    public void fax(String document) {
        System.out.println("AdvancedPrinter: Faxing: " + document);
    }

    @Override
    public void scan(String document) {
        System.out.println("AdvancedPrinter: Scanning: " + document);
    }

    @Override
    public void copy(String document) {
        // Copy functionality internally uses scan and print
        System.out.println("AdvancedPrinter: Copying: " + document);
        scan(document);
        print(document);
    }

    @Override
    public void manageQueue() {
        System.out.println("AdvancedPrinter: Advanced queue management.");
    }

    @Override
    public void checkTonerLevel() {
        System.out.println("AdvancedPrinter: Checking toner and other diagnostics.");
    }
}

// Client Code (Each client depends only on the interfaces it needs)

// Client that only needs to print
class PrintOnlyClient {
    public void executePrint(IPrinter printer, String doc) {
        printer.print(doc);
    }
}

// Client that needs to scan
class ScanOnlyClient {
    public void executeScan(IScanner scanner, String doc) {
        scanner.scan(doc);
    }
}

// Client that needs advanced management features
class AdminClient {
    public void checkPrinterHealth(IPrinterManagement management) {
        management.checkTonerLevel();
        management.manageQueue();
    }
}

public class MainGoodISP {
    public static void main(String[] args) {
        PrintOnlyClient printClient = new PrintOnlyClient();
        ScanOnlyClient scanClient = new ScanOnlyClient();
        AdminClient adminClient = new AdminClient();

        BasicPrinter basicPrinter = new BasicPrinter();
        AdvancedMultiFunctionPrinter multiFunctionPrinter = new AdvancedMultiFunctionPrinter();

        System.out.println("--- Using Basic Printer ---");
        printClient.executePrint(basicPrinter, "Basic Document A"); // Works
        // scanClient.executeScan(basicPrinter, "Image A"); // Compile error: BasicPrinter is not an IScanner! (Good!)
        adminClient.checkPrinterHealth(basicPrinter); // Works

        System.out.println("\n--- Using Advanced Multi-Function Printer ---");
        printClient.executePrint(multiFunctionPrinter, "Advanced Document B"); // Works
        scanClient.executeScan(multiFunctionPrinter, "Image B"); // Works
        multiFunctionPrinter.fax("Fax to Mary"); // Direct call, multi-function can do it
        multiFunctionPrinter.copy("Document C for Copy"); // Direct call
        adminClient.checkPrinterHealth(multiFunctionPrinter); // Works
    }
}
