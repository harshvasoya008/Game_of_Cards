package harshvasoya.gameofcards;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class Table extends Activity {

    Deck deck;
    Player human;
    CPU_Player cpu1,cpu2,cpu3,cpu4;

    TextView name_cpu1,name_cpu2,name_cpu3,name_cpu4,name_human;
    TextView text_money_cpu1,text_money_cpu2,text_money_cpu3,text_money_cpu4;
    TextView text_human_money,text_pot;

    TextView cpu1_card1,cpu1_card2,cpu1_card3;
    TextView cpu2_card1,cpu2_card2,cpu2_card3;
    TextView cpu3_card1,cpu3_card2,cpu3_card3;
    TextView cpu4_card1,cpu4_card2,cpu4_card3;
    TextView player_card1,player_card2,player_card3;

    TableRow cards_cpu1,cards_cpu2,cards_cpu3,cards_cpu4,cards_human;

    Button deal;
    TableRow fn_bar;
    Button fold,show,call,raise,minus,plus;
    TextView text_raise_amt,text_call_amt;
    boolean isShowEnabled;

    TextToSpeech textToSpeech;

    int dablaPlayer;
    int pot,turn;
    int amt_dablu;
    int amt_raise, amt_call;

    ArrayList<Player> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_table);

        name_cpu1 = (TextView)findViewById(R.id.name_cpu1);
        name_cpu2 = (TextView)findViewById(R.id.name_cpu2);
        name_cpu3 = (TextView)findViewById(R.id.name_cpu3);
        name_cpu4 = (TextView)findViewById(R.id.name_cpu4);
        name_human = (TextView)findViewById(R.id.name_human);

        cards_cpu1 = (TableRow)findViewById(R.id.cards_cpu1);
        cards_cpu2 = (TableRow)findViewById(R.id.cards_cpu2);
        cards_cpu3 = (TableRow)findViewById(R.id.cards_cpu3);
        cards_cpu4 = (TableRow)findViewById(R.id.cards_cpu4);
        cards_human = (TableRow)findViewById(R.id.cards_human);

        player_card1 = (TextView)findViewById(R.id.human_card1);
        player_card2 = (TextView)findViewById(R.id.human_card2);
        player_card3 = (TextView)findViewById(R.id.human_card3);

        cpu1_card1 = (TextView)findViewById(R.id.cpu1_cardnum1);
        cpu1_card2 = (TextView)findViewById(R.id.cpu1_card2);
        cpu1_card3 = (TextView)findViewById(R.id.cpu1_card3);

        cpu2_card1 = (TextView)findViewById(R.id.cpu2_cardnum1);
        cpu2_card2 = (TextView)findViewById(R.id.cpu2_card2);
        cpu2_card3 = (TextView)findViewById(R.id.cpu2_card3);

        cpu3_card1 = (TextView)findViewById(R.id.cpu3_cardnum1);
        cpu3_card2 = (TextView)findViewById(R.id.cpu3_card2);
        cpu3_card3 = (TextView)findViewById(R.id.cpu3_card3);

        cpu4_card1 = (TextView)findViewById(R.id.cpu4_cardnum1);
        cpu4_card2 = (TextView)findViewById(R.id.cpu4_card2);
        cpu4_card3 = (TextView)findViewById(R.id.cpu4_card3);

        fn_bar = (TableRow)findViewById(R.id.fn_bar);
        fold = (Button)findViewById(R.id.btn_fold);
        show = (Button)findViewById(R.id.btn_show);
        call = (Button)findViewById(R.id.btn_call);
        raise = (Button)findViewById(R.id.btn_raise);
        minus = (Button)findViewById(R.id.btn_minus);
        plus = (Button)findViewById(R.id.btn_plus);
        text_raise_amt = (TextView)findViewById(R.id.txt_raise);
        text_call_amt = (TextView)findViewById(R.id.txt_call);

        text_money_cpu1 = (TextView)findViewById(R.id.txt_money_cpu1);
        text_money_cpu2 = (TextView)findViewById(R.id.txt_money_cpu2);
        text_money_cpu3 = (TextView)findViewById(R.id.txt_money_cpu3);
        text_money_cpu4 = (TextView)findViewById(R.id.txt_money_cpu4);
        text_human_money = (TextView)findViewById(R.id.txt_human_money);

        text_pot = (TextView)findViewById(R.id.pot);
        deal = (Button)findViewById(R.id.deal);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        playerList = new ArrayList<>();

        //<---------- Intialization ------------------>
        fn_bar.setVisibility(View.INVISIBLE);
        fn_bar.removeView(show);

        cards_human.setVisibility(View.INVISIBLE);
        cards_cpu1.setVisibility(View.INVISIBLE);
        cards_cpu2.setVisibility(View.INVISIBLE);
        cards_cpu3.setVisibility(View.INVISIBLE);
        cards_cpu4.setVisibility(View.INVISIBLE);

        text_money_cpu1.setText(String.valueOf(1000));
        text_money_cpu2.setText(String.valueOf(1000));
        text_money_cpu3.setText(String.valueOf(1000));
        text_money_cpu4.setText(String.valueOf(1000));
        text_human_money.setText(String.valueOf(1000));
        text_pot.setText("0");

        cpu1 = new CPU_Player("CPU1",1000);
        cpu2 = new CPU_Player("CPU2",1000);
        cpu3 = new CPU_Player("CPU3",1000);
        cpu4 = new CPU_Player("CPU4",1000);
        human = new Player("You",1000);

        pot=0;
        dablaPlayer = 4;
        amt_call=0;
        amt_raise=0;
        amt_dablu=50;

        deal.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                name_human.setBackground(getDrawable(R.color.black_overlay));
                name_cpu1.setBackground(getDrawable(R.color.black_overlay));
                name_cpu2.setBackground(getDrawable(R.color.black_overlay));
                name_cpu3.setBackground(getDrawable(R.color.black_overlay));
                name_cpu4.setBackground(getDrawable(R.color.black_overlay));

                cpu1.clearHand();
                cpu2.clearHand();
                cpu3.clearHand();
                cpu4.clearHand();
                human.clearHand();

                cpu1.isPlaying=true;
                cpu2.isPlaying=true;
                cpu3.isPlaying=true;
                cpu4.isPlaying=true;
                human.isPlaying=true;

                put_dablu();
                turn = (dablaPlayer+1)%5;
                playerList.clear();
                for (int i=1;i<=5;i++){
                    int num = (dablaPlayer+i)%5;
                    switch (num){
                        case 0: playerList.add(human); break;
                        case 1: if (!cpu1.isOut) playerList.add(cpu1); break;
                        case 2: if (!cpu2.isOut) playerList.add(cpu2); break;
                        case 3: if (!cpu3.isOut) playerList.add(cpu3); break;
                        case 4: if (!cpu4.isOut) playerList.add(cpu4); break;
                    }
                }

                pot = amt_dablu;
                amt_call = amt_dablu;
                amt_raise = amt_dablu+50;

                deck = new Deck();

                // dealing the cards...
                for (int i = 1; i <= 15; i++) {
                    switch (i%5){
                        case 0: human.addCard(deck.dealCard()); break;
                        case 1: cpu1.addCard(deck.dealCard()); break;
                        case 2: cpu2.addCard(deck.dealCard()); break;
                        case 3: cpu3.addCard(deck.dealCard()); break;
                        case 4: cpu4.addCard(deck.dealCard()); break;
                    }
                }

                cpu1.sortCards();
                cpu2.sortCards();
                cpu3.sortCards();
                cpu4.sortCards();
                human.sortCards();

                text_pot.setText(String.valueOf(pot));
                text_money_cpu1.setText(String.valueOf(cpu1.getMoney()));
                text_money_cpu2.setText(String.valueOf(cpu2.getMoney()));
                text_money_cpu3.setText(String.valueOf(cpu3.getMoney()));
                text_money_cpu4.setText(String.valueOf(cpu4.getMoney()));
                text_human_money.setText(String.valueOf(human.getMoney()));
                text_call_amt.setText(String.valueOf(amt_call));
                text_raise_amt.setText(String.valueOf(amt_raise));

                if (!cpu1.isOut) {
                    cpu1_card1.setText("??");
                    cpu1_card2.setText("??");
                    cpu1_card3.setText("??");
                }
                if (!cpu2.isOut) {
                    cpu2_card1.setText("??");
                    cpu2_card2.setText("??");
                    cpu2_card3.setText("??");
                }
                if (!cpu3.isOut) {
                    cpu3_card1.setText("??");
                    cpu3_card2.setText("??");
                    cpu3_card3.setText("??");
                }
                if (!cpu4.isOut) {
                    cpu4_card1.setText("??");
                    cpu4_card2.setText("??");
                    cpu4_card3.setText("??");
                }
                player_card1.setText(human.hand.get(0).toString());
                player_card2.setText(human.hand.get(1).toString());
                player_card3.setText(human.hand.get(2).toString());

                deal.setVisibility(View.INVISIBLE);
                cards_human.setVisibility(View.VISIBLE);
                if (!cpu1.isOut)    cards_cpu1.setVisibility(View.VISIBLE);
                if (!cpu2.isOut)    cards_cpu2.setVisibility(View.VISIBLE);
                if (!cpu3.isOut)    cards_cpu3.setVisibility(View.VISIBLE);
                if (!cpu4.isOut)    cards_cpu4.setVisibility(View.VISIBLE);

                if (playerList.get(0).equals(human)) {
                    fn_bar.setVisibility(View.VISIBLE);
                    name_human.setBackground(getDrawable(R.color.green));
                }
                else
                    playCPU(0);
            }
        });

        /***********************************************************/
        // <-------------- Now the Game Begins... ------------------>
        /***********************************************************/

        fold.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                fn_bar.setVisibility(View.INVISIBLE);
                cards_human.setVisibility(View.INVISIBLE);

                human.isPlaying=false;
               // textToSpeech.speak("fold",textToSpeech.QUEUE_FLUSH,null,null);

                name_human.setBackground(getDrawable(R.color.black_overlay));

                if (playerList.size()==2){
                    TwoPlayerFold(playerList.get( (playerList.indexOf(human)+1)%2 ));
                }
                else {
                    int num;
                    if (playerList.indexOf(human) == playerList.size() - 1)
                        num = 0;
                    else
                        num = playerList.indexOf(human);

                    playerList.remove(human);
                    playCPU(num);
                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                pot += (amt_call*2);        //pay twice the call amount to show
                text_pot.setText(String.valueOf(pot));
                human.deductMoney(amt_call * 2);
                text_human_money.setText(String.valueOf(human.getMoney()));

                fn_bar.setVisibility(View.INVISIBLE);
                //textToSpeech.speak("show",textToSpeech.QUEUE_FLUSH,null,null);

                showCPUCards();

                decideWinner();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                pot += amt_call;
                text_pot.setText(String.valueOf(pot));
                human.deductMoney(amt_call);
                text_human_money.setText(String.valueOf(human.getMoney()));

                fn_bar.setVisibility(View.INVISIBLE);
                //textToSpeech.speak("call",textToSpeech.QUEUE_FLUSH,null,null);

                name_human.setBackground(getDrawable(R.color.black_overlay));
                int num = (playerList.indexOf(human)+1)%playerList.size();
                playCPU(num);
            }
        });
        raise.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                amt_raise = Integer.valueOf(text_raise_amt.getText().toString());
                amt_call = amt_raise;
                pot += amt_raise;
                human.deductMoney(amt_raise);
                amt_raise += 50;

                text_pot.setText(String.valueOf(pot));
                text_human_money.setText(String.valueOf(human.getMoney()));
                text_call_amt.setText(String.valueOf(amt_call));
                text_raise_amt.setText(String.valueOf(amt_raise));

                fn_bar.setVisibility(View.INVISIBLE);
                //textToSpeech.speak("raise",textToSpeech.QUEUE_FLUSH,null,null);

                name_human.setBackground(getDrawable(R.color.black_overlay));
                int num = (playerList.indexOf(human)+1)%playerList.size();
                playCPU(num);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amt2 = Integer.valueOf(text_raise_amt.getText().toString());
                if (amt2-50>amt_call)
                    amt2 -= 50;
                text_raise_amt.setText(String.valueOf(amt2));
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amt = Integer.valueOf(text_raise_amt.getText().toString());
                if (amt+50<=human.getMoney())
                    amt += 50;
                text_raise_amt.setText(String.valueOf(amt));
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void put_dablu(){
        switch (dablaPlayer){
            case 0: human.deductMoney(amt_dablu);
                name_human.setBackground(getDrawable(R.color.crimsonRed)); break;
            case 1: cpu1.deductMoney(amt_dablu);
                name_cpu1.setBackground(getDrawable(R.color.crimsonRed)); break;
            case 2: cpu2.deductMoney(amt_dablu);
                name_cpu2.setBackground(getDrawable(R.color.crimsonRed)); break;
            case 3: cpu3.deductMoney(amt_dablu);
                name_cpu3.setBackground(getDrawable(R.color.crimsonRed)); break;
            case 4: cpu4.deductMoney(amt_dablu);
                name_cpu4.setBackground(getDrawable(R.color.crimsonRed)); break;
        }
    }

    boolean isShowAsked=false;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void playCPU(final int start) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = start;
                while (!playerList.get(i).equals(human)){

                    CPU_Player cpuPlayer = (CPU_Player) playerList.get(i);
                    String playerName = cpuPlayer.getPlayer_name();

                    Log.i("tag",playerName+"'s turn");

                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",playerName);
                    msg.setData(bundle);

                    greenLightHandler.sendMessage(msg);

                    long futuretime = System.currentTimeMillis() + 2000;
                    while (System.currentTimeMillis() < futuretime){}

                    String decision = cpuPlayer.getTheBestDecision(playerList.size());
                    Log.i("tag",playerName+" decided to "+decision);

                    interpretDecision(playerName,decision);

                    futuretime = System.currentTimeMillis() + 500;
                    while (System.currentTimeMillis() < futuretime){}

                    if (decision.equals("show")) {
                        tempHandler.sendEmptyMessage(0);
                        return;
                    }

                    if (decision.equals("fold")) {
                        if (i==(playerList.size()+1)-1)
                            i=0;
                        else
                            i=i;    //no change
                    }
                    else
                        i=(i+1)%playerList.size();

                    if (playerList.size()==1){
                        TwoPlayerFold(playerList.get(i));
                        return;
                    }

                    futuretime = System.currentTimeMillis() + 1000;
                    while (System.currentTimeMillis() < futuretime) {}

                    Log.i("tag","changing turn...");

                    Message msg2 = new Message();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("name",playerName);
                    msg2.setData(bundle2);

                    greyLightHandler.sendMessage(msg2);

                    futuretime = System.currentTimeMillis() + 200;
                    while (System.currentTimeMillis() < futuretime){}
                }

                humanTurnHandler.sendEmptyMessage(0);
            }
        };
        Thread sidethread = new Thread(runnable);
        sidethread.start();
    }

    Handler tempHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            decideWinner();
        }
    };
    Handler humanTurnHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            Log.i("tag","human turn");
            fn_bar.setVisibility(View.VISIBLE);
            name_human.setBackground(getDrawable(R.color.green));
            if (playerList.size()==2 && !isShowEnabled) {
                fn_bar.addView(show);
                isShowEnabled = true;
            }
        }
    };

    Handler greenLightHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            switch (name){
                case "CPU1": name_cpu1.setBackground(getDrawable(R.color.green)); break;
                case "CPU2": name_cpu2.setBackground(getDrawable(R.color.green)); break;
                case "CPU3": name_cpu3.setBackground(getDrawable(R.color.green)); break;
                case "CPU4": name_cpu4.setBackground(getDrawable(R.color.green)); break;
            }
        }
    };

    Handler greyLightHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            switch (name){
                case "CPU1": name_cpu1.setBackground(getDrawable(R.color.black_overlay)); break;
                case "CPU2": name_cpu2.setBackground(getDrawable(R.color.black_overlay)); break;
                case "CPU3": name_cpu3.setBackground(getDrawable(R.color.black_overlay)); break;
                case "CPU4": name_cpu4.setBackground(getDrawable(R.color.black_overlay)); break;
            }
        }
    };

    Handler foldHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            Log.i("tag",name+" folds...");

            switch (name){
                case "CPU1": cards_cpu1.setVisibility(View.INVISIBLE);
                    cpu1.isPlaying=false;
                    playerList.remove(cpu1);    break;

                case "CPU2": cards_cpu2.setVisibility(View.INVISIBLE);
                    cpu2.isPlaying=false;
                    playerList.remove(cpu2);    break;

                case "CPU3": cards_cpu3.setVisibility(View.INVISIBLE);
                    cpu3.isPlaying=false;
                    playerList.remove(cpu3);    break;

                case "CPU4": cards_cpu4.setVisibility(View.INVISIBLE);
                    cpu4.isPlaying=false;
                    playerList.remove(cpu4);    break;
            }
            //textToSpeech.speak("fold",textToSpeech.QUEUE_FLUSH,null,null);
        }
    };
    Handler showHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            Log.i("tag",name+" shows...");
            switch (name){
                case "CPU1": cpu1.deductMoney(amt_call*2);
                    text_money_cpu1.setText(String.valueOf(cpu1.getMoney())); break;
                case "CPU2": cpu2.deductMoney(amt_call*2);
                    text_money_cpu2.setText(String.valueOf(cpu2.getMoney())); break;
                case "CPU3": cpu3.deductMoney(amt_call*2);
                    text_money_cpu3.setText(String.valueOf(cpu3.getMoney())); break;
                case "CPU4": cpu4.deductMoney(amt_call*2);
                    text_money_cpu4.setText(String.valueOf(cpu4.getMoney())); break;
            }

            pot += amt_call*2;
            text_pot.setText(String.valueOf(pot));

            showCPUCards();
        }
    };
    Handler callHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            Log.i("tag",name+" calls...");
            switch (name){
                case "CPU1": cpu1.deductMoney(amt_call);
                    text_money_cpu1.setText(String.valueOf(cpu1.getMoney())); break;

                case "CPU2": cpu2.deductMoney(amt_call);
                    text_money_cpu2.setText(String.valueOf(cpu2.getMoney())); break;

                case "CPU3": cpu3.deductMoney(amt_call);
                    text_money_cpu3.setText(String.valueOf(cpu3.getMoney())); break;

                case "CPU4": cpu4.deductMoney(amt_call);
                    text_money_cpu4.setText(String.valueOf(cpu4.getMoney())); break;
            }

            pot += amt_call;
            text_pot.setText(String.valueOf(pot));
        }
    };
    Handler raiseHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            int raise_amt = msg.getData().getInt("raise_amt");
            Log.i("tag",name+" raise...");
            switch (name){
                case "CPU1":
                    cpu1.deductMoney(amt_call+raise_amt);
                    text_money_cpu1.setText(String.valueOf(cpu1.getMoney())); break;

                case "CPU2":
                    cpu2.deductMoney(amt_call+raise_amt);
                    text_money_cpu2.setText(String.valueOf(cpu2.getMoney())); break;

                case "CPU3":
                    cpu3.deductMoney(amt_call+raise_amt);
                    text_money_cpu3.setText(String.valueOf(cpu3.getMoney())); break;

                case "CPU4":
                    cpu4.deductMoney(amt_call+raise_amt);
                    text_money_cpu4.setText(String.valueOf(cpu4.getMoney())); break;
            }

            amt_call = amt_call+raise_amt;
            pot += amt_call;

            text_pot.setText(String.valueOf(pot));
            text_raise_amt.setText(String.valueOf(amt_call+50));
            text_call_amt.setText(String.valueOf(amt_call));
        }
    };

    public void interpretDecision(String name, String decision){
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);

        switch (decision){
            case "fold":
                msg.setData(bundle);
                foldHandler.sendMessage(msg);
                break;

            case "show":
                msg.setData(bundle);
                showHandler.sendMessage(msg);
                break;

            case "call":
                msg.setData(bundle);
                callHandler.sendMessage(msg);
                break;

            case "raise,50":
                bundle.putInt("raise_amt",50);
                msg.setData(bundle);
                raiseHandler.sendMessage(msg);
                break;

            case "raise,100":
                bundle.putInt("raise_amt",100);
                msg.setData(bundle);
                raiseHandler.sendMessage(msg);
                break;
        }
    }

    Handler OnOffHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            int signal = msg.getData().getInt("signal");

            if (signal==1) {
                switch (name){
                    case "You": name_human.setBackground(getDrawable(R.color.colorAccent)); break;
                    case "CPU1": name_cpu1.setBackground(getDrawable(R.color.colorAccent)); break;
                    case "CPU2": name_cpu2.setBackground(getDrawable(R.color.colorAccent)); break;
                    case "CPU3": name_cpu3.setBackground(getDrawable(R.color.colorAccent)); break;
                    case "CPU4": name_cpu4.setBackground(getDrawable(R.color.colorAccent)); break;
                }
            }else {
                switch (name){
                    case "You": name_human.setBackground(getDrawable(R.color.black_overlay)); break;
                    case "CPU1": name_cpu1.setBackground(getDrawable(R.color.black_overlay)); break;
                    case "CPU2": name_cpu2.setBackground(getDrawable(R.color.black_overlay)); break;
                    case "CPU3": name_cpu3.setBackground(getDrawable(R.color.black_overlay)); break;
                    case "CPU4": name_cpu4.setBackground(getDrawable(R.color.black_overlay)); break;
                }
            }
        }
    };

    public void TwoPlayerFold(final Player winner){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long futuretime = System.currentTimeMillis() + 500;
                while (System.currentTimeMillis() < futuretime){}

                winner.addMoney(pot);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("name",winner.getPlayer_name());
                message.setData(bundle);
                winnerHandler.sendMessage(message);

                Blink(winner.getPlayer_name());

                futuretime = System.currentTimeMillis() + 1500;
                while (System.currentTimeMillis() < futuretime) {}

                endings.sendEmptyMessage(0);
            }
        };
        Thread endThread = new Thread(runnable);
        endThread.start();
    }

    public void showCPUCards(){
        if (cpu1.isPlaying) {
            cpu1_card1.setText(cpu1.hand.get(0).toString());
            cpu1_card2.setText(cpu1.hand.get(1).toString());
            cpu1_card3.setText(cpu1.hand.get(2).toString());
        }
        if (cpu2.isPlaying) {
            cpu2_card1.setText(cpu2.hand.get(0).toString());
            cpu2_card2.setText(cpu2.hand.get(1).toString());
            cpu2_card3.setText(cpu2.hand.get(2).toString());
        }
        if (cpu3.isPlaying) {
            cpu3_card1.setText(cpu3.hand.get(0).toString());
            cpu3_card2.setText(cpu3.hand.get(1).toString());
            cpu3_card3.setText(cpu3.hand.get(2).toString());
        }
        if (cpu4.isPlaying) {
            cpu4_card1.setText(cpu4.hand.get(0).toString());
            cpu4_card2.setText(cpu4.hand.get(1).toString());
            cpu4_card3.setText(cpu4.hand.get(2).toString());
        }
    }

    public void decideWinner() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Player winner = null;

                int maxRank=-1;
                for (int i=0;i<playerList.size();i++)
                    if (playerList.get(i).getRank() > maxRank)
                        maxRank = playerList.get(i).getRank();

                ArrayList<Player> finalist = new ArrayList<>();
                for (int i=0;i<playerList.size();i++)
                    if (playerList.get(i).getRank()==maxRank)
                        finalist.add(playerList.get(i));

                if (finalist.size()>1){
                    switch (maxRank){
                        case 2:
                            Collections.sort(finalist,new sortByPairCard());
                            winner = finalist.get(0);
                            break;
                        default:
                            Collections.sort(finalist,new sortByHighCard());
                            winner = finalist.get(0);
                    }
                }else {
                    winner = finalist.get(0);
                }

                long futuretime = System.currentTimeMillis() + 2000;
                while (System.currentTimeMillis() < futuretime){}

                winner.addMoney(pot);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("name",winner.getPlayer_name());
                message.setData(bundle);
                winnerHandler.sendMessage(message);

                Blink(winner.getPlayer_name());

                futuretime = System.currentTimeMillis() + 2000;
                while (System.currentTimeMillis() < futuretime) {}

                endings.sendEmptyMessage(0);
            }
        };

        Thread endThread = new Thread(runnable);
        endThread.start();
    }

    Handler winnerHandler = new Handler(){
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString("name");
            switch (name){
                case "You": text_human_money.setText(String.valueOf(human.getMoney())); break;
                case "CPU1": text_money_cpu1.setText(String.valueOf(cpu1.getMoney())); break;
                case "CPU2": text_money_cpu2.setText(String.valueOf(cpu2.getMoney())); break;
                case "CPU3": text_money_cpu3.setText(String.valueOf(cpu3.getMoney())); break;
                case "CPU4": text_money_cpu4.setText(String.valueOf(cpu4.getMoney())); break;
            }

            String message;
            if (name.equals("You"))
                message = "You won!";
            else
                message = name+" wins.";
            textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        }
    };

    private class sortByHighCard implements Comparator<Player>{

        @Override
        public int compare(Player A, Player B) {
            if (B.hand.get(0).getCardNum() != A.hand.get(0).getCardNum())
                return B.hand.get(0).getCardNum()-A.hand.get(0).getCardNum();

            if (B.hand.get(1).getCardNum() != A.hand.get(1).getCardNum())
                return B.hand.get(1).getCardNum()-A.hand.get(1).getCardNum();

            if (B.hand.get(2).getCardNum() != A.hand.get(2).getCardNum())
                return B.hand.get(2).getCardNum()-A.hand.get(2).getCardNum();

            return 0;
        }
    }

    private class sortByPairCard implements Comparator<Player>{

        @Override
        public int compare(Player A, Player B) {
            if (B.hand.get(1).getCardNum() != A.hand.get(1).getCardNum())
                return B.hand.get(1).getCardNum()-A.hand.get(1).getCardNum();

            if (B.hand.get(0).getCardNum() != A.hand.get(0).getCardNum())
                return B.hand.get(0).getCardNum()-A.hand.get(0).getCardNum();

            if (B.hand.get(2).getCardNum() != A.hand.get(2).getCardNum())
                return B.hand.get(2).getCardNum()-A.hand.get(2).getCardNum();

            return 0;
        }
    }

    public void Blink(final String name){
        Runnable blinker = new Runnable() {
            @Override
            public void run() {
                for (int i=1;i<=11;i++){
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    bundle.putInt("signal",i%2);
                    message.setData(bundle);
                    OnOffHandler.sendMessage(message);

                    long waitTime = System.currentTimeMillis() + 250;
                    while (System.currentTimeMillis()<waitTime) {}
                }
            }
        };
        Thread sideThread = new Thread(blinker);
        sideThread.start();
    }

    Handler endings = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            pot = 0;
            amt_dablu = 50;
            amt_call = amt_dablu;

            text_pot.setText(String.valueOf(pot));
            text_call_amt.setText(String.valueOf(amt_call));
            text_raise_amt.setText(String.valueOf(amt_call + 50));

            fn_bar.removeView(show);
            isShowEnabled = false;

            int noOfPlayer = 5;
            if (human.getMoney() <= 0) {
                Intent intent = new Intent(Table.this, MainActivity.class);
                startActivity(intent);
            }
            if (cpu1.getMoney() <= 0) {
                cpu1.isOut = true;
                noOfPlayer--;
            }
            if (cpu2.getMoney() <= 0) {
                cpu2.isOut = true;
                noOfPlayer--;
            }
            if (cpu3.getMoney() <= 0) {
                cpu3.isOut = true;
                noOfPlayer--;
            }
            if (cpu4.getMoney() <= 0) {
                cpu4.isOut = true;
                noOfPlayer--;
            }

            dablaPlayer = (dablaPlayer + 1) % noOfPlayer;

            deal.setVisibility(View.VISIBLE);
        }
    };

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}