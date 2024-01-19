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

    /**
     * "[{\"Id\":\"67f9048f-99b8-4ae4-b866-d8008d00c53d\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"aa9999a0-734c-49c6-8f4a-651864b14e62\", \"Name\":\"RegularSpell\", \"Damage\": 50.0}, {\"Id\":\"d6e9c720-9b5a-40c7-a6b2-bc34752e3463\", \"Name\":\"Knight\", \"Damage\": 20.0}, {\"Id\":\"02a9c76e-b17d-427f-9240-2dd49b0d3bfd\", \"Name\":\"RegularSpell\", \"Damage\": 45.0}, {\"Id\":\"2508bf5c-20d7-43b4-8c77-bc677decadef\", \"Name\":\"FireElf\", \"Damage\": 25.0}]"
     * echo.
     * curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"70962948-2bf7-44a9-9ded-8c68eeac7793\", \"Name\":\"WaterGoblin\", \"Damage\":  9.0}, {\"Id\":\"74635fae-8ad3-4295-9139-320ab89c2844\", \"Name\":\"FireSpell\", \"Damage\": 55.0}, {\"Id\":\"ce6bcaee-47e1-4011-a49e-5a4d7d4245f3\", \"Name\":\"Knight\", \"Damage\": 21.0}, {\"Id\":\"a6fde738-c65a-4b10-b400-6fef0fdb28ba\", \"Name\":\"FireSpell\", \"Damage\": 55.0}, {\"Id\":\"a1618f1e-4f4c-4e09-9647-87e16f1edd2d\", \"Name\":\"FireElf\", \"Damage\": 23.0}]"
     * echo.
     * curl -i -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"2272ba48-6662-404d-a9a1-41a9bed316d9\", \"Name\":\"WaterGoblin\", \"Damage\": 11.0}, {\"Id\":\"3871d45b-b630-4a0d-8bc6-a5fc56b6a043\", \"Name\":\"Dragon\", \"Damage\": 70.0}, {\"Id\":\"166c1fd5-4dcb-41a8-91cb-f45dcd57cef3\", \"Name\":\"Knight\", \"Damage\": 22.0}, {\"Id\":\"237dbaef-49e3-4c23-b64b-abf5c087b276\", \"Name\":\"WaterSpell\", \"Damage\": 40.0}, {\"Id\":\"27051a20-8580-43ff-a473-e986b52f297a\", \"Name\":\"FireElf\", \"Damage\": 28.0}]"
     * echo.*/

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
        // This is a placeholder; you need to implement the actual logic.
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
        System.out.println("Error: token is null!!");

        // Example: Handle transactions logic here
        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "Package operation successful");
    }


}
