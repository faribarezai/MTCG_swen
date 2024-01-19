package mtcg.controller;

import mtcg.model.Card;
import mtcg.dal.UnitOfWork;
import mtcg.service.CardService;

public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Example endpoint for updating a card
    public void handleCardUpdate(Card card) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            cardService.updateCard(card);
            unitOfWork.commitTransaction();
        } catch (Exception e) {
            // Handle exceptions
        }
    }

    // Add other methods for card-related operations as needed
}
