package harshvasoya.gameofcards;

import java.util.ArrayList;

/**
 * Created by Harsh on 04-07-2016.
 */
public class Deck {

    private Card[] cardDeck;
    private int top_card=0;

    public Deck(){
        cardDeck = new Card[52];
        int count=0;
        for(int i=1;i<=4;i++)
            for(int j=2;j<=14;j++)
                cardDeck[count++] = new Card(i,j);
        shuffle();
    }

    public void shuffle() {
        for (int i = cardDeck.length - 1; i > 0; i--) {
            int rand = (int) (Math.random() * (i + 1));
            Card temp = cardDeck[i];
            cardDeck[i] = cardDeck[rand];
            cardDeck[rand] = temp;
        }
    }

    public Card dealCard(){
        return cardDeck[top_card++];
    }
}
