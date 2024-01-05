package src.mtcg;

public class Card {
    private String name;
    private int damage;
    private EType element;


    Card(String name, int damage, EType elem) {
        this.name=name;
        this.damage=damage;
        this.element=elem;

    }

    public int getDamage() {
        return damage;
    }
    public String getName() {
        return name;
    }

    public EType getElement() {
        return element;
    }


}