package harshvasoya.gameofcards;

/**
 * Created by Harsh on 09-07-2016.
 */
public class CPU_Player extends Player {

    int round=0;
    public CPU_Player(String name, int amount) {
        super(name, amount);
    }

    public String getTheBestDecision(int numOfPlayers){
        round++;
        switch (getRank()){
            case 1:
                if (numOfPlayers==2 && hand.get(0).getCardNum()>10)
                    return "show";
                return "fold";

            case 2:
                if (round<=2)
                    return "call";
                if (numOfPlayers==2 && hand.get(1).getCardNum()<=10)
                    return "show";
                if (round>4)
                    return "fold";
                return "call";

            case 3:
                if (round<=2)
                    return "call";
                if (numOfPlayers==2)
                    return "show";
                if (round>4)
                    return "fold";
                return "call";

            case 4:
                if (round<=2)
                    return "call";
                if (numOfPlayers==2)
                    return "show";
                return "call";

            case 5:
                if (round<=3)
                    return "call";
                if (round==4 && hand.get(0).getCardNum()<=8)
                    return "call";
                if (round==4 && hand.get(0).getCardNum()>8)
                    return "raise,50";
                if (numOfPlayers==2)
                    return "show";
                return "call";

            case 6:
                if (round<=2)
                    return "call";
                if (round==3)
                    return "raise,50";
                if (round==4)
                    return "call";
                if (round==5)
                    return "raise,100";
                return "call";

            default: return null;
        }
    }

    @Override
    public void clearHand() {
        hand.clear();
        round=0;
    }
}
