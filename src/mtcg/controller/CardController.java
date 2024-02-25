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
import mtcg.model.Package;
import mtcg.model.User;
import mtcg.repository.CardRepository;
import mtcg.repository.UserRepository;
import mtcg.service.CardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CardController {
    private CardRepository cardRepo = new CardRepository();
    private UserRepository userRepo= new UserRepository();

    public CardController() {
    }


    public Response showAllCards(User user, Request request) {
        List<Card> cards = cardRepo.getAllCards(user.getId());
        //System.out.println("in showAllCards!! ");
        for (Card card : cards) {
            System.out.println("Cardid: " + card.getCardId() + ", userid: " + card.getUserId() +
                    ", name: " + card.getName() + ", damage: " + card.getDamage() + ", element: " + card.getElement() +
                    ", CardType: " + card.getCardType());
        }

        System.out.println("cardId: " + " Userid: " + user.getUsername() + " userid: " + user.getId() + " elo: " + user.getElo() + " coins: " + user.getCoins());

        // Convert the list of cards to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String cardsJson;
        try {
            cardsJson = objectMapper.writeValueAsString(cards);
            cardsJson = cardsJson.replaceAll("\\},", "},\n");

            //  System.out.println("im try Block!! ");
        } catch (JsonProcessingException ex) {
            //System.out.println("JSON Exception!! ");
            throw new RuntimeException(ex);
        }

        // Return the response with the list of cards
        return new Response(HttpStatus.OK, ContentType.JSON, cardsJson + " \n");

    }


    public Response showDeck(User user, Request request) throws JsonProcessingException {
        //curl -i -X GET http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
        System.out.println("Im configureDeck!! ");

        List<Card> userStack = cardRepo.getAllCards(user.getId());


        if (userStack.isEmpty()) {
            System.out.println("Stack is EMPTY? ");
            // If cardsJson is empty, return an invalid response
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Empty Stack, has no cards \n");
        }


        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(userStack);
            requestBody = requestBody.replaceAll("\\},", "},\n");

        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

        /*
        if (user.getDeck().isEmpty()) {
            System.out.println("Deck EMPTY? ");
            // If cardsJson is empty, return an invalid response
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "unconfigured Deck \n" + objectMapper.writeValueAsString(user.getDeck()) + "\n");
        }
        */

        // Perform deck process
        try {
            bestDeck(user, userStack);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // print deck of user on console
        List<Card> userDeck = user.getDeck();
        for (Card c : user.getDeck()) {
            System.out.println("best 4 cards in Deck" + c.getCardId() + ", " + c.getUserId() + ", " + c.getName() + ",damage:  " + c.getDamage() + ", " + c.getElement() + ", " + c.getCardType());
        }

        // Convert the list of cards to JSON
        ObjectMapper objMapper = new ObjectMapper();
        String confDeck;
        try {
            confDeck = objectMapper.writeValueAsString(userDeck);
            confDeck = confDeck.replaceAll("\\},", "},\n");

            //  System.out.println("im try Block!! ");
        } catch (JsonProcessingException ex) {
            //System.out.println("JSON Exception!! ");
            throw new RuntimeException(ex);
        }

        //return List of Deck
        return new Response(HttpStatus.OK, ContentType.JSON, confDeck + "\n");

    }


    public Response createDeck(User user, Request request) throws JsonProcessingException {
        //curl -i -X PUT http://localhost:10001/deck --header "Authorization: Bearer kienboec-mtcgToken"
        System.out.println("Im createDeck!! ");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            System.out.println("requestBody: " + requestBody);
            // Deserialize the JSON into the PackageRequest class
            List<Card> cards = objectMapper.readValue(requestBody, new TypeReference<List<Card>>() {});
            List<Integer> cardIds = new ArrayList<>();

            System.out.println("Size of Cards " + cards.size());

            if(cards.size() < 4){
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Less than 4 Cards are set \n");
            }

            //get Stack of User
            List<Card> userStack = cardRepo.getAllCards(user.getId());

            System.out.println("IM Stack of user: ");
            for (Card card : cards) {
                System.out.println("Cards: " + card.getCardId() + ", " + card.getName()+ ", "  + card.getDamage()+ ", "  + card.getCardType()+ ", "  + card.getElement());
            }

            // Stack empty??
            if (userStack.isEmpty()) {
                System.out.println("Stack is EMPTY? ");
                // If cardsJson is empty, return an invalid response
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "empty Stack, no cards available \n");
            }

            // check if those cards are in userStack
            for (Card card : cards) {
                int cardIdToCheck = card.getCardId();

                // Check if the cardId is in userStack
                boolean isCardIdInUserStack = userStack.stream()
                        .anyMatch(userCard -> userCard.getCardId() == cardIdToCheck);

                if (!isCardIdInUserStack) {
                    // Handle the case where the cardId is not in userStack
                    for (Card cc : user.getDeck()) {
                        System.out.println("Cards: " + cc.getCardId() + ", " + cc.getName()+ ", "  + cc.getDamage()+ ", "  + cc.getCardType()+ ", "  + cc.getElement());
                        //objectMapper.writeValueAsString(userDeck) + "\n \n"
                    }
                    System.out.println("Card with ID " + cardIdToCheck + " not found in userStack \n");


                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, objectMapper.writeValueAsString(user.getDeck()) + "\n \n");
                }
            }


            // perform process of deck
            bestDeck(user, userStack);


            // print deck of User
            List<Card> userDeck = user.getDeck();
            for (Card c : userDeck) {
                System.out.println("best 4 cards in Deck" + c.getCardId() + ", " + c.getUserId() + ", " + c.getName() + ",damage:  " + c.getDamage() + ", " + c.getElement() + ", " + c.getCardType());
            }

            // success message
            return new Response(HttpStatus.OK, ContentType.JSON, "Deck configured successfully \n" );//+ objectMapper.writeValueAsString(userDeck) + "\n \n");


        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Cards not configured! \n");

        }
    }


public void bestDeck(User user, List<Card> userStack) throws SQLException {
    List<Card> sortedUserStack = userStack.stream()
            .sorted(Comparator.comparingInt(Card::getDamage).reversed())
            .limit(4)
            .collect(Collectors.toList());

    if (user.getDeck().isEmpty()) {
        System.out.println("Deck is EMPTY, so create it, need 4 best cards");
        // Put the selected cards into userDeck
        user.getDeck().addAll(sortedUserStack);
        userRepo.updateDeck(user, sortedUserStack);
    }
    System.out.println("Deck specified! ");
}


    public Response showDeckDiff(User user, Request request) {
        List<Card> userStack = cardRepo.getAllCards(user.getId());

        if (userStack.isEmpty()) {
            System.out.println("Stack is EMPTY? ");
            // If cardsJson is empty, return an invalid response
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Empty Stack, has no cards \n");
        }

        // Perform deck process
        try {
            bestDeck(user, userStack);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Card> userDeck= user.getDeck();

        // Convert the list of cards to plain text
        StringBuilder plainTextDeck = new StringBuilder();
        for (Card card : userDeck) {
            plainTextDeck.append("Card ID: ").append(card.getCardId())
                    .append(", Userid: ").append(card.getUserId())
                    .append(", Name: ").append(card.getName())
                    .append(", Damage: ").append(card.getDamage())
                    .append(", Element: ").append(card.getElement())
                    .append(", CardType: ").append(card.getCardType())
                    .append("\n");
        }

        // Return plain text response
        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, plainTextDeck.toString());

    }
}


