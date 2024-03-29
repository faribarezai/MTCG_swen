package mtcg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.dal.UnitOfWork;
import mtcg.model.Card;
import mtcg.model.Player;
import mtcg.repository.PlayerRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class PlayerService implements Service {
    private Player player;
    public PlayerService(Connection connection) {
    }
    public PlayerService() {}


    public List<Card> selectDeckCards(List<Card> card) {
        // put in deck 4 Cards (only the best ones)
        // Sort the cards in descending order of damage
        card.sort(Comparator.comparingInt(Card::getDamage).reversed());

        // Add the top 4 cards with the highest damage to the deck
        for (int i = 0; i < Math.min(card.size(), 4); i++) {
            player.getDeck().add(card.get(i));
        }
        return player.getDeck();
    }

    public void requestBattle() {
        // request to server with current deck
    }

    public void compareStats(Player opponent) {
        // comparing stats of yours and opponent
        System.out.println("Comparing stats: ");
        System.out.println(this.player.getUsername() + ": " + this.player.getElo() + "Elo, ");
        System.out.println(opponent.getUsername() + ": " + opponent.getElo()+ "Elo, ");

        //opponent.getScoreboard();
    }

    //return sorted List
    public List<Integer>sortScoreBoard() {
        return player.getScoreboard().stream().sorted().toList();
    }




    public void updatePlayer(Player player) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            PlayerRepository playerRepository = new PlayerRepository(unitOfWork);
            playerRepository.updatePlayer(player);
            unitOfWork.commitTransaction();
        } catch (Exception e) {
            // Handle exceptions
        }
    }

    @Override
    public Response handleRequest(Request request) {
       // String requestBody = request.getBody();
        // Implement user registration or login logic here

        String route = request.getServiceRoute();
        if ("/users".equals(route) && request.getMethod() == Method.POST) {
            return registerUser(request);
        }

        // Parse request body, handle JSON, etc.
        // You can use request.getHeaders() to access headers (e.g., Authorization)

        // Example: Checking Authorization header
        String token = request.getAuthorizationToken();
        if (token != null) {
            // Validate or use the token as needed
        }

        // Example: Handle user registration or login logic here

        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "User operation successful");
    }

    private Response registerUser(Request request) {
        try {
            // Parse the JSON body from the request
            String requestBody = request.getBody();
           // ObjectMapper objectMapper = new ObjectMapper();
           // Player player = objectMapper.readValue(requestBody, Player.class);

            // Validate the request body
            if (player == null || player.getUsername() == null || player.getPassword() == null
                    || player.getUsername().isEmpty() || player.getPassword().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Invalid request body");
            }


            // Check if the user already exists
            if (userExists(player.getUsername())) {
                return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "User already exists");
            }

            // save user to database
            saveUserToDatabase(player);

            // Return a success response
            return new Response(HttpStatus.CREATED, ContentType.PLAIN_TEXT, "User registered successfully");
        } catch (Exception e){
            // Handle the exception (e.g., invalid JSON format)
            return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Invalid request body");
        }
    }

    private boolean userExists(String username) {
        // Logic to check if the user already exists in the database
        // Return true if the user exists, false otherwise
        // ... (you need to implement this method)
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtcg", "postgres", "password");
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Player WHERE username = ?")) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            return false;
    }

    private void saveUserToDatabase(Player player) {

        //JDBC to execute an INSERT statement
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtcg", "postgres", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}

