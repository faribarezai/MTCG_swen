import httpserver.server.Server;
import mtcg.dal.DatabaseManager;
import mtcg.service.CardService;
import mtcg.service.PlayerService;

public class Main {
    public static void main(String[] args) {

        System.out.println("Lets play.... Monster Trading Card Game!");

       /* Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Name: ");
        String inputName = scanner.next();

        System.out.println("Please enter your Password: ");
        String inputPassword = scanner.next();

        */

        try {
            // Initialize the HTTP server
            Server server = new Server();
            server.start();

            // Initialize other components
            PlayerService playerService = new PlayerService(DatabaseManager.INSTANCE.getConnection());
            CardService cardService = new CardService(/* pass dependencies if needed */);
            // ... other components

            // Your application is now running

        } catch (Exception e) {
            // Handle initialization failures
            e.printStackTrace();
        }
    }
}



/*
    public static void main(String[] args) {
        // Initialize the router
        Router router = new Router();

        // Initialize the server
        Server server = new Server(8080, router);

        // You can add more services/routes dynamically if needed
        // router.addService("/newRoute", new SomeService());

        try {
            // Start the server
            server.start();
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }
*/







/*

        Card firespell = new Card("FireSpell", 10, ElementType.FIRE);
        Card waterspell = new Card("WaterSpell", 20, ElementType.WATER);
        Card watergoblin = new Card("WaterGoblin", 10, ElementType.WATER);
        Card firetroll = new Card("FireTroll", 10, ElementType.FIRE);
        Card knight = new Card("Knight", 15, ElementType.NORMAL);

        List<Card> peterStack = new ArrayList<>();
        peterStack.add(firespell);
        peterStack.add(waterspell);
        peterStack.add(watergoblin);
        peterStack.add(firetroll);
        peterStack.add(watergoblin);
        peterStack.add(knight);
*/
  /*      List<Card> peterDeck = new ArrayList<>();



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
        peter.getScoreboard().add(12);
        peter.getScoreboard().add(45);
        peter.getScoreboard().add(-1);
        peterSc= peter.sortScoreBoard();
        for (Integer in : peterSc)
            System.out.print(in + ", ");




    }
    */