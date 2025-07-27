package AlternativePrinting;

public class MultiThreadedExample {

    public static class NumberPrinter implements Runnable{
        @Override
        public void run(){
            System.out.println("Starting to print numbers...");

            for(int i=1;i<=5;i++){
                System.out.println("Number : "+ i);
                
                try{
                    Thread.sleep(1000);// numbthread
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
             
            }
           
            System.out.println("Finished to print numbers...");
        }
    }

    public  static class AlphPrinter implements Runnable{
        @Override
        public void run(){
            System.out.println("Starting to print alphabets...");

            for(char i='a';i<='e';i++){
                System.out.println("Alphabets : "+ i);
                
                try{
                    Thread.sleep(1000); // alphThred
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
             
            }
           
            System.out.println("Finished to print alphabets...");
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Multi threaded process...");

       // if thread classes not inside main class --> no need to make the thread class static 
       // Thread numberThread=new Thread(new NumberPrinter());
       //  Thread alpThread=new Thread(new AlphabetPrinter());


       // if thread classes inside main class --> make the thread class static 
        Thread numberThread=new Thread(new NumberPrinter());
        Thread alpThread=new Thread(new AlphPrinter());

        numberThread.start();
        alpThread.start();

        try{
            numberThread.join();
            alpThread.join();
        }
        catch(InterruptedException e){
              e.printStackTrace();
        }

             try{
                    Thread.sleep(1000);// main Thread
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }

        System.out.println("Multithreaded process completed");
    }
}


