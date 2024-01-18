package mtcg.model;

import lombok.Getter;

@Getter
public class Card{
    private String name;
    private int id;
    private Player player;
    private int damage;
    private ElementType element;
    @Getter
    private CardType cardType;


    public Card(String name,CardType ct, int damage, ElementType elem) {
        this.name=name;
        this.damage=damage;
        this.element=elem;
        this.cardType=ct;

    }

    public ElementType getElementType() { return element;}


}