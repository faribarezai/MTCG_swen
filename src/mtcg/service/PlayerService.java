package mtcg.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.dal.UnitOfWork;
import mtcg.model.Player;
import mtcg.repository.PlayerRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerService implements Service {
    public PlayerService(Connection connection) {
    }
    public PlayerService() {}


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
            ObjectMapper objectMapper = new ObjectMapper();
            Player player = objectMapper.readValue(requestBody, Player.class);

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
        } catch (IOException e) {
            // Handle the exception (e.g., invalid JSON format)
            return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Invalid request body");
        }
    }

    private boolean userExists(String username) {
        // Logic to check if the user already exists in the database
        // Return true if the user exists, false otherwise
        // ... (you need to implement this method)
        return false;
    }

    private void saveUserToDatabase(Player player) {

        //JDBC to execute an INSERT statement
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtcg", "postgres", "your_password");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Player (username, password) VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}

