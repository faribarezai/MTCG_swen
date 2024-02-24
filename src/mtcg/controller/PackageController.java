package mtcg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.model.Card;
import mtcg.model.Package;

// other import statements...

import mtcg.model.User;
import mtcg.repository.CardRepository;
import mtcg.repository.PackageRepository;
import mtcg.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PackageController {
    private static final int COINS_PER_PACKAGE = 5;
    private PackageRepository packageRepo = new PackageRepository();
     private UserRepository userRepository= new UserRepository();
    private CardRepository cardRepo= new CardRepository();

    public PackageController() {
    }

    public PackageController(PackageRepository packageRepo) {
        this.packageRepo = packageRepo;
    }

    public void updateUserCoin(User user) {
        userRepository.updateCoinOfUser(user);
    }
    public void updateUserID(Card card, int uid) {cardRepo.updateCardByUserID(card, uid);}



    public Response createPackages(Request request) {
        try {
            // Parse the JSON body from the request
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            List<Card> cards = objectMapper.readValue(requestBody, new TypeReference<List<Card>>() {});
            List<Integer> cardIds = new ArrayList<>();

            for (Card card : cards) {
                  cardRepo.saveCard(card);
               // cardIds.add(card.getCardId());
               // System.out.println("after savingCard: " + card.getCardId() + ", " + card.getName() + ", " + card.getDamage() + ", " + card.getElement() + ", " + card.getCardType());
            }

            List<Card> c= cardRepo.getCards();
            for (Card card : c) {
                //cardRepo.saveCard(card);
                if (!cardIds.contains(card.getCardId())) {
                    cardIds.add(card.getCardId());
                }

              //   System.out.println("after getCard: " + card.getCardId() + ", " + card.getName() + ", " + card.getDamage() + ", " + card.getElement() + ", " + card.getCardType());

            }
            System.out.println("sizeOf CardList fetched: " +c.size());

            //_--------------------------------------------------------------------------------------------------------------

            // Fetch all existing card IDs from packages
            List<Integer> existingCardIds = packageRepo.getAllCardIdsFromPackages();
            // Shuffle the list of card IDs to get a random order
            Collections.shuffle(cardIds);

            // Select the first 5 card IDs from the shuffled list that are not already in existing packages
            List<Integer> selectedCardIds = cardIds.stream()
                    .filter(cardId -> !existingCardIds.contains(cardId))
                    .limit(5)
                    .collect(Collectors.toList());

            // Save the selected card IDs in the package only if there are 5 unique cards
            if (selectedCardIds.size() == 5) {
                Package pckg = new Package(selectedCardIds);
                packageRepo.savePackage(pckg);
            } else {
                System.out.println("Not enough unique cards available to fill the package.");
            }



            return new Response(HttpStatus.CREATED, ContentType.JSON, "Package created successfully \n");

        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body \n");
        }

    }

    public Response acquirePackage(User username, Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            System.out.println("requestBody: " +requestBody);
            if ("{}".equals(requestBody)) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No package \n");
            }
            // Deserialize the JSON into the PackageRequest class
            Package packageRequest = objectMapper.readValue(requestBody, Package.class);

            int pckgId = packageRequest.getPckgId();


            if (pckgId == 0 ) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No package \n");
            }



            // enough coins?
            if (username.getCoins() < COINS_PER_PACKAGE) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not enough coins to buy the package \n");
            }

            List<Integer> cardIds= packageRepo.getCardIdsFromPackagById(pckgId);
            List<Card> cards = cardRepo.getCards();

            for (Card card : cards) {
                System.out.println("Processing Card: " + card.getCardId() + ", " + card.getName() + ", " + card.getDamage() + ", " + card.getElement() + ", " + card.getCardType());
                // add it to stack of user
                if (cardIds.contains(card.getCardId())) {
                    updateUserID(card, username.getId());
                    username.addCardToStack(card);
                }
            }

            for (Card card : username.getStack()) {
                System.out.println("all aquired cards in stack of User: "
                        + card.getCardId() + ", "+ card.getUserId() + ", " + card.getName() + ", " + card.getDamage() +
                        ", " + card.getElement() + ", " + card.getCardType());
            }

            // calculate coins
            username.setCoins(username.getCoins() - COINS_PER_PACKAGE);
            updateUserCoin(username);
            return new Response(HttpStatus.OK, ContentType.JSON, "Package acquired successfully \n");

        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
           return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Nothing acquired for now \n");

        }

    }
}