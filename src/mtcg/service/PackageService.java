package mtcg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.controller.PackageController;
import mtcg.model.Card;
import mtcg.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PackageService implements Service {
    private static final int CARDS_PER_PACKAGE = 5;
    private static final int COINS_PER_PACKAGE = 5;
    private PackageController packageController;


    public PackageService(){this.packageController= new PackageController();}

    public static Response buyPackage(User user) {
        // Check if the user has enough coins to buy a package
        if (user.getCoins() < COINS_PER_PACKAGE) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not enough coins to buy a package");
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
        List<Card> packageCards = new ArrayList<>();

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
        String route = request.getServiceRoute();
        //System.out.println("route -> " + route);

      //  String token= request.getAuth();
       // System.out.print
        // ln("Token -> " + token);

                if ("/packages".equals(route) && request.getMethod() == Method.POST) {

                    return packageController.createPackages(request);
                }

                if ("/transactions/packages".equals(route) && request.getMethod() == Method.POST) {
                    return packageController.createPackages(request);
                }

                return new Response(HttpStatus.OK, ContentType.JSON, "Package operation successful");

                // return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "Unauthorized request");
    }




}





