package mtcg.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Package {
    private int id;
    @Getter
    private List<Card> cards;

    public Package(List<Card> card) {
        this.cards= card;

    }

}
