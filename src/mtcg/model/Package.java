package mtcg.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Package {
    @Getter
    private int pckgId;
    @Getter
    private List<Integer> cardIds;

    public Package(int id, List<Integer> card) {
        this.pckgId=id;
        this.cardIds= card;
    }

    public Package( List<Integer> card) {
        this.cardIds= card;
    }

    public Package(){}

    public void setPckgId(int pckgId) {
        this.pckgId = pckgId;
    }

}
