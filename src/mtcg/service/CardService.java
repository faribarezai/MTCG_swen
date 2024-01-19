package mtcg.service;


import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.model.Card;
import mtcg.repository.CardRepository;

import java.sql.Connection;

public class CardService implements Service {

    private CardRepository cardRepository = null;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardService() {}
    public CardService(Connection con){}

    public void updateCard(Card card) {
        // Add business logic related to updating a card (if needed)
        cardRepository.updateCard(card);
    }

    @Override
    public Response handleRequest(Request request) {
        return null;
    }

    // Add other methods for card-related business logic as needed
}
