package AlternativePrinting;

public  class AlphabetPrinter implements Runnable{
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
