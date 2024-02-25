package mtcg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.model.User;
import mtcg.service.UserService;

public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    public UserController(){}

    public Response handleUserRequest(Request request) {
        // Additional logic if needed
        return userService.handleRequest(request);
    }



    public Response loginUser(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String requestBody = request.getBody();
            User user = objectMapper.readValue(requestBody, User.class);

            // Validate the request body
            if (user == null || user.getUsername() == null || user.getPassword() == null
                    || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Invalid request body");
            }

            // Check if the user already exists
            if (userService.userExists(user)) {
                return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "User logged in");
            }
            // if user does not exits, go to register
            //registerUser(request);

            return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "User does not exist");

        }
        catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Invalid request body");
        }

    }



    public Response registerUser(Request request) {
        try {
            // Parse the JSON body from the request
                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = request.getBody();
                User user = objectMapper.readValue(requestBody, User.class);

                // Validate the request body
                if (user == null || user.getUsername() == null || user.getPassword() == null
                        || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Invalid request body");
                }
            // Check if the user already exists
            if (userService.userExists(user)) {
                // if user already exists, then go to login
                // loginUser(request)
                return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "User already exists");
                // if user already exists, then go to login
            }

            // Save user to the database
            userService.saveUser(user);

            // Return a success response
            return new Response(HttpStatus.CREATED, ContentType.PLAIN_TEXT, "User registered successfully");
        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Invalid request body");
        }
    }
}
