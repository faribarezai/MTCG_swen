package mtcg.model;

import lombok.Getter;

import java.util.List;

public class Player {
    private int id;
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private List<Card> deck;
    @Getter
    private List<Card>stack;
    ///////Getter Methods-------------------------------------------
    @Getter
    private int coins=20;
    private List<BattleLog> won; // +3points else -5 for loss
    private List<BattleLog> lost;
    private int ELOvalue=100;
    @Getter
    List<Integer>scoreboard;
    public Player(String username, String password, List<Card> deck, List<Card> stack, int coins, List<Integer> scoreboard) {
        this.username=username;
        this.password=password;
        this.deck=deck;
        this.coins=coins;
        this.stack= stack;
        this.scoreboard =scoreboard;

    }

    public Player() {}

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getElo() {return ELOvalue;}

    public long getId() {return id;}

    public void deductCoins(int coinsPerPackage) {
        this.coins= getCoins()-coinsPerPackage;
    }

    public int setElo(int i) {
        return ELOvalue= i;
    }


    /*
    // logic already implemented in PlayerService
    //select 4 Cards for Battle
    public List<Card> selectDeckCards(List<Card> card) {
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
        // comparing stats of yours and opponent
        System.out.println("Comparing stats: ");
        System.out.println(this.getUsername() + ": " + this.ELOvalue + "Elo, ");
        System.out.println(opponent.getUsername() + ": " + opponent.ELOvalue + "Elo, ");

        //opponent.getScoreboard();
    }

    //return sorted List
    public List<Integer>sortScoreBoard() {
        scoreboard= getScoreboard();
        return scoreboard.stream().sorted().toList();
    }

*/

}
