package mtcg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.model.Card;
import mtcg.model.User;

import java.util.Collections;
import java.util.List;

public class PackageService implements Service {
    private static final int CARDS_PER_PACKAGE = 5;
    private static final int COINS_PER_PACKAGE = 5;

    public static Response buyPackage(User user) {
        // Check if the user has enough coins to buy a package
        if (user.getCoins() < COINS_PER_PACKAGE) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Not enough coins to buy a package");
        }

        // Deduct coins from the user's account
        user.deductCoins(COINS_PER_PACKAGE);

        // Generate and return a package with 5 cards
        List<Card> cards = generateCards();
        //Package purchasedPackage = new Package(cards);

        return new Response(HttpStatus.OK, ContentType.JSON, cards.toString());
       // return new Response(HttpStatus.OK, ContentType.JSON, toJson(purchasedPackage));
    }

    private static List<Card> generateCards() {

        //System.out.println("Id: " + card.getId() + "Name: " +card.getName() + ", Damage: " + card.getDamage());
        List<Card> packageCards = retrieveCardsFromDatabase();

        // Implement the logic to process each card in the package
        // For example, you can add each card to the user's deck, save them to the database, etc.
        // Update the logic based on your requirements.
        // Example: user.getDeck().addAll(packageCards);
        for (Card card : packageCards) {
            System.out.println("Processing card: " + card.getName() + " - Damage: " + card.getDamage());
        }

        return packageCards;
    }

    private static List<Card> retrieveCardsFromDatabase() {
        // Implement the logic to retrieve cards from the database based on their IDs
        // Example: return cardRepository.findByIdIn(cardIds);
        return Collections.emptyList();
    }




    @Override
    public Response handleRequest(Request request) {
        // Implement package-related logic here
        // Parse request body, handle JSON, etc.
        // You can use request.getHeaders() to access headers (e.g., Authorization)

        // Example: Checking Authorization header
        String token = request.getAuthorizationToken();
        if (token != null) {
            System.out.println("Congrats: token is not null!!");
        }
         else {System.out.println("Error: token is null!!");}

        // Example: Handle transactions logic here
        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "Package operation successful");
    }


}
