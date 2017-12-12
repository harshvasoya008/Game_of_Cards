package harshvasoya.gameofcards;

/**
 * Created by Harsh on 03-07-2016.
 */
public class Card {

    private static final int CLUBS = 1;
    private static final int DIAMONDS = 2;
    private static final int HEARTS = 3;
    private static final int SPADES = 4;

    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 14;

    private int card_num=0,suit_num=0;

    public Card(int suit,int card){
        card_num=card;
        suit_num=suit;
    }

    public int getSuitNum() {
        return suit_num;
    }

    public int getCardNum() {
        return card_num;
    }

    public String getSuitAsString()
    {
        switch(suit_num)
        {
            case SPADES: return "S";
            case CLUBS: return "C";
            case DIAMONDS: return "D";
            case HEARTS: return "H";
            default: return null;
        }
    }

    public String getNumAsString()
    {
        switch(card_num)
        {
            case 14: return "A";
            case 2: return "2";
            case 3: return "3";
            case 4: return "4";
            case 5: return "5";
            case 6: return "6";
            case 7: return "7";
            case 8: return "8";
            case 9: return "9";
            case 10: return "10";
            case 11: return "J";
            case 12: return "Q";
            case 13: return "K";
            default: return null;
        }
    }

    public String toString()
    {
        return getSuitAsString() + getNumAsString();
    }
}
