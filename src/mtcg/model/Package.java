package mtcg.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Package {
    private int id;
    @Getter
    private List<Integer> cardIds;

    public Package(List<Integer> card) {
        this.cardIds= card;

    }
    public List<Integer> getCardIds() {
        return cardIds;
    }
}
