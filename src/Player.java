import java.util.Comparator;
import java.util.List;

public class Player {
    private String username;
    private String password;
    private List<Card> deck;
    private List<Card>stack;
    private int coins=20;
    private Boolean win; // +3points else -5 for loss
    private int ELOvalue=100;
    List<Integer>scoreboard;

    Player(String username, String password, List<Card>deck, List<Card>stack, int coins, List<Integer>sboard) {
        this.username=username;
        this.password=password;
        this.deck=deck;
        this.coins=coins;
        this.stack= stack;
        this.scoreboard =sboard;

        if(!register(username, password)){
           register(username, password);
            login(username, password);
        }
        login(username, password);

    }

    public Player() {}

    public Boolean register(String name, String psw){
        return false;
    }

    public Boolean login(String name, String psw) {
        return !(name.isEmpty() || psw.isEmpty());
    }

    //select 4 Cards for Battle
    public List<Card> selectCards(List<Card> card) {
        // put in deck 4 Cards (only the best ones)
        // Sort the cards in descending order of damage
        card.sort(Comparator.comparingInt(Card::getDamage).reversed());

        // Add the top 4 cards with the highest damage to the deck
        for (int i = 0; i < Math.min(card.size(), 4); i++) {
            deck.add(card.get(i));
        }
        return deck;
    }

    public void requestBattle() {
    // request to server with current deck
    }

    public void buyPackage(int coins) {

    }
    public void compareStats(Player opponent) {

    }

    //return sorted List
    public List<Integer>sortScoreBoard() {
        scoreboard= getScoreboard();
        return scoreboard.stream().sorted().toList();
    }


    ///////Getter Methods-------------------------------------------
    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Boolean getWin() {
        return win;
    }

    public int getELO() {
        return ELOvalue;
    }

    public List<Integer>getScoreboard() {
        //return sorted List of scoreboared
        return scoreboard;

    }
}
