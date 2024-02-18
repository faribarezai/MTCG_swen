package mtcg.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.model.Card;
import mtcg.model.User;
import mtcg.service.PackageService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageController {
    private PackageService packageService;
    private Card card;
    private List<Card> packages= new ArrayList<>();

    public PackageController() {}

    public List<Card> getPackage() {
        return this.packages;
    }

    public Response createPackages(Request request) {
        try {
            // Parse the JSON body from the request
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            System.out.println("Requestbody" + requestBody);

            List<Card> cards = objectMapper.readValue(requestBody, new TypeReference<List<Card>>() {});

            for (Card card : cards) {
                System.out.println("Received card registration request: " + card.getCardId() + ", " + card.getName() + ", " + card.getDamage() + ", " + card.getElementType() + ", " + card.getCardType());
                saveCard(card);
            }
            //Card card = objectMapper.readValue(requestBody, Card.class);
           // System.out.println("Received card registration request: " + card.getCardId() + ", " + card.getName() + ", " + card.getDamage()+ ", " + card.getElementType()+ ", " + card.getCardType());
            //saveCard(card);
            return new Response(HttpStatus.CREATED, ContentType.JSON, "Card was added successfully");

        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body");
        }

    }




   /* public void saveCard(Card card) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO card (cardId, name, damage, element, cardType) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, card.getCardId());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setInt(3, card.getDamage());
            preparedStatement.setString(4, card.getElementType().name());
            preparedStatement.setString(5, card.getCardType().name());
            System.out.println("SQL Query: " + preparedStatement.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
*/


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


}