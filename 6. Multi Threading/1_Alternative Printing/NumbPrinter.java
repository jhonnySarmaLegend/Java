
package AlternativePrinting;

public  class NumbPrinter implements Runnable{
        @Override
        public void run(){
            System.out.println("Starting to print alphabets...");

            for(int i=1;i<=5;i++){
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

