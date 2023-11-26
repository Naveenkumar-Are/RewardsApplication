import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Users {
    private int id;
    private String name;
    private int points;
    private int creditCardNumber;
    private GiftCard giftCard = new GiftCard();
    private Random random = new Random();

    private ReentrantLock lock = new ReentrantLock();

    public Users(int id, String name, int creditCardNumber) {
        this.id = id;
        this.name = name;
        this.creditCardNumber = creditCardNumber;
    }

    private Map<LocalDateTime, String> log = new TreeMap<>();

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized int getPoints() {
        return points;
    }


    public void setPoints(int points) {
        if (!lock.isLocked()) {
            lock.lock();
            this.points = points;
            lock.unlock();
        }
    }

   //Adding logs
    public Map<LocalDateTime, String> getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                '}';
    }


    // This method helps to redeem the giftcards
    public void redeemGiftCard(int i) {

        if (giftCard.getGiftCard(i) != -1 & !(this.points == 0)) {
            int redeemedPoints = giftCard.getGiftCard(i);
            if (this.points >= giftCard.getGiftCard(i)) {
                //Checking if reentrant lock is acquired or not. If not locked then redeeming the points
                if (!lock.isLocked()) {
                    lock.lock();
                    this.points -= redeemedPoints;
                    System.out.println( LocalDateTime.now() +" :- "+ this.name + " Congratulations you redeemed" + giftCard.getName(i) + " Remaining points are " + points);
                    log.put(LocalDateTime.now(), "you redeemed" + giftCard.getName(i) + " Remaining points are " + points);
                    lock.unlock();
                }
            } else {
                System.out.println(LocalDateTime.now() +" :- "+"hey " + this.name + " Sorry Insufficient points to redeem " + giftCard.getName(i) + " your total points are " + points);
                log.put(LocalDateTime.now(), "Declined due to insufficient " + giftCard.getName(i) + " Remaining points are " + points);

            }

        }
    }
}