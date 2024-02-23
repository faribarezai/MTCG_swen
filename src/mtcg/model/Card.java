package mtcg.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Card{
    @Getter
    private int cardId;
    @Getter
    private int userId;
    private String name;
    private int damage;
    @Getter
    private ElementType element;
    @Getter
    private CardType cardType;


    public Card(){}
    public Card(int userId, String name,int damage, ElementType elem, CardType ct) {
        this.userId=userId;
        this.name=name;
        this.damage=damage;
        this.element=elem;
        this.cardType=ct;

    }
   // @JsonProperty("element")
   // public ElementType getElementType() { return element;}

    public void setcardId(int cardId) {
        this.cardId=cardId;
    }

    public void setUserId(int uid) {
        this.userId=uid;
    }

}