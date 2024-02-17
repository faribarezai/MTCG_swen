package mtcg.service;


import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
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
    String route = request.getServiceRoute();

        if ("/cards".equals(route) && request.getMethod() == Method.GET) {
        //return cardController.aquireCard(request);
    }
        if ("/deck".equals(route) && request.getMethod() == Method.GET) {
       // return cardController.configureDeck(request);
    }
    // Handle other routes and methods as needed
        return new Response(HttpStatus.OK, ContentType.JSON, "handle Card process successful");
}
    // Add other methods for card-related business logic as needed
}
