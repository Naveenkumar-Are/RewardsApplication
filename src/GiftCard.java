import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class GiftCard {

    private Scanner scan = new Scanner(System.in);
    private int id;
    private String name;
    private int points;

    private ReentrantLock lock = new ReentrantLock();
    private GiftCard[] giftCards;

    public void initializeGiftCards() {
        giftCards = new GiftCard[]{new GiftCard(0, "Headphones", 1000),
                new GiftCard(1, "travelCard", 200),
                new GiftCard(2, "Headphones", 30000),
                new GiftCard(3, "Car", 400000)};
    }

    public GiftCard() {
        initializeGiftCards();
    }

    public GiftCard(int id, String name, int points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

    //
//
    public void displayGiftCards() {

        for (GiftCard g : giftCards) {
            System.out.println(g.id + "\n" +
                    ":" + g.name + "\n" + "Points required to redeem" + g.points
            );
        }

    }

    public int getGiftCard(int i) {
        lock.lock();
        if (i < giftCards.length) {
            GiftCard element = giftCards[i];
            System.out.println(" required points to redeem " + element.name + " are " + element.points);
            return element.points;

        } else {

            System.out.println("Invalid input, Please enter numbers between 0 to 3");

        }
        lock.unlock();
        return -1;
    }

    public String getName(int i) {

        GiftCard card = giftCards[i];
     return card.name;

    }

}
