import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class CreditCard extends Thread implements Bank{

    private Map<LocalDateTime, String> log1 = new TreeMap<>();
    private boolean successfulTransaction=true;
    private double balance= Integer.MAX_VALUE;

    private Random random= new Random();



    private ReentrantLock lock= new ReentrantLock();

    private Users[] users={new Users(1,"Venkat",1234),new Users(2,"Ram",1236),
                           new Users(3,"Sterin",9832),
                           new Users(4,"Krish",1267),
                           new Users(5,"Parth",2345)};


    public CreditCard(Users user) {

    }
    public CreditCard(){

    }
    //Generating random user
    public Users getRandomUser(){
        //Random number between 0-4
        int element = random.nextInt(5);
        return users[element];
    }

    // Overriden method from Bank interface
    @Override
    public void withdraw(int amount) {
        if(balance!=0| amount<balance){
            // Using reentrant lock for concurrency
            if (!lock.isLocked()) {
                lock.lock();
                this.balance -= amount;
                System.out.println(LocalDateTime.now() +" transaction completed - money deducted from your account :- $" + amount );
                log1.put(LocalDateTime.now()," Transaction completed $"+ amount +" current balance is "+ balance);
                lock.unlock(); // Releasing the lock
            }
        } else{
            System.out.println(LocalDateTime.now() +" :- "+"Your balance is insufficient"+ balance );
            log1.put(LocalDateTime.now()," Transaction not completed " +" current balance is "+ balance );
        }

    }

    @Override
    public void deposit(int amount) {
        if (!lock.isLocked()) {
            lock.lock();
            this.balance += amount;
            System.out.println(LocalDateTime.now() +" :- "+"Successfully deposited ! your current balance is" + balance);
            lock.unlock();
        }
    }

    @Override
    public void transaction()  {
      int element = random.nextInt(0,4);

       int i= random.nextInt(10001);
       lock.lock(); // Used Reentrant lock. So only one thread can process withdraw
       withdraw(i);
       if (i>=5000){
          int b= users[element].getPoints()+1000;
          users[element].setPoints(b);
          System.out.println( "Hey !"+ users[element].getName()+ " You got 1000 reward points for this transaction   \n Total points :- "+users[element].getPoints());
       }else if(i>=2500 & i< 5000){
           int b=users[element].getPoints()+500;
           users[element].setPoints(b);
           System.out.println("Hey !"+ users[element].getName()+ " You got 500 reward points for this transaction   \n Total points :- "+users[element].getPoints());
       }else {
           int b=users[element].getPoints()+50;
           users[element].setPoints(b);
           System.out.println("Hey !"+ users[element].getName()+ " You got 50 reward points for this transaction.  \n Total points :- "+ users[element].getPoints());
       }
       lock.unlock();
    }

    // Run method which is overriden from Thread class
    @Override
    public void run(){
        transaction();
        int i=random.nextInt(0,4);
        users[i].redeemGiftCard(i);
    }

}
