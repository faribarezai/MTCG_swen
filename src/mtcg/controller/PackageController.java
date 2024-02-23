package mtcg.controller;

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
import java.util.List;

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

    public void updateUser(User user) {
        userRepository.updateCoinOfUser(user);
    }
    public void updateUserID(Card card, int uid) {cardRepo.updateCardByUserID(card, uid);}


    public Response createPackages(Request request) {
        try {
            // Parse the JSON body from the request
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            List<Card> cards = objectMapper.readValue(requestBody, new TypeReference<List<Card>>() {
            });
            List<Integer> cardIds = new ArrayList<>();

            for (Card card : cards) {
                 System.out.println("Received card registration request: " + card.getCardId() + ", " + card.getName() + ", " + card.getDamage() + ", " + card.getElementType() + ", " + card.getCardType());
                cardRepo.saveCard(card);
                cardIds.add(card.getCardId());
            }
            Package pckg = new Package(cardIds);
            packageRepo.savePackage(pckg);

            return new Response(HttpStatus.CREATED, ContentType.JSON, "Package created successfully \n");

        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body \n");
        }

    }

    public Response acquirePackage(User username, Request request) {
        try {
            // Parse the JSON body from the request
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            List<Card> cards = objectMapper.readValue(requestBody, new TypeReference<>() {
            });

            System.out.println("Username: " + username.getUsername() + " userid: "+ username.getId() + " elo: " + username.getElo() +" coins: " + username.getCoins());

            if (cards.size() != 5) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No package \n");
            }

            // enough coins?
            if (username.getCoins() < COINS_PER_PACKAGE) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not enough coins to buy the package \n");
            }

            //System.out.println("COOL How many coins do I have? " + username.getCoins());

            if(!cards.isEmpty()) {
                for (Card card : cards) {
                    System.out.println("Processing Card: " + card.getCardId() + ", " + card.getName() + ", " + card.getDamage() + ", " + card.getElementType() + ", " + card.getCardType());
                    // add it to stack of user
                    username.addCardToStack(card);
                    updateUserID(card, username.getId());

                    /*try {
        // add it to stack of user
        username.addCardToStack(card);
        // update the userId for the card
        updateUserID(card, username.getId());
    } catch (Exception e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }*/

                }
/*
                for (Card c : username.getStack()) {

                    System.out.println("Stack of user:  " + c.getCardId() + ", " + c.getName() + ", " + c.getDamage() + ", " + c.getElementType() + ", " + c.getCardType());
                }*/

                // update
                username.setCoins(username.getCoins() - COINS_PER_PACKAGE);
                updateUser(username);
                //set userid in card class in db
                //updateUserID(card, username.getId());
                return new Response(HttpStatus.OK, ContentType.JSON, "Package acquired successfully \n");
            }

            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not enough cards in the aquisition package! \n");

        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
           return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Nothing acquired for now \n");

        }

    }
}





  /*  public void savePackage(Package pckg) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password")) {
            // Check if any of the cardIds already exist
            if (cardIdsExist(connection, pckg.getCardIds())) {
                System.out.println("cardIds already exist. Package not saved.");
                return;  // Do not proceed with saving the package
            }

        try (Connection con =  DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO package (cardIds) VALUES (?)")) {
            Integer[] cardIdsArray = pckg.getCardIds().toArray(new Integer[0]);

            preparedStatement.setArray(1, connection.createArrayOf("INTEGER", cardIdsArray));
            // System.out.println("SQL Query: " + preparedStatement.toString());

            preparedStatement.executeUpdate();
            System.out.println("Package saved successfully.");
        }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }



    public void saveCard(Card card) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO card (cardId, name, damage, element, cardType) VALUES (?, ?, ?, CAST(? AS elementtype), CAST(? AS cardtype))")) {
            preparedStatement.setInt(1, card.getCardId());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setInt(3, card.getDamage());
            preparedStatement.setString(4, String.valueOf(card.getElementType()));
            preparedStatement.setString(5, String.valueOf(card.getCardType()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

        private boolean cardIdsExist(Connection connection, List<Integer> cardIds) throws SQLException {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM package WHERE cardIds @> ?::integer[]")) {
                // Check if any row matches the given cardIds
                Array cardIdsArray = connection.createArrayOf("integer", cardIds.toArray());
                preparedStatement.setArray(1, cardIdsArray);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
*/
