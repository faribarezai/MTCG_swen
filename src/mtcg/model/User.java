package mtcg.model;

import lombok.Getter;

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
    private String bio;
    @Getter
    private String image;
    @Getter
    private String changename;
    @Getter
    List<Integer> scoreboard;// shall be in battlelogic

    public User(String username, String password, List<Card> deck, List<Card> stack, int coins, int elo) {
        this.username = username;
        this.password = password;
        this.deck = deck;
        this.coins = coins;
        this.stack = stack;
        this.elo = elo;

    }

    public User() {
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

