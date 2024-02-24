package mtcg.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mtcg.model.User;
import mtcg.repository.UserRepository;
import mtcg.service.UserService;

public class UserController {
     private UserService userService;
    private UserRepository userRepo = new UserRepository();
    private User user;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserController() {
    }

    // Constructor for testing, accepts a UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //Login User
    public Response loginUser(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            User user = objectMapper.readValue(requestBody, User.class);

            try {
                user = objectMapper.readValue(requestBody, User.class);
            } catch (JsonParseException e) {
                // If parsing fails, return a Bad Request response
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format \n");
            }

            if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Username or password empty \n");
            }

            // Check if the user already exists
            if (userRepo.userLogged(user)) {
                return new Response(HttpStatus.OK, ContentType.JSON, "User logged in successfully \n");
            } else
                return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "User does not exist \n");


        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body");
        }

    }


    public Response registerUser(Request request) {

        try {
            // Parse the JSON body from the request
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = request.getBody();
            System.out.println("Request body in User: " + requestBody);

            User user = objectMapper.readValue(requestBody, User.class);

            // Validate the request body
            if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                // System.out.println("Check if user registration request is valid in registerUSer(): " + user.getUsername() + ", " + user.getPassword());

                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "username or password wrong \n");
            }
            // Check if the user already exists
            if (userRepo.userExists(user)) {
                //System.out.println(user.getUsername() + " User with same username already registered");
                return new Response(HttpStatus.CONFLICT, ContentType.JSON, "User with same username already registered \n");
            } else {
                // Save user to the database
                userRepo.saveUser(user);
                return new Response(HttpStatus.CREATED, ContentType.JSON, "User successfully created \n");
            }


        } catch (Exception e) {
            // Handle the exception (e.g., invalid JSON format)
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid request body \n");
        }

    }
}
