package mtcg.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class User {
    private int userId;
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private List<Card> deck;
    @Getter
    private List<Card> stack;
    ///////Getter Methods-------------------------------------------
    @Getter
    private int coins = 20;
    @Getter
    private int elo = 100;
    @Getter
    @Setter
    private String bio;
    @Setter
    @Getter
    private String image;
    @Setter
    @Getter
    private String changename;
    @Setter
    @Getter
    int wins;
    @Setter
    @Getter
    int losses;

    public User(String username, String password, List<Card> deck, List<Card> stack, int coins, int elo, int wins, int losses) {
        this.username = username;
        this.password = password;
        this.deck = deck;
        this.coins = coins;
        this.stack = stack;
        this.elo = elo;
        this.wins=wins;
        this.losses=losses;

    }

    public User() {}

    public User(String name, int coins, int elo){
        this.username=name;
        this.elo=elo;
        this.coins=coins;

    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getElo() {
        return elo;
    }

    public int getId() {
        return userId;
    }

    public void setElo(int i) {
        elo = i;
    }

    public void addCardToStack(Card card) {
        stack.add(card);
    }
    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void setUserId(int userId) {
        this.userId=userId;
    }

    public void setUsername(String username) {
        this.username= username;
    }

    public void setPassword(String password) {
        this.password=password;
    }
}

