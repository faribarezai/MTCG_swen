import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Lets play.... Monster Trading Card Game!");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Name: ");
        String inputName = scanner.next();

        System.out.println("Please enter your Password: ");
        String inputPassword = scanner.next();


        Card firespell = new Card("FireSpell", 10, EType.FIRE);
        Card waterspell = new Card("WaterSpell", 20, EType.WATER);
        Card watergoblin = new Card("WaterGoblin", 10, EType.WATER);
        Card firetroll = new Card("FireTroll", 10, EType.FIRE);
        Card knight = new Card("Knight", 15, EType.NORMAL);

        List<Card> peterStack = new ArrayList<>();
        peterStack.add(firespell);
        peterStack.add(waterspell);
        peterStack.add(watergoblin);
        peterStack.add(firetroll);
        peterStack.add(watergoblin);
        peterStack.add(knight);

        List<Card> peterDeck = new ArrayList<>();



        List<Integer> peterSc = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // create User peter
        Player peter = new Player("Peet", "password", peterDeck, peterStack, 20, peterSc);
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Authenticate the credentials
        boolean authenticate= peter.login(inputName, inputPassword);
        if(authenticate)
            System.out.println("****** Welcome " + inputName + "!!!******");

        peterDeck = peter.selectCards(peterStack);

        System.out.println("----------------------Peters Stack----------------------------");
        for (Card cs : peterStack)
            System.out.println(cs.getName() + "(" + cs.getDamage() + ")");

        System.out.println("----------------------Peters Deck----------------------------");
        for (Card cs : peterDeck)
            System.out.println(cs.getName() + "(" + cs.getDamage() + ")");


        System.out.println("--------sortierter Scoreboard-----");
        peter.scoreboard.add(12);
        peter.scoreboard.add(45);
        peter.scoreboard.add(-1);
        peterSc= peter.sortScoreBoard();
        for (Integer in : peterSc)
            System.out.print(in + ", ");




    }

}