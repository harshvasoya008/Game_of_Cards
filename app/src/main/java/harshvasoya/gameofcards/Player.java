package harshvasoya.gameofcards;

import java.util.ArrayList;

/**
 * Created by Harsh on 04-07-2016.
 */
public class Player {

    ArrayList<Card> hand;
    private String player_name;
    private int money=0;
    boolean isPlaying,isOut;

    public Player(String name, int amount){
        player_name=name;
        money=amount;
        hand = new ArrayList<>();
        isPlaying=true;
        isOut=false;
    }

    public int getMoney() {
        return money;
    }
    public void addMoney(int amt){
        money += amt;
    }
    public void deductMoney(int amt){
        money -= amt;
    }

    public String getPlayer_name(){ return player_name; }

    public void addCard(Card card){
        hand.add(card);
    }

    public void sortCards(){

        //sorting using selection sort
        ArrayList<Card> temp = new ArrayList<Card>();
        while(hand.size()!=0)
        {
            Card best=hand.get(0);
            for(int i=1;i<hand.size();i++)
                if(hand.get(i).getCardNum()>best.getCardNum())
                    best=hand.get(i);

            temp.add(best);
            hand.remove(best);
        }
        hand = temp;
    }

    public int getRank(){

        // 6. Three of a kind
        if (hand.get(0).getCardNum()==hand.get(1).getCardNum()
            && hand.get(0).getCardNum()==hand.get(2).getCardNum())
            return 5;

        // Straight
        else if (hand.get(0).getCardNum()-hand.get(1).getCardNum()==1
                && hand.get(1).getCardNum()-hand.get(2).getCardNum()==1){

            if (hand.get(0).getSuitNum()==hand.get(1).getSuitNum()
                    && hand.get(0).getSuitNum()==hand.get(2).getSuitNum())
                return 6; // 6. Straight Flush
            else
                return 4; // 4. Straight
        }

        // 3. Flush
        else if (hand.get(0).getSuitNum()==hand.get(1).getSuitNum()
                && hand.get(0).getSuitNum()==hand.get(2).getSuitNum())
            return 3;

        // 2. Pair
        else if (hand.get(0).getCardNum()==hand.get(1).getCardNum()
                || hand.get(1).getCardNum()==hand.get(2).getCardNum())
            return 2;

        // 1. High Card
        else
            return 1;
    }

    public void clearHand(){
        hand.clear();
    }
}
