package mtcg.service;


import mtcg.model.Card;
import mtcg.repository.CardRepository;

public class CardService {

    private CardRepository cardRepository = null;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardService() {}

    public void updateCard(Card card) {
        // Add business logic related to updating a card (if needed)
        cardRepository.updateCard(card);
    }

    // Add other methods for card-related business logic as needed
}
