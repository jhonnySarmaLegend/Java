//Below is a very simple “parking lot” example. A `Semaphore` with a fixed number of permits represents 
//available parking spots. Cars (threads) must acquire a permit to park and release it when they leave.


import java.util.concurrent.Semaphore;

public class ParkingLot {
    private static final int MAX_SPOTS = 3;
    // Semaphore with 3 permits: at most 3 cars can park at once
    private final Semaphore spots = new Semaphore(MAX_SPOTS);

    public void park(String car) {
        try {
            System.out.println(car + " is trying to park.");
            spots.acquire();                // wait if no spots available
            System.out.println(car + " parked.");
            
            // Simulate parking time
            Thread.sleep(1000);
            
            System.out.println(car + " is leaving.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            spots.release();                // free up a spot
        }
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot();
        // Create 5 cars but only 3 spots
        for (int i = 1; i <= 5; i++) {
            final String car = "Car " + i;
            new Thread(() -> lot.park(car)).start();
        }
    }
}
```
/*
**How it works**  
- `new Semaphore(3)`: 3 permits = 3 parking spots.  
- `acquire()`:  
  - If a spot is free, the car parks immediately.  
  - If all spots are taken, the thread blocks until someone calls `release()`.  
- `release()`: returns a permit, waking one waiting car.

**Benefits of using a Semaphore**  
- *Automatic blocking* instead of busy‐waiting loops.  
- *Resource counting*: easily limit the number of concurrent users of any resource.  
- *Simplicity*: `acquire()/release()` clearly model “take a spot” / “leave a spot.”  

Use this pattern whenever you need to throttle access to a fixed pool of resources (database connections, threads, licenses, etc.).*/
