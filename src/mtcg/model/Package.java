package mtcg.model;

<<<<<<< HEAD
=======
import lombok.Getter;

import java.util.ArrayList;
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
import java.util.List;

public class Package {
    private int id;
<<<<<<< HEAD
    private List<Card> cards;
=======
    @Getter
    private List<Card> cards;

    public Package(List<Card> card) {
        this.cards= card;

    }

>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
}
