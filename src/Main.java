import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

     for(int i=0;i<5;i++) {
         CreditCard t1 = new CreditCard();
         t1.start();
         try {
             Thread.sleep(100);
         }catch (InterruptedException e){
             System.out.println("Exception");
         }
     }

    }
}