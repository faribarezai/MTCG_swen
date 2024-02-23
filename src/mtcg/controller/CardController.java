package mtcg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.model.Card;
import mtcg.dal.UnitOfWork;
import mtcg.model.User;
import mtcg.repository.CardRepository;
import mtcg.service.CardService;

import java.util.List;

public class CardController {
    private CardRepository cardRepo = new CardRepository();
    public CardController() {}


    public Response showAllCards(User user, Request request) {

        //System.out.println("Username: " + user.getUsername() + " userid: "+ user.getId() + " elo: " + user.getElo() +" coins: " + user.getCoins());

       // cardRepo.updateCard(card);

        List<Card> cards = cardRepo.getAllCards(user.getId());
        System.out.println("in showAllCards!! ");

        // Convert the list of cards to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String cardsJson;
        try {
            cardsJson = objectMapper.writeValueAsString(cards);
            System.out.println("im try Block!! ");
        } catch (JsonProcessingException ex) {
            System.out.println("JSON Exception!! ");
            throw new RuntimeException(ex);
        }

        // Return the response with the list of cards
        return new Response(HttpStatus.OK, ContentType.JSON, cardsJson+ " \n");

        }



    public Response configureDeck(Request request) {
        return new Response(HttpStatus.OK, ContentType.JSON,  "NOT Implemented yet\n");
    }


}
