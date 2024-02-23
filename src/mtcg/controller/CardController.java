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
       List<Card> cards = cardRepo.getAllCards(user.getId());
        System.out.println("in showAllCards!! ");
        for (Card card : cards) {
            System.out.println("Cardid: " + card.getCardId() + ", userid: "  + card.getUserId() +
                    ", name: " + card.getName() + ", damage: " + card.getDamage() + ", element: " + card.getElement() +
                    ", CardType: " + card.getCardType());
        }

        System.out.println("cardId: " +  " Userid: " + user.getUsername() + " userid: "+ user.getId() + " elo: " + user.getElo() +" coins: " + user.getCoins());

        // Convert the list of cards to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String cardsJson;
        try {
            cardsJson = objectMapper.writeValueAsString(cards);
            cardsJson = cardsJson.replaceAll("\\},", "},\n");

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
